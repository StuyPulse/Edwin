/* Copyright (c) 2021 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.robot.commands;

import com.stuypulse.robot.Constants.ClimberSettings;
import com.stuypulse.robot.subsystems.Climber;
import com.stuypulse.robot.subsystems.Intake;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class ClimberRobotClimbCommand extends SequentialCommandGroup {

    private final Climber climber;

    public ClimberRobotClimbCommand(Climber climber, Intake intake) {

        this.climber = climber;

        addCommands(
                new ClimberReleaseBrakeCommand(climber),
                new WaitCommand(ClimberSettings.SETUP_WAIT_TIME),
                new ClimberWindWinchCommand(climber));
    }

    @Override
    public void end(boolean interrupted) {
        climber.stopClimber();
        climber.enableLiftBrake();
    }
}
