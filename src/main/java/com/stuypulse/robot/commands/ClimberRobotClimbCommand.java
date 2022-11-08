/* Copyright (c) 2021 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.robot.commands;

import com.stuypulse.robot.Constants.ClimberSettings;
import com.stuypulse.robot.subsystems.Climber;
import com.stuypulse.robot.subsystems.Intake;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class ClimberRobotClimbCommand extends SequentialCommandGroup {

    public ClimberRobotClimbCommand(Climber climber, Intake intake) {

        addCommands(
                new ClimberReleaseBrakeCommand(climber),
                new WaitCommand(ClimberSettings.SETUP_WAIT_TIME),
                new ClimberWindWinchCommand(climber));
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
