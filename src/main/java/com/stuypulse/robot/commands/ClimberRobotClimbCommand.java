package com.stuypulse.robot.commands;

import com.stuypulse.robot.subsystems.Climber;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ClimberRobotClimbCommand extends CommandBase {

    Climber climber;

    public ClimberRobotClimbCommand(Climber climber) {
        this.climber = climber;
    }

    @Override
    public void execute() {
        climber.moveLiftDown();
    }

}