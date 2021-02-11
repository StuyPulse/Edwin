package com.stuypulse.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class TimeoutCommand extends ParallelRaceGroup {

    public TimeoutCommand(CommandBase command, double timeout) {
        addCommands(command, new WaitCommand(timeout) // timeout in seconds
        );
    }

}