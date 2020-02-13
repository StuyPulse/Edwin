package com.stuypulse.robot.commands;

import com.stuypulse.robot.subsystems.Shooter;

import edu.wpi.first.wpilibj.command.InstantCommand;

public class ShooterStopCommand extends InstantCommand {
    public Shooter shooter;

    public ShooterStopCommand(Shooter shooter) {
        this.shooter = shooter;
    }

    @Override
    protected void initialize() {
        shooter.stopShooter();
    }
}