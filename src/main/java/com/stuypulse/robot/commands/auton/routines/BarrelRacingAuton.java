/* Copyright (c) 2021 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.robot.commands.auton.routines;

import com.stuypulse.robot.commands.DrivetrainRamseteCommand;
import com.stuypulse.robot.subsystems.Drivetrain;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

/** robot becomes sentient */
public class BarrelRacingAuton extends SequentialCommandGroup {

    private static final String BARREL_PATH = "BarrelRacingPath/BarrelRacing.wpilib.json";

    public BarrelRacingAuton(Drivetrain drivetrain) {
        addCommands(new DrivetrainRamseteCommand(drivetrain, BARREL_PATH).robotRelative());
    }
}
