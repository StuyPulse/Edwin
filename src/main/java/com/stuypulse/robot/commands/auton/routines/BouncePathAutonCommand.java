/* Copyright (c) 2021 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.robot.commands.auton.routines;

import com.stuypulse.robot.commands.DrivetrainRamseteCommand;
import com.stuypulse.robot.subsystems.Drivetrain;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class BouncePathAutonCommand extends SequentialCommandGroup {

    public BouncePathAutonCommand(Drivetrain drivetrain) {
        addCommands(
                new DrivetrainRamseteCommand(drivetrain, "BouncePath/Bounce1.wpilib.json")
                        .robotRelative(),
                new DrivetrainRamseteCommand(drivetrain, "BouncePath/Bounce2.wpilib.json")
                        .fieldRelative(),
                new DrivetrainRamseteCommand(drivetrain, "BouncePath/Bounce3.wpilib.json")
                        .fieldRelative(),
                new DrivetrainRamseteCommand(drivetrain, "BouncePath/Bounce4.wpilib.json")
                        .fieldRelative());
    }
}
