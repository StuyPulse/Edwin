/* Copyright (c) 2021 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.robot.commands;

import com.stuypulse.stuylib.network.SmartBoolean;
import com.stuypulse.stuylib.network.SmartNumber;

import com.stuypulse.robot.subsystems.Drivetrain;
import com.stuypulse.robot.subsystems.Shooter;

import edu.wpi.first.wpilibj.util.Units;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

public class ShootAlignCommand extends ParallelCommandGroup {

    public enum ShooterMode {
        // THIS IS THE INITIATION LINE SHOT
        GREEN_ZONE(
                new SmartNumber("Shooting/Green Zone/Distance", Units.feetToMeters(7)),
                new SmartNumber("Shooting/Green Zone/RPM", 2075),
                new SmartBoolean("Shooting/Green Zone/Hood Extended", true)),

        // TODO: TUNE THIS
        YELLOW_ZONE(
                new SmartNumber("Shooting/Yellow Zone/Distance", Units.feetToMeters(11)),
                new SmartNumber("Shooting/Yellow Zone/RPM", 2500),
                new SmartBoolean("Shooting/Yellow Zone/Hood Extended", true)),

        // THIS IS THE TRENCH SHOT
        BLUE_ZONE(
                new SmartNumber("Shooting/Blue Zone/Distance", Units.feetToMeters(16.5)),
                new SmartNumber("Shooting/Blue Zone/RPM", 3000),
                new SmartBoolean("Shooting/Blue Zone/Hood Extended", false)),

        // TODO: TUNE THIS
        RED_ZONE(
                new SmartNumber("Shooting/Red Zone/Distance", Units.feetToMeters(21)),
                new SmartNumber("Shooting/Red Zone/RPM", 3750),
                new SmartBoolean("Shooting/Red Zone/Hood Extended", false));

        public final SmartNumber distance;
        public final SmartNumber rpm;
        public final SmartBoolean extendHood;

        ShooterMode(SmartNumber distance, SmartNumber rpm, SmartBoolean extendHood) {
            this.distance = distance;
            this.rpm = rpm;
            this.extendHood = extendHood;
        }
    }

    public ShootAlignCommand(Drivetrain drivetrain, Shooter shooter, ShooterMode mode) {
        addCommands(
                new ShooterControlCommand(shooter, mode.rpm, mode.extendHood),
                new DrivetrainGoalCommand(drivetrain, mode.distance));
    }
}
