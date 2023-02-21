/************************ PROJECT PHIL ************************/
/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved.*/
/* This work is licensed under the terms of the MIT license.  */
/**************************************************************/

package com.stuypulse.robot;

import com.stuypulse.robot.commands.drivetrain.DriveCommand;
// import com.stuypulse.robot.commands.drivetrain.DrivetrainAlignCommand;
import com.stuypulse.robot.commands.drivetrain.DrivetrainPointAtAngleCommand;
import com.stuypulse.robot.commands.intake.IntakeAcquireCommand;
import com.stuypulse.robot.commands.intake.IntakeDeacquireCommand;
import com.stuypulse.robot.commands.intake.IntakeExtendCommand;
import com.stuypulse.robot.commands.shooter.ShooterLaunchPadShot;
import com.stuypulse.robot.commands.shooter.ShooterRingShot;
import com.stuypulse.robot.commands.shooter.ShooterStopCommand;
import com.stuypulse.robot.commands.auton.DoNothingAuton;
import com.stuypulse.robot.commands.conveyor.ConveyorLiftDownCommand;
import com.stuypulse.robot.commands.conveyor.ConveyorShootCommand;
import com.stuypulse.robot.commands.conveyor.ConveyorStopCommand;
import com.stuypulse.robot.constants.Ports;
import com.stuypulse.robot.subsystems.Intake;
import com.stuypulse.robot.subsystems.Camera;
import com.stuypulse.robot.subsystems.Conveyor;
import com.stuypulse.robot.subsystems.Drivetrain;
import com.stuypulse.robot.subsystems.Pump;
import com.stuypulse.robot.subsystems.Shooter;
import com.stuypulse.stuylib.input.Gamepad;
import com.stuypulse.stuylib.input.gamepads.AutoGamepad;
import com.stuypulse.stuylib.math.Angle;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;

public class RobotContainer {

    // Gamepads
    public final Gamepad driver = new AutoGamepad(Ports.Gamepad.DRIVER);
    public final Gamepad operator = new AutoGamepad(Ports.Gamepad.OPERATOR);

    // Subsystem

    public final Pump pump = new Pump();
    public final Intake intake = new Intake();
    public final Conveyor conveyor = new Conveyor();
    public final Camera camera = new Camera();
    public final Shooter shooter = new Shooter();
    public final Drivetrain drivetrain = new Drivetrain(camera);

    // Autons
    private static SendableChooser<Command> autonChooser = new SendableChooser<>();

    // Robot container

    public RobotContainer() {
        configureDefaultCommands();
        configureButtonBindings();
        configureAutons();
    }

    /****************/
    /*** DEFAULTS ***/
    /****************/

    private void configureDefaultCommands() {
        drivetrain.setDefaultCommand(new DriveCommand(drivetrain, driver));
    }

    /***************/
    /*** BUTTONS ***/
    /***************/

    private void configureButtonBindings() {
        // driver.getBottomButton().whileTrue(new DrivetrainAlignCommand(drivetrain,
        // camera));
        driver.getRightButton().whileTrue(new ConveyorShootCommand(conveyor));
        driver.getTopButton().whileTrue(new ConveyorLiftDownCommand(conveyor));
        // driver.getLeftButton().whileTrue(new ConveyorStopCommand(conveyor));
        driver.getLeftButton()
                .onTrue(new InstantCommand(
                        () -> drivetrain.setPose(new Pose2d(3.302, 0, new Rotation2d())),
                        drivetrain));
        driver.getBottomButton().whileTrue(new DrivetrainPointAtAngleCommand(drivetrain, Angle.kZero));

        driver.getDPadUp().onTrue(new ShooterRingShot(shooter));
        driver.getDPadLeft().onTrue(new ShooterLaunchPadShot(shooter));
        driver.getRightBumper().onTrue(new ShooterStopCommand(shooter));

        // driver..getDPadLeft().onTrue(new Shooter)

        operator.getLeftTriggerButton()
                .whileTrue(new IntakeAcquireCommand(intake));
        operator.getRightTriggerButton().onTrue(new IntakeExtendCommand(intake))
                .whileTrue(new IntakeDeacquireCommand(intake));

        operator.getTopButton().whileTrue(new ConveyorStopCommand(conveyor));
        operator.getBottomButton().whileTrue(new ConveyorShootCommand(conveyor));

        operator.getDPadRight().onTrue(new ShooterRingShot(shooter));
        // operat
    }

    /**************/
    /*** AUTONS ***/
    /**************/

    public void configureAutons() {
        autonChooser.setDefaultOption("Do Nothing", new DoNothingAuton());

        SmartDashboard.putData("Autonomous", autonChooser);
    }

    public Command getAutonomousCommand() {
        return autonChooser.getSelected();
    }
}
