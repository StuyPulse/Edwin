package com.stuypulse.robot.commands;

import com.stuypulse.robot.subsystems.Climber;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ClimberWindWinchCommand extends CommandBase {

    private final Climber climber;

    public ClimberWindWinchCommand(Climber climber) {
        this.climber = climber;
        addRequirements(climber);
    }

    @Override
    public void execute() {
        climber.moveLiftDown();
    }

    @Override
    public void end(boolean interrupted) {
        climber.stopClimber();
    }
}