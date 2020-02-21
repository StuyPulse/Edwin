/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.stuypulse.robot;

import com.stuypulse.robot.Constants.Alignment;
import com.stuypulse.robot.Constants.Ports;
import com.stuypulse.robot.Constants.Shooting;
import com.stuypulse.robot.commands.ChimneyDownCommand;
import com.stuypulse.robot.commands.ClimberRobotClimbCommand;
import com.stuypulse.robot.commands.ClimberSetupCommand;
import com.stuypulse.robot.commands.ClimberToggleLiftBrakeCommand;
import com.stuypulse.robot.commands.DrivetrainAlignmentCommand;
import com.stuypulse.robot.commands.DrivetrainAutoAngleCommand;
import com.stuypulse.robot.commands.DrivetrainAutoSpeedCommand;
import com.stuypulse.robot.commands.DrivetrainDriveCommand;
import com.stuypulse.robot.commands.DrivetrainGoalAligner;
import com.stuypulse.robot.commands.DrivetrainMovementCommand;
import com.stuypulse.robot.commands.FeedBallsCommand;
import com.stuypulse.robot.commands.FunnelUnfunnelCommand;
import com.stuypulse.robot.commands.IntakeAcquireSetupCommand;
import com.stuypulse.robot.commands.IntakeDeacquireCommand;
import com.stuypulse.robot.commands.IntakeRetractCommand;
import com.stuypulse.robot.commands.ReverseShooterCommand;
import com.stuypulse.robot.commands.ShooterControlCommand;
import com.stuypulse.robot.commands.ShooterDefaultCommand;
import com.stuypulse.robot.commands.ShooterStopCommand;
import com.stuypulse.robot.commands.WoofManualControlCommand;
import com.stuypulse.robot.commands.WoofTurnRotationsWithEncoderCommand;
import com.stuypulse.robot.commands.auton.routines.DoNothingAutonCommand;
import com.stuypulse.robot.commands.auton.routines.EightBallFiveRdvsAutonCommand;
import com.stuypulse.robot.commands.auton.routines.EightBallThreeTrenchTwoRdvsAutonCommand;
import com.stuypulse.robot.commands.auton.routines.MobilityTowardIntakeAutonCommand;
import com.stuypulse.robot.commands.auton.routines.MobilityTowardShooterAutonCommand;
import com.stuypulse.robot.commands.auton.routines.ShootThreeMoveTowardIntakeAutonCommand;
import com.stuypulse.robot.commands.auton.routines.ShootThreeMoveTowardShooterAutonCommand;
import com.stuypulse.robot.commands.auton.routines.SixBallThreeRdvsAutonCommand;
import com.stuypulse.robot.commands.auton.routines.SixBallThreeTrenchAutonCommand;
import com.stuypulse.robot.commands.auton.routines.SixBallTwoTrenchOneTrenchAutonCommand;
import com.stuypulse.robot.subsystems.Chimney;
import com.stuypulse.robot.subsystems.Climber;
import com.stuypulse.robot.subsystems.Drivetrain;
import com.stuypulse.robot.subsystems.Funnel;
import com.stuypulse.robot.subsystems.Intake;
import com.stuypulse.robot.subsystems.Shooter;
import com.stuypulse.robot.subsystems.Shooter.ShooterMode;
import com.stuypulse.robot.subsystems.Woof;
import com.stuypulse.robot.util.LEDController;
import com.stuypulse.robot.util.MotorStalling;
import com.stuypulse.stuylib.control.PIDCalculator;
import com.stuypulse.stuylib.input.Gamepad;
import com.stuypulse.stuylib.input.WPIGamepad;
import com.stuypulse.stuylib.input.buttons.ButtonWrapper;
import com.stuypulse.stuylib.input.gamepads.Logitech;
import com.stuypulse.stuylib.input.gamepads.PS4;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public class RobotContainer {

  private final boolean DEBUG = true;


  //Subsystems
  private final Chimney chimney = new Chimney();
  private final Climber climber = new Climber();
  private final Drivetrain drivetrain = new Drivetrain();
  private final Funnel funnel = new Funnel();
  private final Intake intake = new Intake();
  private final Shooter shooter = new Shooter();
  private final Woof woof = new Woof();

  private final LEDController ledController = new LEDController(0);

  private final WPIGamepad driver = new PS4(Ports.Gamepad.DRIVER);
  private final WPIGamepad operator = new Logitech.DMode(Ports.Gamepad.OPERATOR);
  private final WPIGamepad debug = new Logitech.XMode(Ports.Gamepad.DEBUGGER);

  private static SendableChooser<Command> autonChooser = new SendableChooser<>();

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {

    // Default driving command that uses gamepad
    drivetrain.setDefaultCommand(new DrivetrainDriveCommand(drivetrain, driver));

    // Configure the button bindings
    configureButtonBindings();

    // chimney.setDefaultCommand(new ChimneyStopCommand(chimney));

    woof.setDefaultCommand(new WoofManualControlCommand(woof, operator));
    // chimney.setDefaultCommand(new FeedBallsAutomaticCommand(chimney, funnel, operator));
    shooter.setDefaultCommand(new ShooterDefaultCommand(shooter, null));

  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by instantiating a {@link GenericHID} or one of its subclasses
   * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
   * passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {

    operator.getLeftAnalogButton().whenPressed(new ClimberToggleLiftBrakeCommand(climber));
    new ButtonWrapper(() -> (Math.abs(operator.getLeftMag()) >= Math.pow(Constants.CLIMBER_MOVE_DEADBAND, 2) && operator.getLeftY() >= Math.abs(operator.getLeftX()))).whileHeld(new ClimberSetupCommand(climber, intake));
    new ButtonWrapper(() -> (Math.abs(operator.getLeftMag()) >= Math.pow(Constants.CLIMBER_MOVE_DEADBAND, 2) && operator.getLeftY() <= -Math.abs(operator.getLeftX()))).whileHeld(new ClimberRobotClimbCommand(climber, intake));
    // new ButtonWrapper(() -> (Math.abs(operator.getLeftMag()) >= Math.pow(Constants.CLIMBER_MOVE_DEADBAND, 2) && Math.abs(operator.getLeftX()) >= Math.abs(operator.getLeftY()))).whileHeld(new ClimberMoveYoyoCommand(climber, operator));

    operator.getLeftButton().whileHeld(new FunnelUnfunnelCommand(funnel));
    operator.getRightButton().whenPressed(new IntakeRetractCommand(intake));
    operator.getTopButton().whileHeld(new ChimneyDownCommand(chimney));
    // operator.getBottomButton().whileHeld(new ChimneyUpCommand(chimney));

    operator.getLeftTrigger().whileHeld(new IntakeDeacquireCommand(intake));
    operator.getRightTrigger().whileHeld(new IntakeAcquireSetupCommand(intake));

    // operator.getLeftBumper().whenPressed(new WoofSpinToColorCommand(woof));
    operator.getRightBumper().whenPressed(new WoofTurnRotationsWithEncoderCommand(woof));

    // operator.getLeftAnalogButton().whenPressed(new ClimberSetupCommand(climber));

    operator.getDPadUp().whenPressed(new ShooterControlCommand(shooter, Shooting.FAR_RPM, ShooterMode.SHOOT_FROM_FAR));
    operator.getDPadDown().whenPressed(new ShooterControlCommand(shooter, Shooting.INITATION_LINE_RPM, ShooterMode.SHOOT_FROM_INITIATION_LINE));
    operator.getDPadLeft().whenPressed(new ShooterControlCommand(shooter, Shooting.TRENCH_RPM, ShooterMode.SHOOT_FROM_TRENCH));
    // operator.getDPadUp().whenPressed(new ShooterControlCommand(shooter, 480));
    // operator.getDPadDown().whenPressed(new ShooterControlCommand(shooter, 240));
    // operator.getDPadLeft().whenPressed(new ShooterControlCommand(shooter, 360));
    operator.getDPadRight().whenPressed(new ShooterStopCommand(shooter)).whenPressed(new ShooterControlCommand(shooter, 0, ShooterMode.NONE));

    operator.getStartButton().whileHeld(new ReverseShooterCommand(shooter));

    operator.getBottomButton().whileHeld(new FeedBallsCommand(shooter, funnel, chimney));

    driver.getLeftButton().whileHeld(new DrivetrainAlignmentCommand(drivetrain, new DrivetrainGoalAligner(Alignment.INITATION_LINE_DISTANCE)).setNeverFinish());
    driver.getTopButton().whileHeld(new DrivetrainAlignmentCommand(drivetrain, new DrivetrainGoalAligner(Alignment.TRENCH_DISTANCE)).setNeverFinish());

    /**
     * 
     */

    if (DEBUG) {
      // Auto alignment for angle and speed and update pid values
      debug.getLeftButton()
          .whileHeld(new DrivetrainAutoAngleCommand(drivetrain, new DrivetrainGoalAligner(Alignment.INITATION_LINE_DISTANCE)));
      debug.getTopButton().whileHeld(new DrivetrainAutoSpeedCommand(drivetrain, new DrivetrainGoalAligner(Alignment.INITATION_LINE_DISTANCE)));

      debug.getRightButton().whileHeld(new ShooterDefaultCommand(shooter, debug,
          new PIDCalculator(Shooting.Shooter.BANGBANG_SPEED), new PIDCalculator(Shooting.Feeder.BANGBANG_SPEED)));

      // Steal driving abilities from the driver
      debug.getBottomButton().whileHeld(new DrivetrainDriveCommand(drivetrain, debug));

      // DPad controls for 90 degree turns and 2.5 ft steps
      debug.getDPadUp().whenPressed(new DrivetrainMovementCommand(drivetrain, 0, 2.5));
      debug.getDPadDown().whenPressed(new DrivetrainMovementCommand(drivetrain, 0, 2.5));
      debug.getDPadLeft().whenPressed(new DrivetrainMovementCommand(drivetrain, -90));
      debug.getDPadRight().whenPressed(new DrivetrainMovementCommand(drivetrain, 90));
    }
  } 
    public LEDController getLEDController() {
      return ledController;
    }

  public void initSmartDashboard() {
    autonChooser.setDefaultOption("Do Nothing", new DoNothingAutonCommand(ledController));
    autonChooser.addOption("Mobility Toward Intake", new MobilityTowardIntakeAutonCommand(drivetrain, ledController));
    autonChooser.addOption("Mobility Toward Shooter", new MobilityTowardShooterAutonCommand(drivetrain, ledController));
    autonChooser.addOption("Shoot Three, Move Toward Intake", new ShootThreeMoveTowardIntakeAutonCommand(drivetrain, shooter, intake, funnel, chimney, ledController));
    autonChooser.addOption("Shoot Three, Move Toward Shooter", new ShootThreeMoveTowardShooterAutonCommand(drivetrain, shooter, intake, funnel, chimney, ledController));
    autonChooser.addOption("Six Ball Two, then One Trench", new SixBallTwoTrenchOneTrenchAutonCommand(drivetrain, shooter, funnel, chimney, intake, ledController));

    autonChooser.addOption("Six Ball Three Rdvs", new SixBallThreeRdvsAutonCommand(drivetrain, intake, funnel, chimney, shooter));
    autonChooser.addOption("Six Ball Three Trench", new SixBallThreeTrenchAutonCommand(drivetrain, shooter, funnel, chimney));
    autonChooser.addOption("Eight Ball Five Rdvs", new EightBallFiveRdvsAutonCommand(drivetrain, intake));
    autonChooser.addOption("Eight Ball Three Trench Two Rdvs", new EightBallThreeTrenchTwoRdvsAutonCommand(drivetrain));
    
    SmartDashboard.putData("Autonomous", autonChooser);
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return autonChooser.getSelected();
  }

  public Drivetrain getDrivetrain() {
    return drivetrain;
  }

  public Intake getIntake() {
    return intake;
  }

  public Shooter getShooter() {
    return shooter;
  }

  public Funnel getFunnel() {
    return funnel;
  }

  public Gamepad getDriver() {
    return driver;
  }

}
