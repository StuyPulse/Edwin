package com.stuypulse.robot.commands;

import com.stuypulse.robot.Constants;
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

        if(targetVelocity == Constants.SHOOT_FROM_TRENCH_RPM || targetVelocity == Constants.SHOOT_FROM_FAR_RPM) {
            shooter.retractHoodSolenoid();
        }
        if(targetVelocity == Constants.SHOOT_FROM_INITATION_LINE_RPM) {
            shooter.extendHoodSolenoid();
        }
    }
}