/* Copyright (c) 2021 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.robot.commands.auton.routines;

import com.stuypulse.robot.commands.DrivetrainRamseteCommand;
import com.stuypulse.robot.subsystems.Drivetrain;

public class SlalomPathAutonCommand extends DrivetrainRamseteCommand {
    public SlalomPathAutonCommand(Drivetrain drivetrain) {
        super(drivetrain, "SlalomPath/Slalom.wpilib.json");

        robotRelative();
    }
}
