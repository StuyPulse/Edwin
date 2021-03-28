/* Copyright (c) 2021 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class TimeoutCommand extends ParallelRaceGroup {

    public TimeoutCommand(CommandBase command, double timeout) {
        addCommands(
                command, new WaitCommand(timeout) // timeout in seconds
                );
    }
}
