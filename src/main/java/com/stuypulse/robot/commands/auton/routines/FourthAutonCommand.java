package com.stuypulse.robot.commands.auton.routines;

import com.stuypulse.robot.Constants;
import com.stuypulse.robot.commands.DrivetrainGoalAligner;
import com.stuypulse.robot.commands.DrivetrainMovementCommand;
import com.stuypulse.robot.commands.DrivetrainPIDAlignmentCommand;
import com.stuypulse.robot.commands.DrivetrainStopCommand;
import com.stuypulse.robot.commands.IntakeAcquireCommand;
import com.stuypulse.robot.subsystems.Drivetrain;
import com.stuypulse.robot.subsystems.Intake;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class FourthAutonCommand extends SequentialCommandGroup {
    public FourthAutonCommand(Drivetrain drivetrain) {
        addCommands(new DrivetrainPIDAlignmentCommand(drivetrain, new DrivetrainGoalAligner(Constants.SHOOT_FROM_START_TO_GOAL)));
        //Add shoot 3
        addCommands(new DrivetrainMovementCommand(drivetrain, Constants.ANGLE_FROM_START_TO_TRENCH));
        addCommands(new DrivetrainMovementCommand(drivetrain, 0, Constants.DISTANCE_FROM_START_TO_TRENCH));
        addCommands(new DrivetrainMovementCommand(drivetrain, -Constants.ANGLE_FROM_START_TO_TRENCH));
        addCommands(new IntakeAcquireCommand(new Intake()));
        addCommands(new DrivetrainMovementCommand(drivetrain, 0, Constants.DISTANCE_FROM_BALL_TO_BALL));
        addCommands(new DrivetrainPIDAlignmentCommand(drivetrain, new DrivetrainGoalAligner(Constants.DISTANCE_FROM_TRENCH_TO_GOAL)));
        //Add shoot 3
        addCommands(new DrivetrainStopCommand(drivetrain));
    }
}