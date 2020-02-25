package com.stuypulse.robot.commands;

import com.stuypulse.robot.subsystems.Shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ShooterReverseCommand extends CommandBase {
    public Shooter shooter;

    public ShooterReverseCommand(Shooter shooter) {  
        this.shooter = shooter;
    }

    @Override
    public void execute() {
        shooter.reverse();
    }

    @Override
    public void end(boolean interrupted) {
        shooter.stopShooter();
        shooter.stopFeeder();
    }
}