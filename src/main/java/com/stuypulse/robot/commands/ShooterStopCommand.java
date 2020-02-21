package com.stuypulse.robot.commands;

import com.stuypulse.robot.subsystems.Shooter;

import edu.wpi.first.wpilibj2.command.InstantCommand;

public class ShooterStopCommand extends InstantCommand {
    public Shooter shooter;

    public ShooterStopCommand(Shooter shooter) {
        this.shooter = shooter;
    }

    @Override
    public void initialize() {
        shooter.stopShooter();
    }
}