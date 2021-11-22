/* Copyright (c) 2021 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.robot.commands;

import com.stuypulse.robot.Constants.WoofSettings;
import com.stuypulse.robot.subsystems.ColorSensor.WColor;
import com.stuypulse.robot.subsystems.LEDController;
import com.stuypulse.robot.subsystems.LEDController.LEDColor;
import com.stuypulse.stuylib.streams.filters.IFilter;
import com.stuypulse.stuylib.streams.filters.LowPassFilter;
import com.stuypulse.robot.subsystems.Woof;

import edu.wpi.first.wpilibj2.command.CommandBase;

// this can extend from a WoofSpinToColorCommand that has a protected wcolor
public class WoofSpinToFMSColorCommand extends CommandBase {

    private Woof woof;
    private LEDController ledController;

    private IFilter turnFilter;

    public WoofSpinToFMSColorCommand(Woof woof, LEDController led) {
        this.woof = woof;
        this.ledController = led;

        this.turnFilter = new LowPassFilter(WoofSettings.TURN_FILTER);

        addRequirements(woof);
    }

    private void turn(double value) {
        woof.turn(turnFilter.get(value));
    }

    @Override
    public void execute() {
        final WColor goal = woof.getTargetColor(); // <-- reads from the FMS
        final WColor curr = woof.getCurrentColor();

        // If any color is not reporting, skip
        if (goal == WColor.NONE || curr == WColor.NONE) return;

        // If we are in front of the goal (1 away), spin backwards...
        if (curr == goal.getNextColor()) turn(-WoofSettings.TURN_SPEED);
        // Otherwise, spin forwards
        else turn(+WoofSettings.TURN_SPEED);
    }

    @Override
    public boolean isFinished() {
        boolean isFinished = woof.getTargetColor() == woof.getCurrentColor();

        if (isFinished) {
            ledController.setColor(LEDColor.GREEN_SOLID);
        } else {
            ledController.setColor(LEDColor.YELLOW_PULSE);
        }

        return isFinished;
    }

    @Override
    public void end(boolean interrupted) {
        woof.stop();
    }
}
