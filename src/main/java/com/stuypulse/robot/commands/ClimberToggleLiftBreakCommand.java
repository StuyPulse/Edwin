package com.stuypulse.robot.commands; 

import com.stuypulse.robot.subsystems.Climber;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ClimberToggleLiftBreakCommand extends CommandBase {
    public Climber climber;

    public ClimberToggleLiftBreakCommand(Climber climber) {
        this.climber = climber;
        addRequirements(climber);
    }

    @Override
    public void initialize() {
        climber.toggleLiftBrake();
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public void end(boolean interrupted) {
    }

}