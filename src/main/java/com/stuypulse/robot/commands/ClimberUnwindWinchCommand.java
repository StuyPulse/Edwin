package com.stuypulse.robot.commands;

import com.stuypulse.robot.subsystems.Climber;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ClimberUnwindWinchCommand extends CommandBase {

    Climber climber;

    public ClimberUnwindWinchCommand(Climber climber) {
        this.climber = climber;
        addRequirements(climber);
    }

    @Override
    public void execute() {
        climber.moveLiftUp();
    }
} 