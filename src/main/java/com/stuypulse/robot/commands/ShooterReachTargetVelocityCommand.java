package com.stuypulse.robot.commands;

import com.stuypulse.robot.subsystems.Shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ShooterReachTargetVelocityCommand extends CommandBase {

    private final Shooter shooter;

    public ShooterReachTargetVelocityCommand(Shooter shooter) {
        this.shooter = shooter;
    }

    @Override
    public boolean isFinished() {
        return shooter.isAtTargetVelocity();
    }

}