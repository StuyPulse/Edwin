/* Copyright (c) 2021 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

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
        System.out.println(shooter.isReady());
        System.out.println(shooter.getShooterRPM());
    }

    @Override
    public boolean isFinished() {
        System.err.println("AT TARGET VELOCITY");

        return shooter.isReady();
    }
}
