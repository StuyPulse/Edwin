package com.stuypulse.robot.commands.auton.routines;

import com.stuypulse.robot.Constants;
import com.stuypulse.robot.Constants.Alignment;

import com.stuypulse.robot.commands.DrivetrainAlignmentCommand;
import com.stuypulse.robot.commands.DrivetrainGoalAligner;
import com.stuypulse.robot.commands.DrivetrainMovementCommand;
import com.stuypulse.robot.commands.FeedBallsCommand;
import com.stuypulse.robot.commands.IntakeAcquireCommand;
import com.stuypulse.robot.commands.IntakeAcquireForeverCommand;
import com.stuypulse.robot.commands.IntakeAcquireSetupCommand;
import com.stuypulse.robot.commands.IntakeExtendCommand;
import com.stuypulse.robot.commands.LEDSetCommand;
import com.stuypulse.robot.commands.ShooterControlCommand;
import com.stuypulse.robot.commands.TimeoutCommand;
import com.stuypulse.robot.subsystems.Chimney;
import com.stuypulse.robot.subsystems.Drivetrain;
import com.stuypulse.robot.subsystems.Funnel;
import com.stuypulse.robot.subsystems.Intake;
import com.stuypulse.robot.subsystems.Shooter;
import com.stuypulse.robot.subsystems.Shooter.ShooterMode;
import com.stuypulse.robot.util.LEDController;
import com.stuypulse.robot.util.LEDController.Color;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class BlueSixBallTwoTrenchOneTrenchAutonCommand extends SequentialCommandGroup {
    public BlueSixBallTwoTrenchOneTrenchAutonCommand(Drivetrain drivetrain, Shooter shooter, Funnel funnel, Chimney chimney, Intake intake, LEDController controller) {

        final double DISTANCE_TO_ACQUIRE_TWO_BALLS_IN_FEET = 5 * 1.208;
        final double DISTANCE_TO_ACQUIRE_THIRD_BALL_IN_FEET = 5 * 1.208;
        final double ANGLE_TO_ACQUIRE_FROM_TRENCH_IN_DEGREES = 15 * 1.208;
        final double DRIVETRAIN_SPEED_LIMIT = 0.55;

        addCommands(
            new LEDSetCommand(Color.WHITE_SOLID, controller),
            new IntakeExtendCommand(intake),
            new ShooterControlCommand(shooter, Constants.Shooting.TRENCH_RPM, ShooterMode.SHOOT_FROM_TRENCH),
            new WaitCommand(0.1),
                new IntakeAcquireForeverCommand(intake),
            new WaitCommand(1.0),

            new LEDSetCommand(Color.YELLOW_SOLID, controller),
            new DrivetrainMovementCommand(drivetrain, 0, Constants.DISTANCE_FROM_START_TO_TRENCH_IN_FEET + DISTANCE_TO_ACQUIRE_TWO_BALLS_IN_FEET).setSpeed(DRIVETRAIN_SPEED_LIMIT).setTimeout(3),
            // new DrivetrainMovementCommand(drivetrain, 0, -DISTANCE_TO_ACQUIRE_TWO_BALLS_IN_FEET).setTimeout(1.5),
           
            new LEDSetCommand(Color.ORANGE_SOLID, controller),
            new DrivetrainAlignmentCommand(drivetrain, new DrivetrainGoalAligner(Constants.Alignment.TRENCH_DISTANCE)).setSpeed(Alignment.Speed.LIMELIGHT_MAX_SPEED).setTimeout(3.5),

            
            new LEDSetCommand(Color.RED_SOLID, controller),
            new TimeoutCommand(new FeedBallsCommand(shooter, funnel, chimney), 1.0),
            
            new LEDSetCommand(Color.GREEN_SOLID, controller),
            // new DrivetrainMovementCommand(drivetrain, ANGLE_TO_ACQUIRE_FROM_TRENCH_IN_DEGREES, 0).setTimeout(1.0),
            // new DrivetrainMovementCommand(drivetrain, 0, DISTANCE_TO_ACQUIRE_THIRD_BALL_IN_FEET).setSpeed(0.5).setTimeout(1.0),
            new DrivetrainMovementCommand(drivetrain, 0, DISTANCE_TO_ACQUIRE_THIRD_BALL_IN_FEET).setSpeed(DRIVETRAIN_SPEED_LIMIT).setTimeout(2.0),
            
            new LEDSetCommand(Color.BLUE_SOLID, controller),
            new DrivetrainAlignmentCommand(drivetrain, new DrivetrainGoalAligner(Constants.Alignment.TRENCH_DISTANCE)).setSpeed(Alignment.Speed.LIMELIGHT_MAX_SPEED).setTimeout(4),
          
            new LEDSetCommand(Color.PURPLE_SOLID, controller),
            new ParallelCommandGroup(
                new FeedBallsCommand(shooter, funnel, chimney),
                new IntakeAcquireCommand(intake)
            )
        );
    }
}