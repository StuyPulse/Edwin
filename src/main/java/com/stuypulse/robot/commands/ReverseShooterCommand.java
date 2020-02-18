package com.stuypulse.robot.commands;

import com.stuypulse.robot.subsystems.Shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ReverseShooterCommand extends CommandBase {
    public Shooter shooter;

    public ReverseShooterCommand(Shooter shooter) {  
        this.shooter = shooter;
    }

    @Override
    public void execute() {
        shooter.setShooterSpeed(-1);
    }

    @Override
    public void end(boolean interrupted) {
        shooter.stopShooter();
    }
}