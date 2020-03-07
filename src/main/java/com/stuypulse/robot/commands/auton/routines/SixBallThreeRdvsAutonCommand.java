package com.stuypulse.robot.commands.auton.routines;

import com.stuypulse.robot.Constants;
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
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;


public class SixBallThreeRdvsAutonCommand extends SequentialCommandGroup {
    public SixBallThreeRdvsAutonCommand(Drivetrain drivetrain, Intake intake, Funnel funnel, Chimney chimney, Shooter shooter, LEDController controller) {
        final double DISTANCE_TO_RDVS_IN_FEET = 7.1;
        final double ANGLE_TO_WIGGLE = -10.0;
        final double ANGLE_TO_SHOOT_TWO_BALLS = 45.0;
        final double DISTANCE_TO_BACKUP_FROM_RDVS_IN_FEET = -1.0;
        final double DISTANCE_TO_BACKUP_AFTER_SHOOTING = -3.0;
        final double DISTANCE_TO_ACQUIRE_LAST_BALL_IN_FEET = 3.0;
        final double ANGLE_TO_LAST_BALL = -30.0;
        final double ANGLE_TO_SHOOT_LAST_BALL = 45.0;
        
        addCommands(
            new LEDSetCommand(Color.WHITE_SOLID, controller),
            new IntakeExtendCommand(intake),

            new WaitCommand(0.5),
            new LEDSetCommand(Color.RED_SOLID, controller),
            new IntakeAcquireForeverCommand(intake),

            new LEDSetCommand(Color.ORANGE_SOLID, controller),
            new ParallelCommandGroup(
                new DrivetrainMovementCommand(drivetrain, 0, DISTANCE_TO_RDVS_IN_FEET).setMaxSpeed(0.6).withTimeout(5.0),
                new SequentialCommandGroup(
                    new WaitCommand(1.5),
                    new ShooterControlCommand(shooter, Constants.Shooting.TRENCH_RPM, ShooterMode.SHOOT_FROM_TRENCH)
                )
            ),

            new LEDSetCommand(Color.YELLOW_SOLID, controller),
            new DrivetrainMovementCommand(drivetrain, ANGLE_TO_WIGGLE).withTimeout(0.3),
           
            new LEDSetCommand(Color.GREEN_SOLID, controller),
            new DrivetrainMovementCommand(drivetrain, -2 * ANGLE_TO_WIGGLE).withTimeout(0.6),
            
            new LEDSetCommand(Color.BLUE_SOLID, controller),
            new DrivetrainMovementCommand(drivetrain, ANGLE_TO_WIGGLE).withTimeout(0.3),

            new LEDSetCommand(Color.PURPLE_SOLID, controller),
            new DrivetrainMovementCommand(drivetrain, 0, DISTANCE_TO_BACKUP_FROM_RDVS_IN_FEET).withTimeout(5.0),

            new LEDSetCommand(Color.RED_SOLID, controller),
            new DrivetrainMovementCommand(drivetrain, ANGLE_TO_SHOOT_TWO_BALLS),

            new LEDSetCommand(Color.ORANGE_SOLID, controller),
            new ParallelDeadlineGroup(
                new DrivetrainGoalCommand(drivetrain, Constants.Alignment.TRENCH_DISTANCE).withTimeout(3.0),
                new FeedBallsInAutoCommand(funnel, chimney)
            ),

            new LEDSetCommand(Color.GREEN_SOLID, controller),
            new FeedBallsCommand(funnel, chimney).withTimeout(2.0),

            new LEDSetCommand(Color.SINELON, controller),
            new DrivetrainMovementCommand(drivetrain, 0, DISTANCE_TO_BACKUP_AFTER_SHOOTING),

            new LEDSetCommand(Color.RAINBOW, controller),
            new DrivetrainMovementCommand(drivetrain, ANGLE_TO_LAST_BALL),

            new LEDSetCommand(Color.BLUE_SOLID, controller),
            new DrivetrainMovementCommand(drivetrain, 0, DISTANCE_TO_ACQUIRE_LAST_BALL_IN_FEET),
            
            new LEDSetCommand(Color.RAINBOW, controller),
            new DrivetrainMovementCommand(drivetrain, 0, -DISTANCE_TO_ACQUIRE_LAST_BALL_IN_FEET),
            
            new LEDSetCommand(Color.BLUE_SOLID, controller),
            new DrivetrainMovementCommand(drivetrain, ANGLE_TO_SHOOT_LAST_BALL),
            
            new LEDSetCommand(Color.TWINKLE, controller),
            new ParallelDeadlineGroup(
                new DrivetrainGoalCommand(drivetrain, Constants.Alignment.TRENCH_DISTANCE).setMaxSpeed(0.0).withTimeout(1.0),
                new FeedBallsInAutoCommand(funnel, chimney)
            ),

            new LEDSetCommand(Color.WAVE, controller),
            new ParallelCommandGroup(
                new FeedBallsCommand(funnel, chimney),
                new IntakeAcquireCommand(intake)
            )
            );
    }
}