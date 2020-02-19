package com.stuypulse.robot.commands.auton.routines;

import com.stuypulse.robot.Constants;
import com.stuypulse.robot.commands.DrivetrainAlignmentCommand;
import com.stuypulse.robot.commands.DrivetrainGoalAligner;
import com.stuypulse.robot.commands.DrivetrainInnerGoalAligner;
import com.stuypulse.robot.commands.DrivetrainMovementCommand;
import com.stuypulse.robot.commands.DrivetrainStopCommand;
import com.stuypulse.robot.commands.FeedAndShootBallsAtTargetVelocityCommand;
import com.stuypulse.robot.commands.ShooterControlCommand;
import com.stuypulse.robot.subsystems.Chimney;
import com.stuypulse.robot.subsystems.Drivetrain;
import com.stuypulse.robot.subsystems.Funnel;
import com.stuypulse.robot.subsystems.Shooter;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class ShootThreeWithLimelightAutonCommand extends SequentialCommandGroup {


    public ShootThreeWithLimelightAutonCommand(Drivetrain drivetrain, Shooter shooter, Funnel funnel, Chimney chimney, boolean towardIntake) {
        addCommands(
            new ShooterControlCommand(shooter, Constants.Shooting.INITATION_LINE_RPM),
            new DrivetrainAlignmentCommand(drivetrain, new DrivetrainGoalAligner(Constants.SHOOT_FROM_START_TO_GOAL)),
            new DrivetrainAlignmentCommand(drivetrain, new DrivetrainInnerGoalAligner()),
            new FeedAndShootBallsAtTargetVelocityCommand(3, funnel, chimney, shooter),
            new DrivetrainMovementCommand(drivetrain, 0, towardIntake? Constants.DISTANCE_TO_MOVE_AT_START : -Constants.DISTANCE_TO_MOVE_AT_START),
            new DrivetrainStopCommand(drivetrain)
        );
    }
    
}