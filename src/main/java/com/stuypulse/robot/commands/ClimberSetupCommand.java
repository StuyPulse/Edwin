/* Copyright (c) 2021 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.robot.commands;

import com.stuypulse.robot.Constants.ClimberSettings;
import com.stuypulse.robot.subsystems.Climber;
import com.stuypulse.robot.subsystems.Intake;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class ClimberSetupCommand extends SequentialCommandGroup {

    public ClimberSetupCommand(Climber climber, Intake intake) {

        addCommands(
                new ClimberReleaseBrakeCommand(climber),
                new WaitCommand(ClimberSettings.SETUP_WAIT_TIME),
                new ParallelRaceGroup(
                        new ClimberWindWinchSlowCommand(climber), new WaitCommand(0.1)),
                new ClimberUnwindWinchCommand(climber));

        // Finished
        addCommands(
                new InstantCommand(() -> {
                    climber.stopClimber();
                }, climber),
                new InstantCommand(() -> {
                    climber.enableLiftBrake();
                }, climber));
    }
}
