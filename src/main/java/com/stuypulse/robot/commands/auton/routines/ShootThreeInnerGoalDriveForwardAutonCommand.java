package com.stuypulse.robot.commands.auton.routines;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import com.stuypulse.robot.commands.DrivetrainInnerGoalAligner;
import com.stuypulse.robot.commands.DrivetrainMovementCommand;
import com.stuypulse.robot.commands.DrivetrainPIDAlignmentCommand;
import com.stuypulse.robot.subsystems.Drivetrain;
import com.stuypulse.robot.commands.DrivetrainStopCommand;

public class ShootThreeInnerGoalDriveForwardAutonCommand extends SequentialCommandGroup {
    public ShootThreeInnerGoalDriveForwardAutonCommand(Drivetrain drivetrain) {
        addCommands(new DrivetrainPIDAlignmentCommand(drivetrain, new DrivetrainInnerGoalAligner()));
        // TODO: Add shoot 3
        addCommands(new DrivetrainMovementCommand(drivetrain, 0, 12));
        addCommands(new DrivetrainStopCommand(drivetrain));
    }
    
}