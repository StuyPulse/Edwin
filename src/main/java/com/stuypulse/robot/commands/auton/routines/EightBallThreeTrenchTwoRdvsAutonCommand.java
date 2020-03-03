package com.stuypulse.robot.commands.auton.routines;

import com.stuypulse.robot.Constants;
import com.stuypulse.robot.commands.DrivetrainAlignmentCommand;
import com.stuypulse.robot.commands.DrivetrainGoalAligner;
import com.stuypulse.robot.commands.DrivetrainInnerGoalAligner;
import com.stuypulse.robot.commands.DrivetrainMovementCommand;
import com.stuypulse.robot.commands.DrivetrainStopCommand;
import com.stuypulse.robot.commands.IntakeAcquireCommand;
import com.stuypulse.robot.subsystems.Drivetrain;
import com.stuypulse.robot.subsystems.Intake;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;


public class EightBallThreeTrenchTwoRdvsAutonCommand extends SequentialCommandGroup {
    public EightBallThreeTrenchTwoRdvsAutonCommand(Drivetrain drivetrain) {
        addCommands(
            new DrivetrainAlignmentCommand(drivetrain, new DrivetrainInnerGoalAligner(Constants.SHOOT_FROM_START_TO_GOAL)),
            new DrivetrainMovementCommand(drivetrain, Constants.ANGLE_FROM_START_TO_TRENCH),
            //new DrivetrainMovementCommand(drivetrain, 0, Constants.DISTANCE_FROM_START_TO_TRENCH),
            new DrivetrainMovementCommand(drivetrain, -Constants.ANGLE_FROM_START_TO_TRENCH),
            // new IntakeAcquireCommand(intake),
            new DrivetrainMovementCommand(drivetrain, 0, Constants.DISTANCE_FROM_BALL_TO_BALL),

            new DrivetrainMovementCommand(drivetrain, Constants.ANGLE_FROM_TRENCH_TO_RDVS),
            // new IntakeAcquireCommand(intake),
            new DrivetrainMovementCommand(drivetrain, 0, Constants.DISTANCE_FROM_TRENCH_TO_RDVS),
            new DrivetrainMovementCommand(drivetrain, Constants.ANGLE_FROM_RDVS_TO_TWO_BALL),
            new DrivetrainMovementCommand(drivetrain, 0, Constants.DISTANCE_BETWEEN_TWO_BALL),
            new DrivetrainMovementCommand(drivetrain, 90),
            new DrivetrainMovementCommand(drivetrain, 0, Constants.DISTANCE_FROM_RDVS_TO_INTERSECTION_BEWTWEEN_TWO_BALL_AND_GOAL),
            new DrivetrainMovementCommand(drivetrain, 80), //estimate
            new DrivetrainAlignmentCommand(drivetrain, new DrivetrainGoalAligner(20)),
            //shoot
            new DrivetrainStopCommand(drivetrain)
        );
    }
}