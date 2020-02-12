package com.stuypulse.robot.commands.auton.routines;

import com.stuypulse.robot.subsystems.Drivetrain;
import com.stuypulse.robot.subsystems.Intake;
import com.stuypulse.robot.commands.DrivetrainGoalAligner;
import com.stuypulse.robot.commands.DrivetrainLowGearCommand;
import com.stuypulse.robot.commands.DrivetrainMovementCommand;
import com.stuypulse.robot.commands.DrivetrainPIDAlignmentCommand;
import com.stuypulse.robot.commands.DrivetrainStopCommand;
import com.stuypulse.robot.commands.IntakeAcquireCommand;
import com.stuypulse.robot.Constants;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;


public class FifthAutonCommand extends SequentialCommandGroup {
    public FifthAutonCommand(Drivetrain drivetrain) {
        addCommands(new DrivetrainPIDAlignmentCommand(drivetrain, new DrivetrainGoalAligner(Constants.SHOOT_FROM_START_TO_GOAL)));
        //Add shoot 3
        addCommands(new DrivetrainMovementCommand(drivetrain, Constants.ANGLE_FROM_START_TO_TRENCH));
        addCommands(new DrivetrainMovementCommand(drivetrain, 0, Constants.DISTANCE_FROM_START_TO_TRENCH));
        addCommands(new DrivetrainMovementCommand(drivetrain, -Constants.ANGLE_FROM_START_TO_TRENCH));
        addCommands(new IntakeAcquireCommand(new Intake()));
        addCommands(new DrivetrainMovementCommand(drivetrain, 0, Constants.DISTANCE_FROM_BALL_TO_BALL));

        addCommands(new DrivetrainMovementCommand(drivetrain, Constants.ANGLE_FROM_TRENCH_TO_RDVS));
        addCommands(new IntakeAcquireCommand(new Intake()));
        addCommands(new DrivetrainMovementCommand(drivetrain, 0, Constants.DISTANCE_FROM_TRENCH_TO_RDVS));
        addCommands(new DrivetrainMovementCommand(drivetrain, Constants.ANGLE_FROM_RDVS_TO_TWO_BALL));
        addCommands(new DrivetrainMovementCommand(drivetrain, 0, Constants.DISTANCE_BETWEEN_TWO_BALL));
        addCommands(new DrivetrainMovementCommand(drivetrain, Constants.ANGLE_FROM_RDVS_TO_GOAL));
        addCommands(new DrivetrainPIDAlignmentCommand(drivetrain, new DrivetrainGoalAligner(20)));
        //shoot
        addCommands(new DrivetrainStopCommand(drivetrain));
    }
}