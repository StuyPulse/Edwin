package com.stuypulse.robot.commands.auton.routines;

import com.stuypulse.robot.Constants;
import com.stuypulse.robot.Constants.Shooting;
import com.stuypulse.robot.commands.DrivetrainAlignmentCommand;
import com.stuypulse.robot.commands.DrivetrainGoalAligner;
import com.stuypulse.robot.commands.DrivetrainInnerGoalAligner;
import com.stuypulse.robot.commands.DrivetrainMovementCommand;
import com.stuypulse.robot.commands.DrivetrainStopCommand;
import com.stuypulse.robot.commands.FeedAndShootBallsAtTargetVelocityCommand;
import com.stuypulse.robot.commands.IntakeAcquireCommand;
import com.stuypulse.robot.commands.IntakeAcquireForeverCommand;
import com.stuypulse.robot.commands.ShooterControlCommand;
import com.stuypulse.robot.subsystems.Chimney;
import com.stuypulse.robot.subsystems.Drivetrain;
import com.stuypulse.robot.subsystems.Funnel;
import com.stuypulse.robot.subsystems.Intake;
import com.stuypulse.robot.subsystems.Shooter;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class SixBallTwoTrenchOneTrenchAutonCommand extends SequentialCommandGroup {
    public SixBallTwoTrenchOneTrenchAutonCommand(Drivetrain drivetrain, Shooter shooter, Funnel funnel, Chimney chimney, Intake intake) {

        final double DISTANCE_TO_ACQUIRE_TWO_BALLS = 6;
        final double DISTANCE_TO_ACQUIRE_THIRD_BALL = 9;
        final double ANGLE_TO_ACQUIRE_FROM_TRENCH = 15;

        addCommands(
            new ParallelCommandGroup(
                new IntakeAcquireForeverCommand(intake),
                new ShooterControlCommand(shooter, Constants.Shooting.TRENCH_RPM)
            ),
            new DrivetrainMovementCommand(drivetrain, 0, Constants.toFeet(Constants.DISTANCE_FROM_START_TO_TRENCH) + DISTANCE_TO_ACQUIRE_TWO_BALLS),
            new DrivetrainMovementCommand(drivetrain, 0, -DISTANCE_TO_ACQUIRE_TWO_BALLS),
            new DrivetrainAlignmentCommand(drivetrain, new DrivetrainGoalAligner(Constants.toFeet(Constants.DISTANCE_FROM_TRENCH_TO_GOAL))),
            new FeedAndShootBallsAtTargetVelocityCommand(5, funnel, chimney, shooter),
            new DrivetrainMovementCommand(drivetrain, ANGLE_TO_ACQUIRE_FROM_TRENCH, 0),
            new DrivetrainMovementCommand(drivetrain, 0, DISTANCE_TO_ACQUIRE_THIRD_BALL),
            new DrivetrainMovementCommand(drivetrain, 0, -DISTANCE_TO_ACQUIRE_THIRD_BALL),
            new DrivetrainAlignmentCommand(drivetrain, new DrivetrainGoalAligner(Constants.toFeet(Constants.DISTANCE_FROM_TRENCH_TO_GOAL))),
            new FeedAndShootBallsAtTargetVelocityCommand(3, funnel, chimney, shooter)
        );
    }
}