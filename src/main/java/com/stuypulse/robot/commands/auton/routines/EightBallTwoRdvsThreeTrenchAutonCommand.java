package com.stuypulse.robot.commands.auton.routines;

import com.stuypulse.robot.Constants;
import com.stuypulse.robot.commands.DrivetrainAlignmentCommand;
import com.stuypulse.robot.commands.DrivetrainGoalAligner;
import com.stuypulse.robot.commands.DrivetrainMovementCommand;
import com.stuypulse.robot.commands.FeedBallsCommand;
import com.stuypulse.robot.commands.IntakeAcquireCommand;
import com.stuypulse.robot.commands.IntakeAcquireForeverCommand;
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

public class EightBallTwoRdvsThreeTrenchAutonCommand extends SequentialCommandGroup {

    public EightBallTwoRdvsThreeTrenchAutonCommand(Intake intake, Shooter shooter, Funnel funnel, Chimney chimney, Drivetrain drivetrain) {

        final double DISTANCE_TO_RDVS_IN_FEET = 7.0;
        final double ANGLE_TO_RDVS = 90.0;
        final double DISTANCE_TO_ACQUIRE_RDVS_BALLS_IN_FEET = 2.0;
        final double DISTANCE_TO_BACKUP_AFTER_RDVS = -5;
        final double ANGLE_TO_TRENCH = -75.0;
        final double DISTANCE_TO_ACQUIRE_TRENCH_BALLS = 6.0;

        addCommands(
            new IntakeExtendCommand(intake),
            new ShooterControlCommand(shooter, Constants.Shooting.INITATION_LINE_RPM, ShooterMode.SHOOT_FROM_INITIATION_LINE),
            new WaitCommand(0.1),
            new IntakeAcquireForeverCommand(intake),
            new TimeoutCommand(new FeedBallsCommand(shooter, funnel, chimney), 2.0),
            new ShooterControlCommand(shooter, Constants.Shooting.TRENCH_RPM, ShooterMode.SHOOT_FROM_TRENCH),
            new DrivetrainMovementCommand(drivetrain, 0, DISTANCE_TO_RDVS_IN_FEET).setTimeout(15),
            new DrivetrainMovementCommand(drivetrain, ANGLE_TO_RDVS).setTimeout(15),
            new DrivetrainMovementCommand(drivetrain, 0, DISTANCE_TO_ACQUIRE_RDVS_BALLS_IN_FEET).setTimeout(15),
            new DrivetrainMovementCommand(drivetrain, 0, DISTANCE_TO_BACKUP_AFTER_RDVS).setTimeout(15),
            new DrivetrainMovementCommand(drivetrain, ANGLE_TO_TRENCH).setTimeout(15),
            new DrivetrainMovementCommand(drivetrain, 0, DISTANCE_TO_ACQUIRE_TRENCH_BALLS).setTimeout(15),
            new DrivetrainAlignmentCommand(drivetrain, new DrivetrainGoalAligner(Constants.Alignment.TRENCH_DISTANCE)).setTimeout(2.0),
            new ParallelCommandGroup(
                new FeedBallsCommand(shooter, funnel, chimney),
                new IntakeAcquireCommand(intake)
            )
        );

    }

}