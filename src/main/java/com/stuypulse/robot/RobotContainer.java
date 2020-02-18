/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.stuypulse.robot;

import com.stuypulse.robot.Constants.Ports;
import com.stuypulse.robot.Constants.Shooting;
import com.stuypulse.robot.commands.ChimneyDownCommand;
import com.stuypulse.robot.commands.ChimneyStopCommand;
import com.stuypulse.robot.commands.ChimneyUpCommand;
import com.stuypulse.robot.commands.ClimberMoveYoyoCommand;
import com.stuypulse.robot.commands.ClimberRobotClimbCommand;
import com.stuypulse.robot.commands.ClimberSetupCommand;
import com.stuypulse.robot.commands.ClimberToggleLiftBrakeCommand;
import com.stuypulse.robot.commands.DrivetrainAlignmentCommand;
import com.stuypulse.robot.commands.DrivetrainAutoAngleCommand;
import com.stuypulse.robot.commands.DrivetrainAutoSpeedCommand;
import com.stuypulse.robot.commands.DrivetrainDriveCommand;
import com.stuypulse.robot.commands.DrivetrainGoalAligner;
import com.stuypulse.robot.commands.DrivetrainInnerGoalAligner;
import com.stuypulse.robot.commands.DrivetrainMovementCommand;
import com.stuypulse.robot.commands.FeedBallsCommand;
import com.stuypulse.robot.commands.FunnelUnfunnelCommand;
import com.stuypulse.robot.commands.IntakeAcquireCommand;
import com.stuypulse.robot.commands.IntakeDeacquireCommand;
import com.stuypulse.robot.commands.IntakeExtendCommand;
import com.stuypulse.robot.commands.IntakeRetractCommand;
import com.stuypulse.robot.commands.ReverseShooterCommand;
import com.stuypulse.robot.commands.ShooterControlCommand;
import com.stuypulse.robot.commands.ShooterDefaultCommand;
import com.stuypulse.robot.commands.ShooterStopCommand;
import com.stuypulse.robot.commands.WoofManualControlCommand;
import com.stuypulse.robot.commands.WoofSpinToColorCommand;
import com.stuypulse.robot.commands.WoofTurnRotationsCommand;
import com.stuypulse.robot.subsystems.Chimney;
import com.stuypulse.robot.subsystems.Climber;
import com.stuypulse.robot.subsystems.Drivetrain;
import com.stuypulse.robot.subsystems.Funnel;
import com.stuypulse.robot.subsystems.Intake;
import com.stuypulse.robot.subsystems.Shooter;
import com.stuypulse.robot.subsystems.Woof;
import com.stuypulse.robot.util.MotorStalling;
import com.stuypulse.stuylib.control.PIDCalculator;
import com.stuypulse.stuylib.input.WPIGamepad;
import com.stuypulse.stuylib.input.buttons.ButtonWrapper;
import com.stuypulse.stuylib.input.gamepads.Logitech;
import com.stuypulse.stuylib.input.gamepads.PS4;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public class RobotContainer {

  private final boolean DEBUG = false;


  //Subsystems
  private final Chimney chimney = new Chimney();
  // private final Climber climber = new Climber();
  private final Drivetrain drivetrain = new Drivetrain();
  private final Funnel funnel = new Funnel();
  private final Intake intake = new Intake();
  private final Shooter shooter = new Shooter();
  // private final Woof woof = new Woof();

  private final WPIGamepad driver = new PS4(Ports.Gamepad.DRIVER);
  private final WPIGamepad operator = new Logitech.DMode(Ports.Gamepad.OPERATOR);
  private final WPIGamepad debug = new Logitech.XMode(Ports.Gamepad.DEBUGGER);

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {

    // Default driving command that uses gamepad
    drivetrain.setDefaultCommand(new DrivetrainDriveCommand(drivetrain, driver));

    // Configure the button bindings
    configureButtonBindings();

    // chimney.setDefaultCommand(new ChimneyStopCommand(chimney));

    // woof.setDefaultCommand(new WoofManualControlCommand(woof, operator));

    shooter.setDefaultCommand(new ShooterDefaultCommand(shooter, operator));

    new Thread(new MotorStalling(funnel)).start();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by instantiating a {@link GenericHID} or one of its subclasses
   * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
   * passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {

    // operator.getLeftAnalogButton().whenPressed(new ClimberToggleLiftBrakeCommand(climber));
    // new ButtonWrapper(() -> (Math.abs(operator.getLeftMag()) >= Math.pow(Constants.CLIMBER_MOVE_DEADBAND, 2) && operator.getLeftY() >= Math.abs(operator.getLeftX()))).whileHeld(new ClimberSetupCommand(climber, intake));
    // new ButtonWrapper(() -> (Math.abs(operator.getLeftMag()) >= Math.pow(Constants.CLIMBER_MOVE_DEADBAND, 2) && operator.getLeftY() <= -Math.abs(operator.getLeftX()))).whileHeld(new ClimberRobotClimbCommand(climber));
    // new ButtonWrapper(() -> (Math.abs(operator.getLeftMag()) >= Math.pow(Constants.CLIMBER_MOVE_DEADBAND, 2) && Math.abs(operator.getLeftX()) >= Math.abs(operator.getLeftY()))).whileHeld(new ClimberMoveYoyoCommand(climber, operator));

    operator.getLeftButton().whileHeld(new FunnelUnfunnelCommand(funnel));
    operator.getRightButton().whenPressed(new IntakeRetractCommand(intake));
    operator.getTopButton().whileHeld(new ChimneyDownCommand(chimney));
    // operator.getBottomButton().whileHeld(new ChimneyUpCommand(chimney));

    operator.getLeftTrigger().whileHeld(new IntakeDeacquireCommand(intake));
    operator.getRightTrigger().whileHeld(new IntakeAcquireCommand(intake));

    // operator.getLeftBumper().whenPressed(new WoofSpinToColorCommand(woof));
    // operator.getRightBumper().whenPressed(new WoofTurnRotationsCommand(woof));

    // operator.getLeftAnalogButton().whenPressed(new ClimberToggleLiftBrakeCommand(climber));

    operator.getDPadUp().whenPressed(new ShooterControlCommand(shooter, Shooting.FAR_RPM));
    operator.getDPadDown().whenPressed(new ShooterControlCommand(shooter, Shooting.INITATION_LINE_RPM));
    operator.getDPadLeft().whenPressed(new ShooterControlCommand(shooter, Shooting.TRENCH_RPM));
    // operator.getDPadUp().whenPressed(new ShooterControlCommand(shooter, 480));
    // operator.getDPadDown().whenPressed(new ShooterControlCommand(shooter, 240));
    // operator.getDPadLeft().whenPressed(new ShooterControlCommand(shooter, 360));
    operator.getDPadRight().whenPressed(new ShooterStopCommand(shooter)).whenPressed(new ShooterControlCommand(shooter, 0));
    operator.getStartButton().whileHeld(new ReverseShooterCommand(shooter));

    operator.getBottomButton().whileHeld(new FeedBallsCommand(shooter, funnel, chimney));

    driver.getLeftButton().whenHeld(new DrivetrainAlignmentCommand(drivetrain, new DrivetrainInnerGoalAligner()));
    driver.getTopButton().whenHeld(new DrivetrainAlignmentCommand(drivetrain, new DrivetrainGoalAligner(20)));

    /**
     * 
     */

    if (DEBUG) {
      // Auto alignment for angle and speed and update pid values
      debug.getLeftButton()
          .toggleWhenPressed(new DrivetrainAutoAngleCommand(drivetrain, new DrivetrainGoalAligner(10)));
      debug.getTopButton().toggleWhenPressed(new DrivetrainAutoSpeedCommand(drivetrain, new DrivetrainGoalAligner(10)));

      debug.getRightButton().toggleWhenPressed(new ShooterDefaultCommand(shooter, debug,
          new PIDCalculator(Shooting.Shooter.BANGBANG_SPEED), new PIDCalculator(Shooting.Feeder.BANGBANG_SPEED)));

      // Steal driving abilities from the driver
      debug.getBottomButton().toggleWhenPressed(new DrivetrainDriveCommand(drivetrain, debug));

      // DPad controls for 90 degree turns and 2.5 ft steps
      debug.getDPadUp().whenPressed(new DrivetrainMovementCommand(drivetrain, 0, 2.5));
      debug.getDPadDown().whenPressed(new DrivetrainMovementCommand(drivetrain, 0, -2.5));
      debug.getDPadLeft().whenPressed(new DrivetrainMovementCommand(drivetrain, -90));
      debug.getDPadRight().whenPressed(new DrivetrainMovementCommand(drivetrain, 90));
    }
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return null;
  }

}
