package com.stuypulse.robot.commands;

import com.stuypulse.robot.subsystems.Shooter;

import edu.wpi.first.wpilibj.command.InstantCommand;

public class ReverseShooterCommand extends InstantCommand {
    public Shooter shooter;

    public ReverseShooterCommand(Shooter shooter) {  
        this.shooter = shooter;
    }

    @Override
    protected void initialize() {
        shooter.reverseShooter();
    }
}