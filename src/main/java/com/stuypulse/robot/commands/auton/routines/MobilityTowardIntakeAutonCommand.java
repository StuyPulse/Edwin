package com.stuypulse.robot.commands.auton.routines;

import com.stuypulse.robot.Constants;
import com.stuypulse.robot.commands.DrivetrainMovementCommand;
import com.stuypulse.robot.commands.DrivetrainStopCommand;
import com.stuypulse.robot.subsystems.Drivetrain;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class MobilityTowardIntakeAutonCommand extends SequentialCommandGroup {
    public MobilityTowardIntakeAutonCommand(Drivetrain drivetrain) {
        addCommands(
            new DrivetrainMovementCommand(drivetrain, 0, Constants.DISTANCE_TO_MOVE_AT_START),
            new DrivetrainStopCommand(drivetrain)
        );
    }


}