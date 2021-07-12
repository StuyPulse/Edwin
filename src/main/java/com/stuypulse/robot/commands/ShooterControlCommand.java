/* Copyright (c) 2021 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.robot.commands;

import com.stuypulse.robot.subsystems.Shooter;
import com.stuypulse.robot.subsystems.Shooter.ShooterMode;

import edu.wpi.first.wpilibj2.command.InstantCommand;

public class ShooterControlCommand extends InstantCommand {

    private final Shooter shooter;
    private final ShooterMode mode;

    public ShooterControlCommand(Shooter shooter, ShooterMode mode) {
        this.shooter = shooter;
        this.mode = mode;
        
        addRequirements(shooter);
    }

    @Override
    public void initialize() {
        this.shooter.setMode(this.mode);
    }
}
