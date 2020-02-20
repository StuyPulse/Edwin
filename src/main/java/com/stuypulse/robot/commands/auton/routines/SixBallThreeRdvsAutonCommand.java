package com.stuypulse.robot.commands.auton.routines;

import com.stuypulse.robot.Constants;
import com.stuypulse.robot.commands.DrivetrainAlignmentCommand;
import com.stuypulse.robot.commands.DrivetrainGoalAligner;
import com.stuypulse.robot.commands.DrivetrainInnerGoalAligner;
import com.stuypulse.robot.commands.DrivetrainMovementCommand;
import com.stuypulse.robot.commands.DrivetrainStopCommand;
import com.stuypulse.robot.commands.IntakeAcquireCommand;
import com.stuypulse.robot.commands.ShooterControlCommand;
import com.stuypulse.robot.subsystems.Chimney;
import com.stuypulse.robot.subsystems.Drivetrain;
import com.stuypulse.robot.subsystems.Funnel;
import com.stuypulse.robot.subsystems.Intake;
import com.stuypulse.robot.subsystems.Shooter;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;


public class SixBallThreeRdvsAutonCommand extends SequentialCommandGroup {
    public SixBallThreeRdvsAutonCommand(Drivetrain drivetrain, Intake intake, Funnel funnel, Chimney chimney, Shooter shooter) {
        addCommands(
            new ShooterControlCommand(shooter, Constants.Shooting.INITATION_LINE_RPM),
            new DrivetrainAlignmentCommand(drivetrain, new DrivetrainGoalAligner(Constants.SHOOT_FROM_START_TO_GOAL)),
            
            //Shoot 3
            //new FeedAndShootBallsAtTargetVelocityCommand(3, funnel, chimney, shooter),
            new DrivetrainMovementCommand(drivetrain, 0, 12),

            //Move forward
            new DrivetrainMovementCommand(drivetrain, 0, Constants.DISTANCE_FROM_START_TO_RDVS),
            new DrivetrainMovementCommand(drivetrain, Constants.ANGLE_FROM_START_POINT_TO_THREE_BALL),
            new IntakeAcquireCommand(intake),
            new DrivetrainMovementCommand(drivetrain, 0, Constants.DISTANCE_FOR_THREE_BALLS_IN_RDVS),
            new DrivetrainMovementCommand(drivetrain, -Constants.ANGLE_FROM_START_POINT_TO_THREE_BALL),
            new DrivetrainAlignmentCommand(drivetrain, new DrivetrainGoalAligner(Constants.DISTANCE_FROM_TRENCH_TO_GOAL)),
            new DrivetrainAlignmentCommand(drivetrain, new DrivetrainInnerGoalAligner()),
            new DrivetrainStopCommand(drivetrain)
            
            //Shoot 3
            //new FeedAndShootBallsAtTargetVelocityCommand(3, funnel, chimney, shooter)
            );
    }
}