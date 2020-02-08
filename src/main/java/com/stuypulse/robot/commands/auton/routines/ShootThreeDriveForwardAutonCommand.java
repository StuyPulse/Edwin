package com.stuypulse.robot.commands.auton.routines;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import com.stuypulse.robot.commands.DrivetrainGoalAligner;
import com.stuypulse.robot.commands.DrivetrainMovementCommand;
import com.stuypulse.robot.commands.DrivetrainPIDAlignmentCommand;
import com.stuypulse.robot.commands.DrivetrainStopCommand;
import com.stuypulse.robot.subsystems.Drivetrain;
import com.stuypulse.robot.Constants;

public class ShootThreeDriveForwardAutonCommand extends SequentialCommandGroup {
    public ShootThreeDriveForwardAutonCommand(Drivetrain drivetrain) {
        addCommands(new DrivetrainPIDAlignmentCommand(drivetrain, new DrivetrainGoalAligner(Constants.DISTANCE_FROM_START_TO_GOAL)));
        
        // TODO: Add shoot 3
        addCommands(new DrivetrainMovementCommand(drivetrain, 0, 12));
        addCommands(new DrivetrainStopCommand(drivetrain));
    }
    
}