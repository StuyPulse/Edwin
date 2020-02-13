package com.stuypulse.robot.commands;

import com.stuypulse.robot.subsystems.Climber;

import edu.wpi.first.wpilibj2.command.InstantCommand;

public class ClimberReleaseBrakeCommand extends InstantCommand {

    private final Climber climber;

    public ClimberReleaseBrakeCommand(Climber climber) {
        this.climber = climber;

        addRequirements(climber);
    }

    @Override
    public void initialize() {
        climber.releaseLiftBrake();
    }

}