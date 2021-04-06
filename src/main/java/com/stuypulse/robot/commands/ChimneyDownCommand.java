/* Copyright (c) 2021 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.robot.commands;

import com.stuypulse.robot.subsystems.Chimney;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ChimneyDownCommand extends CommandBase {

    private final Chimney chimney;

    public ChimneyDownCommand(Chimney chimney) {
        this.chimney = chimney;

        addRequirements(chimney);
    }

    @Override
    public void execute() {
        chimney.liftDown();
    }

    @Override
    public void end(boolean interrupted) {
        chimney.stop();
    }
}
