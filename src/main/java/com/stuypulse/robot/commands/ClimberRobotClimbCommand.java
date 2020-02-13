package com.stuypulse.robot.commands;

import com.revrobotics.CANSparkMax.IdleMode;
import com.stuypulse.robot.subsystems.Climber;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ClimberRobotClimbCommand extends CommandBase {

    private final Climber climber;

    public ClimberRobotClimbCommand(Climber climber) {
        this.climber = climber;
        
        addRequirements(climber);
    }

    @Override 
    public void initialize() {
        climber.setNeutralMode(IdleMode.kBrake);
    }

    @Override
    public void execute() {
        climber.moveLiftDown();
        climber.releaseLiftBrake();
    }

}