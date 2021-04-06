/* Copyright (c) 2021 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.robot.commands;

import com.stuypulse.robot.subsystems.Chimney;
import com.stuypulse.robot.subsystems.Funnel;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

public class FeedBallsCommand extends ParallelCommandGroup {

    public FeedBallsCommand(Funnel funnel, Chimney chimney) {
        //addCommands(new FunnelFunnelCommand(funnel));
        addCommands(new ChimneyUpCommand(chimney));
    }
}
