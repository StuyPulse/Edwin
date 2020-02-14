package com.stuypulse.robot.commands.auton.routines;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import com.stuypulse.robot.Constants;
import com.stuypulse.robot.commands.DrivetrainMovementCommand;
import com.stuypulse.robot.commands.DrivetrainStopCommand;
import com.stuypulse.robot.subsystems.Drivetrain;

public class MobilityAutonCommand extends SequentialCommandGroup {
    public MobilityAutonCommand(Drivetrain drivetrain) {
        //Constructors
        addCommands(new DrivetrainMovementCommand(drivetrain, 0, Constants.DISTANCE_TO_MOVE_AT_START));
        addCommands(new DrivetrainStopCommand(drivetrain));

        //TODO: DONEEE
    }


}