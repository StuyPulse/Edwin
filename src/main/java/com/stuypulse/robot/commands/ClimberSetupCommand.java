package com.stuypulse.robot.commands;

import com.revrobotics.CANSparkMax.IdleMode;
import com.stuypulse.robot.subsystems.Climber;
import com.stuypulse.robot.subsystems.Intake;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class ClimberSetupCommand extends SequentialCommandGroup {

    Climber climber;
    Intake intake;

    public ClimberSetupCommand(Climber climber, Intake intake) {
        this.climber = climber;
        this.intake = intake;
        
        addCommands(
            new IntakeRetractCommand(intake),
            new ClimberReleaseBrakeCommand(climber),
            new WaitCommand(0.1),
            new ClimberSetNeutralModeCommand(climber, IdleMode.kCoast)
        );
    }
}

// move down command
// check limit switch and move motor
