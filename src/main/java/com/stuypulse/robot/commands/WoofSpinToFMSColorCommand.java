/* Copyright (c) 2021 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.robot.commands;

import com.stuypulse.robot.Constants.WoofSettings;
import com.stuypulse.robot.subsystems.Woof;
import com.stuypulse.robot.subsystems.ColorSensor.WColor;

import edu.wpi.first.wpilibj2.command.CommandBase;

// this can extend from a WoofSpinToColorCommand that has a protected wcolor 
public class WoofSpinToFMSColorCommand extends CommandBase {

    private Woof woof;

    public WoofSpinToFMSColorCommand(Woof woof) {
        this.woof = woof;
        addRequirements(woof);
    }

    @Override
    public void execute() {
        final WColor goal = woof.getTargetColor(); // <-- reads from the FMS
        final WColor curr = woof.getCurrentColor();

        // If any color is not reporting, skip 
        if (goal == WColor.NONE || curr == WColor.NONE)
            return;
        
        // If the goal is behind us (1 away), spin backwards
        if (curr.getNextColor() == goal)
            woof.turn(-WoofSettings.TURN_SPEED);
        // If the goal is more than (1 away), spin forwards
        else
            woof.turn(+WoofSettings.TURN_SPEED);

    }

    @Override
    public boolean isFinished() {
        return woof.getTargetColor() == woof.getCurrentColor();
    }

    @Override
    public void end(boolean interrupted) {
        woof.stop();
    }
}