package com.stuypulse.robot.commands;

import com.stuypulse.robot.Constants;
import com.stuypulse.robot.subsystems.Shooter;
import com.stuypulse.stuylib.input.WPIGamepad;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ShooterControlCommand extends CommandBase {
    public Shooter shooter;
    public WPIGamepad gamepad;
    public double targetVelocity;

    public ShooterControlCommand(Shooter shooter, WPIGamepad gamepad) {
        this.shooter = shooter;
        this.gamepad = gamepad;
        addRequirements(shooter);
    }

    @Override
    public void execute() {
        // If up dpad button is pressed, shoot from far (Steph Curry)
        if (gamepad.getRawDPadUp()) {
            targetVelocity = Constants.SHOOT_FROM_FAR_RPM;

            shooter.retractHoodSolenoid();  
            shooter.runShooter();
            shooter.runFeeder();
            
            if (shooter.getCurrentShooterVelocityInRPM() > targetVelocity) {
                gamepad.setRumble(1);
            }
        }
        
        // If down dpad button is pressed, shoot from initiation line
        if (gamepad.getRawDPadDown()) {
            targetVelocity = Constants.SHOOT_FROM_INITATION_LINE_RPM;

            shooter.retractHoodSolenoid();
            shooter.runShooter();
            shooter.runFeeder();

            if (shooter.getCurrentShooterVelocityInRPM() > targetVelocity) {
                gamepad.setRumble(1);
            }
        }

        // If left dpad button is pressed, shoot from trench
        if (gamepad.getRawDPadLeft()) {
            targetVelocity = Constants.SHOOT_FROM_TRENCH_RPM;

            shooter.extendHoodSolenoid();
            shooter.runShooter();
            shooter.runFeeder();

            if (shooter.getCurrentShooterVelocityInRPM() > targetVelocity) {
                gamepad.setRumble(1);
            }
        }

        // If right dpad button is pressed, stop shooter
        if (gamepad.getRawDPadRight()) {
            shooter.stopShooter();
        }
    } 

    @Override
    public boolean isFinished() {
        return false;
    }
}