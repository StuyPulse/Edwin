/* Copyright (c) 2021 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.robot.commands;

import com.stuypulse.robot.subsystems.Funnel;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class FunnelFunnelCommand extends CommandBase {

    private final Funnel funnel;

    public FunnelFunnelCommand(Funnel funnel) {
        this.funnel = funnel;
        addRequirements(funnel);
    }

    @Override
    public void execute() {
        funnel.funnel();
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        funnel.stop();
    }
}
