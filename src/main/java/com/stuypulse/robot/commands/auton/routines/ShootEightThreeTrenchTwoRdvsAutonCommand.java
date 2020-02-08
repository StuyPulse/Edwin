package com.stuypulse.robot.commands.auton.routines;

import com.stuypulse.robot.subsystems.Drivetrain;
import com.stuypulse.robot.commands.DrivetrainGoalAligner;
import com.stuypulse.robot.commands.DrivetrainLowGearCommand;
import com.stuypulse.robot.commands.DrivetrainMovementCommand;
import com.stuypulse.robot.commands.DrivetrainPIDAlignmentCommand;
import com.stuypulse.robot.Constants;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;


public class ShootEightThreeTrenchTwoRdvsAutonCommand extends SequentialCommandGroup {
    public ShootEightThreeTrenchTwoRdvsAutonCommand(Drivetrain drivetrain) {
        //addCommands(new DrivetrainLowGearCommand(drivetrain));
        addCommands(new DrivetrainMovementCommand(drivetrain, Constants.ANGLE_FROM_START_TO_TRENCH));
        addCommands(new DrivetrainMovementCommand(drivetrain, 0, Constants.DISTANCE_FROM_START_TO_TRENCH));
        addCommands(new DrivetrainMovementCommand(drivetrain, -Constants.ANGLE_FROM_START_TO_TRENCH));
        for (int i = 0; i < 3; i ++) {
            addCommands(new DrivetrainMovementCommand(drivetrain, 0, Constants.DISTANCE_FROM_BALL_TO_BALL));
            }
        addCommands(new DrivetrainMovementCommand(drivetrain, Constants.ANGLE_FROM_TRENCH_TO_RDVS));
        addCommands(new DrivetrainMovementCommand(drivetrain, 0, Constants.DISTANCE_FROM_TRENCH_TO_RDVS));
        addCommands(new DrivetrainMovementCommand(drivetrain, Constants.ANGLE_FROM_RDVS_TO_GOAL));
        addCommands(new DrivetrainPIDAlignmentCommand(drivetrain, new DrivetrainGoalAligner(20)));
        //shoot
    }
}