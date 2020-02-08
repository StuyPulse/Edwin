package com.stuypulse.robot.commands.auton.routines;

import com.stuypulse.robot.Constants;
import com.stuypulse.robot.commands.DrivetrainInnerGoalAligner;
import com.stuypulse.robot.commands.DrivetrainMovementCommand;
import com.stuypulse.robot.commands.DrivetrainPIDAlignmentCommand;
import com.stuypulse.robot.subsystems.Drivetrain;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class ShootSixInnerGoalAcquireThreeAutonCommand extends SequentialCommandGroup {
    public ShootSixInnerGoalAcquireThreeAutonCommand(Drivetrain drivetrain) {
        addCommands(new DrivetrainPIDAlignmentCommand(drivetrain, new DrivetrainInnerGoalAligner()));
        //Add shoot 3
        addCommands(new DrivetrainMovementCommand(drivetrain, Constants.ANGLE_FROM_START_TO_TRENCH));
        addCommands(new DrivetrainMovementCommand(drivetrain, 0, Constants.DISTANCE_FROM_START_TO_TRENCH));
        addCommands(new DrivetrainMovementCommand(drivetrain, 0, -Constants.DISTANCE_FROM_START_TO_TRENCH));
        for (int i = 0; i < 3; i ++) {
            addCommands(new DrivetrainMovementCommand(drivetrain, 0, Constants.DISTANCE_FROM_BALL_TO_BALL));
           }
        addCommands(new DrivetrainPIDAlignmentCommand(drivetrain, new DrivetrainInnerGoalAligner()));
        //Add shoot 3
    }
}