package com.stuypulse.robot.commands.auton.routines;

import com.stuypulse.robot.Constants;
import com.stuypulse.robot.commands.DrivetrainMovementCommand;
import com.stuypulse.robot.commands.DrivetrainStopCommand;
import com.stuypulse.robot.subsystems.Drivetrain;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class MobilityAutonCommand extends SequentialCommandGroup {
    public MobilityAutonCommand(Drivetrain drivetrain, boolean towardIntake) {
        //Constructors
        addCommands(
            new DrivetrainMovementCommand(drivetrain, 0, towardIntake? Constants.DISTANCE_TO_MOVE_AT_START : -Constants.DISTANCE_TO_MOVE_AT_START),
            new DrivetrainStopCommand(drivetrain)
        );
        //TODO: DONEEE
    }


}