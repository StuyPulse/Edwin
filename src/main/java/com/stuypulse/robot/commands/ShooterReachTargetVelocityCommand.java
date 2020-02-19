package com.stuypulse.robot.commands;

import com.stuypulse.robot.subsystems.Shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ShooterReachTargetVelocityCommand extends CommandBase {

    private final Shooter shooter;

    public ShooterReachTargetVelocityCommand(Shooter shooter) {
        this.shooter = shooter;
    }

    @Override
    public void execute() {
        System.out.println(shooter.isAtTargetVelocity());
        System.out.println(shooter.getCurrentShooterVelocityInRPM());
    }

    @Override
    public boolean isFinished() {
        System.out.println("AT TARGET VELOCITY");
        System.out.println("AT TARGET VELOCITY");
        System.out.println("AT TARGET VELOCITY");
        System.out.println("AT TARGET VELOCITY");
        System.out.println("AT TARGET VELOCITY");
        System.out.println("AT TARGET VELOCITY");
        System.out.println("AT TARGET VELOCITY");
        System.out.println("AT TARGET VELOCITY");
        System.out.println("AT TARGET VELOCITY");
        System.out.println("AT TARGET VELOCITY");
        System.out.println("AT TARGET VELOCITY");
        System.out.println("AT TARGET VELOCITY");
        System.out.println("AT TARGET VELOCITY");
        System.out.println("AT TARGET VELOCITY");
        System.out.println("AT TARGET VELOCITY");
        System.out.println("AT TARGET VELOCITY");
        System.out.println("AT TARGET VELOCITY");
        System.out.println("AT TARGET VELOCITY");
        System.out.println("AT TARGET VELOCITY");
        System.out.println("AT TARGET VELOCITY");
        System.out.println("AT TARGET VELOCITY");
        System.out.println("AT TARGET VELOCITY");
        System.out.println("AT TARGET VELOCITY");
        System.out.println("AT TARGET VELOCITY");
        System.out.println("AT TARGET VELOCITY");
        System.out.println("AT TARGET VELOCITY");
        System.out.println("AT TARGET VELOCITY");
        System.out.println("AT TARGET VELOCITY");
        System.out.println("AT TARGET VELOCITY");
        System.out.println("AT TARGET VELOCITY");
        System.out.println("AT TARGET VELOCITY");
        System.out.println("AT TARGET VELOCITY");
        System.out.println("AT TARGET VELOCITY");
        System.out.println("AT TARGET VELOCITY");
        System.out.println("AT TARGET VELOCITY");
        System.out.println("AT TARGET VELOCITY");
        System.out.println("AT TARGET VELOCITY");
        System.out.println("AT TARGET VELOCITY");
        System.out.println("AT TARGET VELOCITY");
        System.out.println("AT TARGET VELOCITY");
        System.out.println("AT TARGET VELOCITY");
        System.out.println("AT TARGET VELOCITY");
        System.out.println("AT TARGET VELOCITY");
        System.out.println("AT TARGET VELOCITY");
        System.out.println("AT TARGET VELOCITY");
        System.out.println("AT TARGET VELOCITY");
        System.out.println("AT TARGET VELOCITY");
        System.out.println("AT TARGET VELOCITY");
        System.out.println("AT TARGET VELOCITY");
        System.out.println("AT TARGET VELOCITY");
        System.out.println("AT TARGET VELOCITY");
        System.out.println("AT TARGET VELOCITY");
        System.out.println("AT TARGET VELOCITY");
        System.out.println("AT TARGET VELOCITY");

        return shooter.isAtTargetVelocity();
    }

}