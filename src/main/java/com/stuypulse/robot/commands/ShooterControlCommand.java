/* Copyright (c) 2021 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.robot.commands;

import com.stuypulse.stuylib.network.SmartBoolean;

import com.stuypulse.robot.subsystems.Shooter;

import edu.wpi.first.wpilibj2.command.InstantCommand;

public class ShooterControlCommand extends InstantCommand {

    private final Shooter shooter;
    private final Number targetRPM;
    private final SmartBoolean extendHood;

    public ShooterControlCommand(Shooter shooter, Number targetRPM, SmartBoolean extendHood) {
        this.shooter = shooter;
        this.targetRPM = targetRPM;
        this.extendHood = extendHood;
    }

    @Override
    public void initialize() {
        shooter.setTargetRPM(this.targetRPM.doubleValue());

        if (this.extendHood.get()) {
            shooter.extendHoodSolenoid();
        } else {
            shooter.retractHoodSolenoid();
        }
    }
}
