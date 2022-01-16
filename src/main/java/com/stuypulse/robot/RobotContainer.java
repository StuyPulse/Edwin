/* Copyright (c) 2021 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.robot;

import com.stuypulse.stuylib.input.Gamepad;
import com.stuypulse.stuylib.input.gamepads.*;

import com.stuypulse.robot.Constants.*;
import com.stuypulse.robot.commands.*;
import com.stuypulse.robot.commands.auton.routines.*;
import com.stuypulse.robot.subsystems.*;
import com.stuypulse.robot.subsystems.Shooter.ShooterMode;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.Button;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {

    // Subsystems
    private final LEDController ledController = new LEDController(0, this);
    private final Chimney chimney = new Chimney();
    private final Climber climber = new Climber();
    private final Drivetrain drivetrain = new Drivetrain();
    private final Funnel funnel = new Funnel();
    private final Pump pump = new Pump();
    private final Intake intake = new Intake();
    private final Shooter shooter = new Shooter();
    private final Woof woof = new Woof();

    // Gamepads
    public final Gamepad driver = new PS4(Ports.Gamepad.DRIVER);
    public final Gamepad operator = new Logitech.DMode(Ports.Gamepad.OPERATOR);

    // Autons
    private static SendableChooser<Command> autonChooser = new SendableChooser<>();

    public RobotContainer() {
        // Disable telementry to reduce lag
        LiveWindow.disableAllTelemetry();
        DriverStation.silenceJoystickConnectionWarning(true);

        // Set pump to false to avoid warning
        pump.set(false);

        // Configure the button bindings
        configureDefaultCommands();
        configureButtonBindings();
        configureAutons();
    }

    private void configureDefaultCommands() {
        drivetrain.setDefaultCommand(new DrivetrainDriveCommand(drivetrain, driver));
        woof.setDefaultCommand(new WoofManualControlCommand(woof, operator));

        // NOPE! Not Anymore, It's Annoying.
        // chimney.setDefaultCommand(new FeedBallsAutomaticCommand(chimney, funnel));
    }

    private void configureButtonBindings() {

        /***********************/
        /*** Climber Control ***/
        /***********************/

        // Setup Climber before Climbing (Move Left Stick Up)
        new Button(() -> operator.getRightY() >= 0.75)
                .whileHeld(new ClimberSetupCommand(climber, intake));

        // Start Climbing (Move Left Stick Down)
        new Button(() -> operator.getRightY() <= -0.75)
                .whenPressed(new IntakeRetractCommand(intake))
                .whileHeld(new ClimberRobotClimbCommand(climber, intake));

        // Toggle Brake (Push In Left Stick)
        operator.getRightAnalogButton().whenPressed(new ClimberToggleLiftBrakeCommand(climber));

        /**************************/
        /*** Funnel and Chimney ***/
        /**************************/

        // Reverse each component if it gets stuck
        operator.getLeftButton()
                .whileHeld(new FunnelUnfunnelCommand(funnel))
                .whileHeld(new ChimneyDownCommand(chimney));

        // Bottom button puts balls into the shooter
        operator.getBottomButton().whileHeld(new FeedBallsCommand(funnel, chimney));
        operator.getRightButton().whileHeld(new FeedBallsCommand(funnel, chimney));

        /***********************/
        /*** Intake Controls ***/
        /***********************/

        // Right Trigger Extends Intake and Acquires
        operator.getRightTriggerButton()
                .whenPressed(new IntakeExtendCommand(intake))
                .whileHeld(new IntakeAcquireCommand(intake));

        // Left Trigger Deacquires
        operator.getLeftTriggerButton().whileHeld(new IntakeDeacquireCommand(intake));

        // Right Button Retracts Intake
        operator.getTopButton().whenPressed(new IntakeRetractCommand(intake));

        /*********************/
        /*** Woof Controls ***/
        /*********************/

        // Right Bumper Uses Encoder
        operator.getRightBumper()
                .whenPressed(new WoofTurnRotationsWithEncoderCommand(woof, ledController));
        operator.getLeftBumper().whenPressed(new WoofSpinToFMSColorCommand(woof, ledController));

        // Left Stick moves woof manually
        // it is handled by the default commands

        /*****************************/
        /*** Shooter Speed Control ***/
        /*****************************/

        // Everything that is not meant to shoot, stops the shooter
        operator.getDPadUp().whenPressed(new ShooterStopCommand(shooter));
        operator.getDPadDown().whenPressed(new ShooterStopCommand(shooter));

        // Move to different zone
        operator.getDPadLeft()
                .whileHeld(new ShooterControlCommand(shooter, ShooterMode.INITIATION_LINE));
        operator.getDPadRight()
                .whileHeld(new ShooterControlCommand(shooter, ShooterMode.TRENCH_SHOT));
        operator.getDPadDown()
                .whileHeld(new ShooterControlCommand(shooter, ShooterMode.SUPER_TRENCH_SHOT));

        /*****************/
        /*** Alignment ***/
        /*****************/

        // Left Button Aligns just sideways
        driver.getLeftButton()
                .whileHeld(
                        new DrivetrainGoalCommand(drivetrain, -1)
                                .setNeverFinish()
                                .setMaxSpeed(0)
                                .setLEDController(ledController))
                .whileHeld(new FeedBallsAutomaticCommand(chimney, funnel));

        // Bottom Button Aligns to the right distance
        driver.getBottomButton()
                .whileHeld(
                        new DrivetrainAutomaticAlign(drivetrain, shooter)
                                .setNeverFinish()
                                .setLEDController(ledController))
                .whileHeld(new FeedBallsAutomaticCommand(chimney, funnel));
    }

    public void configureAutons() {
        autonChooser.addOption("Do Nothing", new DoNothingAutonCommand(ledController));

        autonChooser.setDefaultOption(
                "6Ball Auto", new OldSixBallTrenchAutonClean(this));

        autonChooser.addOption("5Ball Auto", new WoofFiveBallAuton(this));


        autonChooser.addOption("Moby Auto", new MobilityAuton(this));

        autonChooser.addOption("3Ball Auto", new ThreeBallAuton(this));

        autonChooser.addOption("8Ball Auto", new EightBallAuton(this));

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

    public Pump getPump() {
        return pump;
    }

    public Gamepad getDriver() {
        return driver;
    }

    public Gamepad getOperator() {
        return operator;
    }
}
