/* Copyright (c) 2021 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.robot.commands;

import com.stuypulse.stuylib.input.Gamepad;

import com.stuypulse.robot.subsystems.Woof;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class WoofManualControlCommand extends CommandBase {

    private final Woof woof;
    private final Gamepad gamepad;

    public WoofManualControlCommand(Woof woof, Gamepad gamepad) {
        this.woof = woof;
        this.gamepad = gamepad;

        addRequirements(woof);
    }

    @Override
    public void execute() {
        if (Math.abs(gamepad.getRightX()) > 0.1) {
            woof.turn(gamepad.getRightX());
        } else {
            woof.stop();
        }
    }

    @Override
    public void end(boolean interrupted) {
        woof.stop();
        woof.reset();
    }
}
