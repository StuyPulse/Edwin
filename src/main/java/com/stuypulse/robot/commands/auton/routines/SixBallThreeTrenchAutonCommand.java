package com.stuypulse.robot.commands.auton.routines;

import com.stuypulse.robot.Constants.AutoSettings;
import com.stuypulse.robot.Constants.ShooterSettings;
import com.stuypulse.robot.commands.DrivetrainAlignmentCommand;
import com.stuypulse.robot.commands.DrivetrainGoalAligner;
import com.stuypulse.robot.commands.DrivetrainGoalCommand;
import com.stuypulse.robot.commands.DrivetrainMovementCommand;
import com.stuypulse.robot.commands.DrivetrainStopCommand;
import com.stuypulse.robot.commands.ShooterControlCommand;
import com.stuypulse.robot.subsystems.Chimney;
import com.stuypulse.robot.subsystems.Drivetrain;
import com.stuypulse.robot.subsystems.Funnel;
import com.stuypulse.robot.subsystems.Shooter;
import com.stuypulse.robot.subsystems.Shooter.ShooterMode;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class SixBallThreeTrenchAutonCommand extends SequentialCommandGroup {
    public SixBallThreeTrenchAutonCommand(Drivetrain drivetrain, Shooter shooter, Funnel funnel, Chimney chimney) {
        addCommands(
                new ShooterControlCommand(shooter, ShooterSettings.INITATION_LINE_RPM,
                        ShooterMode.SHOOT_FROM_INITIATION_LINE),
                new DrivetrainGoalCommand(drivetrain, AutoSettings.SHOOT_FROM_START_TO_GOAL),
                // Shoot 3
                // new FeedAndShootBallsAtTargetVelocityCommand(3, funnel, chimney, shooter),
                new DrivetrainMovementCommand(drivetrain, AutoSettings.ANGLE_FROM_START_TO_TRENCH),
                // new DrivetrainMovementCommand(drivetrain, 0, AutoSettings.toFeet(0,
                // AutoSettings.DISTANCE_FROM_START_TO_TRENCH)),
                new DrivetrainMovementCommand(drivetrain, -AutoSettings.ANGLE_FROM_START_TO_TRENCH),
                // new IntakeAcquireCommand(intake),
                new DrivetrainMovementCommand(drivetrain, 0, 2 * AutoSettings.DISTANCE_FROM_BALL_TO_BALL),
                new DrivetrainGoalCommand(drivetrain, AutoSettings.DISTANCE_FROM_TRENCH_TO_GOAL),
                new DrivetrainStopCommand(drivetrain)
        // Shoot three
        // new FeedAndShootBallsAtTargetVelocityCommand(3, funnel, chimney, shooter)
        );
    }
}