/* Copyright (c) 2021 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.robot.commands;

import com.stuypulse.robot.subsystems.Intake;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class IntakeAcquireSetupCommand extends SequentialCommandGroup {

    public IntakeAcquireSetupCommand(Intake intake) {

        addCommands(
                new IntakeExtendCommand(intake),
                new WaitCommand(0.1),
                new IntakeAcquireCommand(intake));
    }
}
