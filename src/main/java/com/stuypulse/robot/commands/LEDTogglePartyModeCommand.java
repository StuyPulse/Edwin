/* Copyright (c) 2021 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.robot.commands;

import com.stuypulse.robot.util.LEDController;

import edu.wpi.first.wpilibj2.command.InstantCommand;

public class LEDTogglePartyModeCommand extends InstantCommand {

    private final LEDController controller;

    public LEDTogglePartyModeCommand(LEDController controller) {
        this.controller = controller;
    }

    @Override
    public void initialize() {
        controller.togglePartyMode();
        if (controller.inPartyMode()) controller.startParty();
    }
}
