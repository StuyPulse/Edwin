/* Copyright (c) 2021 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.robot.commands;

import com.stuypulse.robot.Constants.WoofSettings;
import com.stuypulse.robot.subsystems.LEDController;
import com.stuypulse.robot.subsystems.LEDController.LEDColor;
import com.stuypulse.robot.subsystems.Woof;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class WoofTurnRotationsWithEncoderCommand extends CommandBase {

    private final Woof woof;
    private final LEDController ledController;

    /**
     * Creates a new ExampleCommand.
     *
     * @param subsystem The subsystem used by this command.
     */
    public WoofTurnRotationsWithEncoderCommand(Woof woof, LEDController led) {
        this.woof = woof;
        this.ledController = led;
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
        boolean isFinished =
                woof.getControlPanelRotations() >= WoofSettings.TARGET_CONTROL_PANEL_TURNS;

        if (isFinished) {
            ledController.setColor(LEDColor.GREEN_SOLID);
        } else {
            ledController.setColor(LEDColor.YELLOW_SOLID);
        }

        return isFinished;
    }

    @Override
    public void end(boolean interrupted) {
        woof.stop();
    }
}
