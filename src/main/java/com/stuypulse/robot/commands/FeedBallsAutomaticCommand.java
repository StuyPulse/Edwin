/* Copyright (c) 2021 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.robot.commands;

import com.stuypulse.stuylib.input.Gamepad;

import com.stuypulse.robot.subsystems.Chimney;
import com.stuypulse.robot.subsystems.Funnel;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class FeedBallsAutomaticCommand extends CommandBase {

    private final Chimney chimney;
    private final Funnel funnel;
    private final Gamepad gamepad;

    public FeedBallsAutomaticCommand(Chimney chimney, Funnel funnel, Gamepad gamepad) {
        this.chimney = chimney;
        this.funnel = funnel;
        this.gamepad = gamepad;

        addRequirements(chimney, funnel);
    }

    @Override
    public void execute() {
        if (gamepad.getRawBottomButton()) {
            chimney.liftUp();
            funnel.funnel();
        } else {
            if (!chimney.getUpperChimneyValue()) { // && chimney.getLowerChimneyValue()) {
                chimney.liftUp();
                funnel.funnel();
            } else {
                chimney.stop();
                funnel.stop();
            }
        }
    }

    @Override
    public void end(boolean interrupted) {
        chimney.stop();
        funnel.stop();
    }
}
