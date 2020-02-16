package com.stuypulse.robot.commands;

import com.revrobotics.CANSparkMax.IdleMode;
import com.stuypulse.robot.Constants;
import com.stuypulse.robot.subsystems.Climber;
import com.stuypulse.robot.subsystems.Intake;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class ClimberSetupCommand extends SequentialCommandGroup {

    Climber climber;

    public ClimberSetupCommand(Climber climber, Intake intake) {

        this.climber = climber;

        addCommands(
            new IntakeRetractCommand(intake),
            new ClimberReleaseBrakeCommand(climber),
            new WaitCommand(Constants.CLIMBER_SETUP_WAIT_TIME),
            new ClimberUnwindWinchCommand(climber)
        );
    }

    @Override
    public void end(boolean interrupted) {
        climber.enableLiftBrake();
    }
}
