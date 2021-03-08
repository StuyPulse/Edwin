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
import com.stuypulse.stuylib.input.gamepads.*;

import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.Button;

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

    private final Gamepad tester = new AutoGamepad(Ports.Gamepad.TESTER);

    private static SendableChooser<Command> autonChooser = new SendableChooser<>();


    public RobotContainer() {
        LiveWindow.disableAllTelemetry();

        // Configure the button bindings
        configureDefaultCommands();
        configureButtonBindings();
        configureAutons();
    }


    private void configureDefaultCommands() { 
        drivetrain.setDefaultCommand(new DrivetrainDriveCommand(drivetrain, tester));
        woof.setDefaultCommand(new WoofManualControlCommand(woof, tester));
    }


    private void configureButtonBindings() {

        // Climber Control
        tester.getLeftAnalogButton().whenPressed(new ClimberToggleLiftBrakeCommand(climber));
        tester.getSelectButton().whileHeld(new ClimberSetupCommand(climber, intake));
        tester.getStartButton().whileHeld(new ClimberRobotClimbCommand(climber, intake));

        // Funnel and Chimney
        tester.getLeftButton().whileHeld(new FunnelUnfunnelCommand(funnel));
        tester.getLeftButton().whileHeld(new ChimneyDownCommand(chimney));
        tester.getBottomButton().whileHeld(new FeedBallsCommand(funnel, chimney));
        tester.getRightButton().whileHeld(new FeedBallsAutomaticCommand(chimney, funnel, tester));

        // Intake Controlls
        tester.getTopButton().whenPressed(new IntakeRetractCommand(intake));
        tester.getLeftBumper().whileHeld(new IntakeDeacquireCommand(intake));
        tester.getRightBumper().whileHeld(new IntakeAcquireSetupCommand(intake));

        // Shooter Speed Control
        tester.getDPadUp().whenPressed(new ShooterStopCommand(shooter))
                .whenPressed(new ShooterControlCommand(shooter, 0, Shooter.ShooterMode.NONE));
        tester.getDPadRight().whenPressed(new ShooterControlCommand(shooter, ShooterSettings.INITATION_LINE_RPM,
                Shooter.ShooterMode.SHOOT_FROM_INITIATION_LINE));
        tester.getDPadLeft().whenPressed(
                new ShooterControlCommand(shooter, ShooterSettings.TRENCH_RPM, Shooter.ShooterMode.SHOOT_FROM_TRENCH));

        // Alignment Control
        tester.getDPadDown()
                .whileHeld(new DrivetrainGoalCommand(drivetrain, Alignment.TRENCH_DISTANCE).setNeverFinish())
                .whileHeld(new FeedBallsAutomaticCommand(chimney, funnel, tester));
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
        return tester;
    }

}
