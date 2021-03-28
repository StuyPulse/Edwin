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
    public final Gamepad driver = new AutoGamepad(Ports.Gamepad.DRIVER);
    public final Gamepad operator = new AutoGamepad(Ports.Gamepad.OPERATOR);

    // Autons
    private static SendableChooser<Command> autonChooser = new SendableChooser<>();

    public RobotContainer() {
        // Disable telementry to reduce lag
        LiveWindow.disableAllTelemetry();

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
    }

    private void configureButtonBindings() {
        // Climber Control
        operator.getLeftAnalogButton().whenPressed(new ClimberToggleLiftBrakeCommand(climber));
        operator.getSelectButton().whileHeld(new ClimberSetupCommand(climber, intake));
        operator.getStartButton().whileHeld(new ClimberRobotClimbCommand(climber, intake));

        // Funnel and Chimney
        operator.getLeftButton().whileHeld(new FunnelUnfunnelCommand(funnel));
        operator.getLeftButton().whileHeld(new ChimneyDownCommand(chimney));
        operator.getBottomButton().whileHeld(new FeedBallsCommand(funnel, chimney));
        operator.getRightButton().whileHeld(new FeedBallsAutomaticCommand(chimney, funnel, driver));

        // Intake Controlls
        operator.getTopButton().whenPressed(new IntakeRetractCommand(intake));
        operator.getLeftBumper().whileHeld(new IntakeDeacquireCommand(intake));
        operator.getRightBumper().whileHeld(new IntakeAcquireSetupCommand(intake));

        // Shooter Speed Control
        operator.getLeftBumper().whenPressed(new ShooterStopCommand(shooter));
        operator.getRightBumper().whenPressed(new ShooterStopCommand(shooter));

        operator.getDPadUp()
                .whileHeld(new ShootAlignCommand(drivetrain, shooter, ShooterMode.GREEN_ZONE));
        operator.getDPadDown()
                .whileHeld(new ShootAlignCommand(drivetrain, shooter, ShooterMode.YELLOW_ZONE));
        operator.getDPadLeft()
                .whileHeld(new ShootAlignCommand(drivetrain, shooter, ShooterMode.BLUE_ZONE));
        operator.getDPadRight()
                .whileHeld(new ShootAlignCommand(drivetrain, shooter, ShooterMode.RED_ZONE));

        // This is never true, its just to comment out the old code
        if (drivetrain == null) {
            operator.getLeftAnalogButton().whenPressed(new ClimberToggleLiftBrakeCommand(climber));
            new Button(
                            () ->
                                    (Math.abs(operator.getLeftStick().magnitude())
                                                    >= Math.pow(ClimberSettings.MOVE_DEADBAND, 2)
                                            && operator.getLeftY()
                                                    >= Math.abs(operator.getLeftX())))
                    .whileHeld(new ClimberSetupCommand(climber, intake));
            new Button(
                            () ->
                                    (Math.abs(operator.getLeftStick().magnitude())
                                                    >= Math.pow(ClimberSettings.MOVE_DEADBAND, 2)
                                            && operator.getLeftY()
                                                    <= -Math.abs(operator.getLeftX())))
                    .whileHeld(new ClimberRobotClimbCommand(climber, intake));
            // new Button(() -> (Math.abs(operator.getLeftMag()) >=
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

            //     operator.getDPadUp()
            //             .whenPressed(
            //                     new ShooterControlCommand(
            //                             shooter,
            //                             ShooterSettings.FAR_RPM,
            //                             Shooter.ShooterMode.SHOOT_FROM_FAR));
            //     operator.getDPadDown()
            //             .whenPressed(
            //                     new ShooterControlCommand(
            //                             shooter,
            //                             ShooterSettings.INITATION_LINE_RPM,
            //                             Shooter.ShooterMode.SHOOT_FROM_INITIATION_LINE));
            //     operator.getDPadLeft()
            //             .whenPressed(
            //                     new ShooterControlCommand(
            //                             shooter,
            //                             ShooterSettings.TRENCH_RPM,
            //                             Shooter.ShooterMode.SHOOT_FROM_TRENCH));
            // operator.getDPadUp().whenPressed(new ShooterControlCommand(shooter, 480));
            // operator.getDPadDown().whenPressed(new ShooterControlCommand(shooter, 240));
            // operator.getDPadLeft().whenPressed(new ShooterControlCommand(shooter, 360));
            //     operator.getDPadRight()
            //             .whenPressed(new ShooterStopCommand(shooter))
            //             .whenPressed(new ShooterControlCommand(shooter, 0,
            // Shooter.ShooterMode.NONE));

            operator.getBottomButton().whileHeld(new FeedBallsCommand(funnel, chimney));

            // operator.getRightAnalogButton()
            //         .whenPressed(new LEDTogglePartyModeCommand(ledController));

            operator.getStartButton()
                    .whileHeld(new FeedBallsAutomaticCommand(chimney, funnel, operator));

            driver.getLeftButton()
                    .whileHeld(
                            new DrivetrainGoalCommand(drivetrain, Alignment.INITATION_LINE_DISTANCE)
                                    .setNeverFinish())
                    .whileHeld(new FeedBallsAutomaticCommand(chimney, funnel, operator));

            driver.getTopButton()
                    .whileHeld(
                            new DrivetrainGoalCommand(drivetrain, Alignment.TRENCH_DISTANCE)
                                    .setNeverFinish())
                    .whileHeld(new FeedBallsAutomaticCommand(chimney, funnel, operator));

            // driver.getLeftButton().whileHeld(new FeedBallsAutomaticCommand(chimney,
            // funnel, operator));
            // driver.getTopButton().whileHeld(new FeedBallsAutomaticCommand(chimney,
            // funnel, operator));
            // driver.getRightButton().whileHeld(new FeedBallsAutomaticCommand(chimney,
            // funnel, operator));
        }
    }

    public void configureAutons() {
        autonChooser.setDefaultOption("Do Nothing", new DoNothingAutonCommand(ledController));

        autonChooser.addOption("Bounce Path", new BouncePathAutonCommand(drivetrain));
        autonChooser.addOption("Barrel Racing Path", new BarrelRacingAuton(drivetrain));
        autonChooser.addOption("Slalom Path", new SlalomPathAutonCommand(drivetrain));

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

    public Gamepad getOperator() {
        return operator;
    }
}
