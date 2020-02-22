package com.stuypulse.robot.commands;

import com.revrobotics.CANSparkMax.IdleMode;
import com.stuypulse.robot.Constants;
import com.stuypulse.robot.subsystems.Climber;
import com.stuypulse.robot.subsystems.Intake;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandGroupBase;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class ClimberRobotClimbCommand extends SequentialCommandGroup {

    private final Climber climber;

    public ClimberRobotClimbCommand(Climber climber, Intake intake) {
        
        this.climber = climber;

        addCommands(
            new ClimberReleaseBrakeCommand(climber),
            new WaitCommand(Constants.CLIMBER_SETUP_WAIT_TIME),
            // new ParallelRaceGroup(
            //     new ClimberUnwindWinchSlowCommand(climber),
            //     new WaitCommand(0.1)
            // ),
            new ClimberWindWinchCommand(climber)
        );
    }

    @Override
    public void end(boolean interrupted) {
        climber.stopClimber();
        climber.enableLiftBrake();
    }

}