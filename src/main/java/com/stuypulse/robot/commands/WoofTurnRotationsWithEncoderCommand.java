/* Copyright (c) 2021 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.robot.commands;

import com.stuypulse.robot.Constants.WoofSettings;
import com.stuypulse.robot.subsystems.Woof;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class WoofTurnRotationsWithEncoderCommand extends CommandBase {

    private final Woof woof;

    /**
     * Creates a new ExampleCommand.
     *
     * @param subsystem The subsystem used by this command.
     */
    public WoofTurnRotationsWithEncoderCommand(Woof woof) {
        this.woof = woof;
        addRequirements(woof);
    }

    @Override
    public void initialize() {
        woof.reset();
    }

    @Override
    public void execute() {
        woof.turn(WoofSettings.TURN_SPEED);
    }

    @Override
    public boolean isFinished() {
        return woof.getControlPanelRotations() >= WoofSettings.TARGET_CONTROL_PANEL_TURNS;
    }

    @Override
    public void end(boolean interrupted) {
        woof.stop();
    }
}
