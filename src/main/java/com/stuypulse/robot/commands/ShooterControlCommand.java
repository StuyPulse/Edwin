package com.stuypulse.robot.commands;

import com.stuypulse.robot.subsystems.Shooter;
import com.stuypulse.robot.subsystems.Shooter.ShooterMode;

import edu.wpi.first.wpilibj2.command.InstantCommand;

public class ShooterControlCommand extends InstantCommand {
    private final Shooter shooter;
    private final double targetVelocity;
    private final ShooterMode mode;

    public ShooterControlCommand(Shooter shooter, double targetVelocity, ShooterMode mode) {
        this.shooter = shooter;
        this.targetVelocity = targetVelocity;
        this.mode = mode;
    }

    @Override
    public void initialize() {
        shooter.setTargetVelocity(targetVelocity);
        shooter.setShooterMode(mode);
    }
}