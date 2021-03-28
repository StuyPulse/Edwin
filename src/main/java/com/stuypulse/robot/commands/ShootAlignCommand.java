/* Copyright (c) 2021 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.robot.commands;

import com.stuypulse.robot.subsystems.Drivetrain;
import com.stuypulse.robot.subsystems.Shooter;
import com.stuypulse.robot.subsystems.Shooter.ShooterMode;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

public class ShootAlignCommand extends ParallelCommandGroup {

    public ShootAlignCommand(Drivetrain drivetrain, Shooter shooter, ShooterMode mode) {
        if (mode.distance.doubleValue() > 0) {
            addCommands(
                    new ShooterControlCommand(shooter, mode),
                    new DrivetrainGoalCommand(drivetrain, mode.distance));
        } else {
            addCommands(new ShooterControlCommand(shooter, mode));
        }
    }
}
