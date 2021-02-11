package com.stuypulse.robot.commands.auton.routines;

import com.stuypulse.robot.Constants;
import com.stuypulse.robot.Constants.Alignment;
import com.stuypulse.robot.Constants.ShooterSettings;
import com.stuypulse.robot.commands.*;
import com.stuypulse.robot.subsystems.Chimney;
import com.stuypulse.robot.subsystems.Drivetrain;
import com.stuypulse.robot.subsystems.Funnel;
import com.stuypulse.robot.subsystems.Intake;
import com.stuypulse.robot.subsystems.Shooter;
import com.stuypulse.robot.subsystems.Shooter.ShooterMode;
import com.stuypulse.robot.util.LEDController;
import com.stuypulse.robot.util.LEDController.Color;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class EightBallTwoRdvsThreeTrenchAutonCommand extends SequentialCommandGroup {

    public EightBallTwoRdvsThreeTrenchAutonCommand(Intake intake, Shooter shooter, Funnel funnel, Chimney chimney,
            Drivetrain drivetrain, LEDController controller) {
        final double DISTANCE_TO_RDVS_IN_FEET = 7.75;
        final double ANGLE_TO_RDVS = 85.0;
        final double DISTANCE_TO_ACQUIRE_RDVS_BALLS_IN_FEET = 2.0;
        final double DISTANCE_TO_BACKUP_AFTER_RDVS = -6.5;
        final double ANGLE_TO_TRENCH = -55.0;
        final double DISTANCE_TO_ACQUIRE_TRENCH_BALLS = 9.25;
        final double ANGLE_TO_WIGGLE = 10.0;

        addCommands(
                new LEDSetCommand(Color.WHITE_SOLID, controller), new IntakeExtendCommand(intake),
                new ShooterControlCommand(shooter, ShooterSettings.TRENCH_RPM, ShooterMode.SHOOT_FROM_TRENCH),

                new WaitCommand(1.0), new IntakeAcquireForeverCommand(intake),

                new LEDSetCommand(Color.RED_SOLID, controller),
                new ParallelCommandGroup(new TimeoutCommand(new FeedBallsCommand(funnel, chimney), 2.0),
                        new SequentialCommandGroup(new WaitCommand(0.5),
                                new LEDSetCommand(Color.ORANGE_SOLID, controller),
                                new DrivetrainMovementCommand(drivetrain, 0, DISTANCE_TO_RDVS_IN_FEET)
                                        .withTimeout(3.0))),

                // new LEDSetCommand(Color.ORANGE_SOLID, controller),
                // new DrivetrainMovementCommand(drivetrain, 0,
                // DISTANCE_TO_RDVS_IN_FEET).withTimeout(15.0),

                new LEDSetCommand(Color.YELLOW_SOLID, controller),
                new DrivetrainMovementCommand(drivetrain, ANGLE_TO_RDVS).withTimeout(1.0),

                // new LEDSetCommand(Color.GREEN_SOLID, controller),
                // new ShooterControlCommand(shooter, Constants.Shooting.TRENCH_RPM,
                // ShooterMode.SHOOT_FROM_TRENCH),

                new LEDSetCommand(Color.GREEN_SOLID, controller),
                new DrivetrainMovementCommand(drivetrain, 0, DISTANCE_TO_ACQUIRE_RDVS_BALLS_IN_FEET).withTimeout(1.0),

                new LEDSetCommand(Color.BLUE_SOLID, controller),
                new DrivetrainMovementCommand(drivetrain, ANGLE_TO_WIGGLE).withTimeout(0.15),

                new LEDSetCommand(Color.PURPLE_SOLID, controller),
                new DrivetrainMovementCommand(drivetrain, -ANGLE_TO_WIGGLE).withTimeout(0.15),

                new LEDSetCommand(Color.RED_SOLID, controller),
                new DrivetrainMovementCommand(drivetrain, -30).withTimeout(0.75),

                new LEDSetCommand(Color.ORANGE_SOLID, controller),
                new DrivetrainMovementCommand(drivetrain, 0, DISTANCE_TO_BACKUP_AFTER_RDVS).withTimeout(3.5),

                new LEDSetCommand(Color.YELLOW_SOLID, controller),
                new DrivetrainMovementCommand(drivetrain, ANGLE_TO_TRENCH).withTimeout(1.0),

                new LEDSetCommand(Color.GREEN_SOLID, controller),
                new DrivetrainMovementCommand(drivetrain, 0, DISTANCE_TO_ACQUIRE_TRENCH_BALLS).withTimeout(3.0),

                // new DrivetrainMovementCommand(drivetrain, 0, -5.0),

                new LEDSetCommand(Color.BLUE_SOLID, controller),
                new ParallelDeadlineGroup(
                        new DrivetrainGoalCommand(drivetrain, Constants.Alignment.TRENCH_DISTANCE).withTimeout(15.0),
                        new FeedBallsInAutoCommand(funnel, chimney)),

                new LEDSetCommand(Color.PURPLE_SOLID, controller),
                new ParallelCommandGroup(new FeedBallsCommand(funnel, chimney), new IntakeAcquireCommand(intake)));

    }

}