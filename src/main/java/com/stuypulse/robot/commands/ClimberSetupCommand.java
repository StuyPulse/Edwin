package com.stuypulse.robot.commands;

import com.revrobotics.CANSparkMax.IdleMode;
import com.stuypulse.robot.Constants;
import com.stuypulse.robot.subsystems.Climber;
import com.stuypulse.robot.subsystems.Intake;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class ClimberSetupCommand extends SequentialCommandGroup {

    public ClimberSetupCommand(Climber climber, Intake intake) {

        addCommands(
            new IntakeRetractCommand(intake),
            new ClimberReleaseBrakeCommand(climber),
            new WaitCommand(Constants.CLIMBER_SETUP_WAIT_TIME),
            new ClimberSetNeutralModeCommand(climber, IdleMode.kCoast)
        );
    }
}