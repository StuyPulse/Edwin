/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.stuypulse.robot;

import com.stuypulse.robot.Constants.*;
import com.stuypulse.robot.commands.*;
import com.stuypulse.robot.commands.auton.routines.*;

import com.stuypulse.robot.subsystems.*;
import com.stuypulse.robot.util.LEDController;

import com.stuypulse.stuylib.input.Gamepad;
import com.stuypulse.stuylib.input.buttons.ButtonWrapper;
import com.stuypulse.stuylib.input.gamepads.*;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public class RobotContainer {

    private final boolean DEBUG = true;

    // Subsystems
    private final Chimney chimney = new Chimney();
    private final Climber climber = new Climber();
    private final Drivetrain drivetrain = new Drivetrain();
    private final Funnel funnel = new Funnel();
    private final Pump pump = new Pump();
    private final Intake intake = new Intake();
    private final Shooter shooter = new Shooter();
    private final Woof woof = new Woof();

    private final LEDController ledController = new LEDController(0);

    private final Gamepad driver = new Xbox(Ports.Gamepad.DRIVER);
    private final Gamepad operator = new Logitech.DMode(Ports.Gamepad.OPERATOR);
    private final Gamepad debug = new Logitech.XMode(Ports.Gamepad.DEBUGGER);

    private static SendableChooser<Command> autonChooser = new SendableChooser<>();


    public RobotContainer() {
        // Configure the button bindings
        configureDefaultCommands();
        configureButtonBindings();
        configureAutons();
    }


    private void configureDefaultCommands() { 
        drivetrain.setDefaultCommand(new DrivetrainDriveCommand(drivetrain, driver));
        woof.setDefaultCommand(new WoofManualControlCommand(woof, operator));
    }


    private void configureButtonBindings() {

        operator.getLeftAnalogButton().whenPressed(new ClimberToggleLiftBrakeCommand(climber));
        new ButtonWrapper(
                () -> (Math.abs(operator.getLeftStick().magnitude()) >= Math.pow(ClimberSettings.MOVE_DEADBAND, 2)
                        && operator.getLeftY() >= Math.abs(operator.getLeftX())))
                                .whileHeld(new ClimberSetupCommand(climber, intake));
        new ButtonWrapper(
                () -> (Math.abs(operator.getLeftStick().magnitude()) >= Math.pow(ClimberSettings.MOVE_DEADBAND, 2)
                        && operator.getLeftY() <= -Math.abs(operator.getLeftX())))
                                .whileHeld(new ClimberRobotClimbCommand(climber, intake));
        // new ButtonWrapper(() -> (Math.abs(operator.getLeftMag()) >=
        // Math.pow(Constants.CLIMBER_MOVE_DEADBAND, 2) && Math.abs(operator.getLeftX())
        // >= Math.abs(operator.getLeftY()))).whileHeld(new
        // ClimberMoveYoyoCommand(climber, operator));

        operator.getLeftButton().whileHeld(new FunnelUnfunnelCommand(funnel));
        operator.getRightButton().whenPressed(new IntakeRetractCommand(intake));
        operator.getTopButton().whileHeld(new ChimneyDownCommand(chimney));
        // operator.getBottomButton().whileHeld(new ChimneyUpCommand(chimney));

        operator.getLeftTriggerButton().whileHeld(new IntakeDeacquireCommand(intake));
        operator.getRightTriggerButton().whileHeld(new IntakeAcquireSetupCommand(intake));

        // operator.getLeftBumper().whenPressed(new WoofSpinToColorCommand(woof));
        operator.getRightBumper().whenPressed(new WoofTurnRotationsWithEncoderCommand(woof));

        // operator.getLeftAnalogButton().whenPressed(new ClimberSetupCommand(climber));

        operator.getDPadUp().whenPressed(
                new ShooterControlCommand(shooter, ShooterSettings.FAR_RPM, Shooter.ShooterMode.SHOOT_FROM_FAR));
        operator.getDPadDown().whenPressed(new ShooterControlCommand(shooter, ShooterSettings.INITATION_LINE_RPM,
                Shooter.ShooterMode.SHOOT_FROM_INITIATION_LINE));
        operator.getDPadLeft().whenPressed(
                new ShooterControlCommand(shooter, ShooterSettings.TRENCH_RPM, Shooter.ShooterMode.SHOOT_FROM_TRENCH));
        // operator.getDPadUp().whenPressed(new ShooterControlCommand(shooter, 480));
        // operator.getDPadDown().whenPressed(new ShooterControlCommand(shooter, 240));
        // operator.getDPadLeft().whenPressed(new ShooterControlCommand(shooter, 360));
        operator.getDPadRight().whenPressed(new ShooterStopCommand(shooter))
                .whenPressed(new ShooterControlCommand(shooter, 0, Shooter.ShooterMode.NONE));

        operator.getBottomButton().whileHeld(new FeedBallsCommand(funnel, chimney));

        operator.getRightAnalogButton().whenPressed(new LEDTogglePartyModeCommand(ledController));

        operator.getStartButton().whileHeld(new FeedBallsAutomaticCommand(chimney, funnel, operator));

        driver.getLeftButton()
                .whileHeld(new DrivetrainGoalCommand(drivetrain, Alignment.INITATION_LINE_DISTANCE).setNeverFinish())
                .whileHeld(new FeedBallsAutomaticCommand(chimney, funnel, operator));

        driver.getTopButton()
                .whileHeld(new DrivetrainGoalCommand(drivetrain, Alignment.TRENCH_DISTANCE).setNeverFinish())
                .whileHeld(new FeedBallsAutomaticCommand(chimney, funnel, operator));

        // driver.getLeftButton().whileHeld(new FeedBallsAutomaticCommand(chimney,
        // funnel, operator));
        // driver.getTopButton().whileHeld(new FeedBallsAutomaticCommand(chimney,
        // funnel, operator));
        // driver.getRightButton().whileHeld(new FeedBallsAutomaticCommand(chimney,
        // funnel, operator));

        if (DEBUG) {
            driver.getRightBumper().whileHeld(new DrivetrainArcCommand(drivetrain, 90, 8));
            driver.getLeftBumper().whileHeld(new DrivetrainArcCommand(drivetrain, -90, 8));

            // Auto alignment for angle and speed and update pid values
            debug.getLeftButton().whileHeld(new DrivetrainAutoAngleCommand(drivetrain,
                    new DrivetrainMovementCommand.Aligner(drivetrain, 0, 0)));
            debug.getTopButton().whileHeld(new DrivetrainAutoSpeedCommand(drivetrain,
                    new DrivetrainMovementCommand.Aligner(drivetrain, 0, 0)));

            // Steal driving abilities from the driver
            debug.getBottomButton().whileHeld(new DrivetrainDriveCommand(drivetrain, debug));

            // DPad controls for 90 degree turns and 2.5 ft steps
            debug.getDPadUp().whenPressed(new InstantCommand(() -> { pump.compress(); }));
            debug.getDPadDown().whenPressed(new InstantCommand(() -> { pump.stop(); }));
        }
    }


    public void configureAutons() {
        autonChooser.setDefaultOption("Do Nothing", new DoNothingAutonCommand(ledController));

        autonChooser.addOption("Barrel Racing Path", new BarrelRacingAuton(drivetrain));


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

    public LEDController getLEDController() {
        return ledController;
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

    public Chimney getChimney() {
        return chimney;
    }

    public Gamepad getDriver() {
        return driver;
    }

}
