package com.stuypulse.robot.commands;

import com.stuypulse.robot.subsystems.Climber;

import edu.wpi.first.wpilibj2.command.InstantCommand;

public class ClimberToggleLiftBrakeCommand extends InstantCommand {

    private final Climber climber;

    public ClimberToggleLiftBrakeCommand(Climber climber) {
        this.climber = climber;

        addRequirements(climber);
    }

    @Override
    public void initialize() {
        climber.toggleLiftBrake();
    }
}