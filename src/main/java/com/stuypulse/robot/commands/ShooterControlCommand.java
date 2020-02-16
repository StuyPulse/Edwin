package com.stuypulse.robot.commands;

import com.stuypulse.robot.subsystems.Shooter;

import edu.wpi.first.wpilibj.command.InstantCommand;

public class ShooterControlCommand extends InstantCommand {
    public Shooter shooter;
    public double targetVelocity;

    public ShooterControlCommand(Shooter shooter, double targetVelocity) {
        this.shooter = shooter;
        this.targetVelocity = targetVelocity;
    }

    @Override
    protected void initialize() {
        shooter.setTargetVelocity(targetVelocity);
    }
}