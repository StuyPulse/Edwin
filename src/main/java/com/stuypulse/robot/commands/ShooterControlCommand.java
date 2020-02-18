package com.stuypulse.robot.commands;

import com.stuypulse.robot.Constants;
import com.stuypulse.robot.Constants.Shooting;
import com.stuypulse.robot.subsystems.Shooter;

import edu.wpi.first.wpilibj2.command.InstantCommand;

public class ShooterControlCommand extends InstantCommand {
    public Shooter shooter;
    public double targetVelocity;

    public ShooterControlCommand(Shooter shooter, double targetVelocity) {
        this.shooter = shooter;
        this.targetVelocity = targetVelocity;
    }

    @Override
    public void initialize() {
        shooter.setTargetVelocity(targetVelocity);

        if(targetVelocity == Shooting.TRENCH_RPM || targetVelocity == Shooting.FAR_RPM) {
            shooter.retractHoodSolenoid();
        }
        if(targetVelocity == Shooting.INITATION_LINE_RPM) {
            shooter.extendHoodSolenoid();
        }
    }
}