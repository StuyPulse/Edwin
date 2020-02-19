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
import com.stuypulse.robot.commands.ShooterControlCommand;
import com.stuypulse.robot.subsystems.Chimney;
import com.stuypulse.robot.subsystems.Drivetrain;
import com.stuypulse.robot.subsystems.Funnel;
import com.stuypulse.robot.subsystems.Intake;
import com.stuypulse.robot.subsystems.Shooter;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class SixBallThreeTrenchAutonCommand extends SequentialCommandGroup {
    public SixBallThreeTrenchAutonCommand(Drivetrain drivetrain, Shooter shooter, Funnel funnel, Chimney chimney) {
        addCommands(
            new ShooterControlCommand(shooter, Constants.Shooting.INITATION_LINE_RPM),
            new DrivetrainAlignmentCommand(drivetrain, new DrivetrainGoalAligner(Constants.SHOOT_FROM_START_TO_GOAL)),
            //Shoot 3
            new FeedAndShootBallsAtTargetVelocityCommand(3, funnel, chimney, shooter),
            new DrivetrainMovementCommand(drivetrain, Constants.ANGLE_FROM_START_TO_TRENCH),
            new DrivetrainMovementCommand(drivetrain, 0, Constants.toFeet(0, Constants.DISTANCE_FROM_START_TO_TRENCH)),
            new DrivetrainMovementCommand(drivetrain, -Constants.toFeet(0, Constants.ANGLE_FROM_START_TO_TRENCH)),
            new IntakeAcquireCommand(new Intake()),
            new DrivetrainMovementCommand(drivetrain, 0, 2 * Constants.toFeet(0, Constants.DISTANCE_FROM_BALL_TO_BALL)),
            new DrivetrainAlignmentCommand(drivetrain, new DrivetrainGoalAligner(Constants.toFeet(0, Constants.DISTANCE_FROM_TRENCH_TO_GOAL))),
            new DrivetrainAlignmentCommand(drivetrain, new DrivetrainInnerGoalAligner()),
            new DrivetrainStopCommand(drivetrain),
            //Shoot three
            new FeedAndShootBallsAtTargetVelocityCommand(3, funnel, chimney, shooter)
        );
    }
}