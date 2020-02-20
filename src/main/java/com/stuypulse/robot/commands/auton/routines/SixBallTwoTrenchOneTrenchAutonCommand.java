package com.stuypulse.robot.commands.auton.routines;

import com.stuypulse.robot.Constants;
import com.stuypulse.robot.commands.DrivetrainAlignmentCommand;
import com.stuypulse.robot.commands.DrivetrainGoalAligner;
import com.stuypulse.robot.commands.DrivetrainMovementCommand;
import com.stuypulse.robot.commands.FeedBallsCommand;
import com.stuypulse.robot.commands.IntakeAcquireCommand;
import com.stuypulse.robot.commands.IntakeAcquireForeverCommand;
import com.stuypulse.robot.commands.IntakeAcquireSetupCommand;
import com.stuypulse.robot.commands.IntakeExtendCommand;
import com.stuypulse.robot.commands.ShooterControlCommand;
import com.stuypulse.robot.commands.TimeoutCommand;
import com.stuypulse.robot.subsystems.Chimney;
import com.stuypulse.robot.subsystems.Drivetrain;
import com.stuypulse.robot.subsystems.Funnel;
import com.stuypulse.robot.subsystems.Intake;
import com.stuypulse.robot.subsystems.Shooter;
import com.stuypulse.robot.subsystems.Shooter.ShooterMode;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class SixBallTwoTrenchOneTrenchAutonCommand extends SequentialCommandGroup {
    public SixBallTwoTrenchOneTrenchAutonCommand(Drivetrain drivetrain, Shooter shooter, Funnel funnel, Chimney chimney, Intake intake) {

        final double DISTANCE_TO_ACQUIRE_TWO_BALLS_IN_FEET = 6;
        final double DISTANCE_TO_ACQUIRE_THIRD_BALL_IN_FEET = 9;
        final double ANGLE_TO_ACQUIRE_FROM_TRENCH_IN_DEGREES = 15;

        addCommands(
            new IntakeExtendCommand(intake),
            new WaitCommand(0.1),
            new ParallelCommandGroup(
                new IntakeAcquireForeverCommand(intake),
                new ShooterControlCommand(shooter, Constants.Shooting.TRENCH_RPM, ShooterMode.SHOOT_FROM_TRENCH)
            ),

            new WaitCommand(2.0),
            new DrivetrainMovementCommand(drivetrain, 0, Constants.toFeet(Constants.DISTANCE_FROM_START_TO_TRENCH) + DISTANCE_TO_ACQUIRE_TWO_BALLS_IN_FEET).setSpeed(0.5).setTimeout(3.0),
            // new DrivetrainMovementCommand(drivetrain, 0, -DISTANCE_TO_ACQUIRE_TWO_BALLS_IN_FEET).setTimeout(1.5),
           
            new DrivetrainAlignmentCommand(drivetrain, new DrivetrainGoalAligner(Constants.Alignment.TRENCH_DISTANCE)).setTimeout(1.0),
            new TimeoutCommand(new FeedBallsCommand(shooter, funnel, chimney), 4.0),
            
            // new DrivetrainMovementCommand(drivetrain, ANGLE_TO_ACQUIRE_FROM_TRENCH_IN_DEGREES, 0).setTimeout(1.0),
            // new DrivetrainMovementCommand(drivetrain, 0, DISTANCE_TO_ACQUIRE_THIRD_BALL_IN_FEET).setSpeed(0.5).setTimeout(1.0),
            new DrivetrainMovementCommand(drivetrain, 0, -DISTANCE_TO_ACQUIRE_THIRD_BALL_IN_FEET).setTimeout(1.0),
            
            new DrivetrainAlignmentCommand(drivetrain, new DrivetrainGoalAligner(Constants.toFeet(Constants.Alignment.TRENCH_DISTANCE))),
            new ParallelCommandGroup(
                new FeedBallsCommand(shooter, funnel, chimney),
                new IntakeAcquireCommand(intake)
            )
        );
    }
}