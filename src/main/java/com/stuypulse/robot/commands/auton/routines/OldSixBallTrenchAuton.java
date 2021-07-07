package com.stuypulse.robot.commands.auton.routines;

import com.stuypulse.robot.RobotContainer;
import com.stuypulse.robot.Constants.Alignment;
import com.stuypulse.robot.Constants.AutoSettings;

import com.stuypulse.robot.commands.*;
import com.stuypulse.robot.subsystems.Chimney;
import com.stuypulse.robot.subsystems.Drivetrain;
import com.stuypulse.robot.subsystems.Funnel;
import com.stuypulse.robot.subsystems.Intake;
import com.stuypulse.robot.subsystems.Shooter;
import com.stuypulse.robot.subsystems.LEDController.LEDColor;
import com.stuypulse.robot.subsystems.Shooter.ShooterMode;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class OldSixBallTrenchAuton extends SequentialCommandGroup {
    public OldSixBallTrenchAuton(RobotContainer robot) {

        final double DISTANCE_TO_ACQUIRE_TWO_BALLS_IN_FEET = 5 * 1.208;
        final double DISTANCE_TO_ACQUIRE_THIRD_BALL_IN_FEET = 5 * 1.208 + 0.5;
        final double ANGLE_TO_ACQUIRE_FROM_TRENCH_IN_DEGREES = 15;
        final double DRIVETRAIN_SPEED_LIMIT = 0.55;

        addCommands(
            new LEDSetCommand(robot.getLEDController(), LEDColor.WHITE_SOLID),
            
            new IntakeExtendCommand(robot.getIntake()),
            new ShooterControlCommand(robot.getShooter(), ShooterMode.TRENCH_SHOT),
            new WaitCommand(0.1),
                new IntakeAcquireForeverCommand(robot.getIntake()),
            new WaitCommand(1.0),

            new LEDSetCommand(robot.getLEDController(), LEDColor.YELLOW_SOLID),
            new DrivetrainMovementCommand(robot.getDrivetrain(), 0, AutoSettings.DISTANCE_FROM_START_TO_TRENCH_IN_FEET + DISTANCE_TO_ACQUIRE_TWO_BALLS_IN_FEET).setMaxSpeed(DRIVETRAIN_SPEED_LIMIT).withTimeout(3),
            // new DrivetrainMovementCommand(drivetrain, 0, -DISTANCE_TO_ACQUIRE_TWO_BALLS_IN_FEET).setTimeout(1.5),
           
            new LEDSetCommand(robot.getLEDController(), LEDColor.ORANGE_SOLID),
            new DrivetrainGoalCommand(robot.getDrivetrain(), Alignment.TRENCH_DISTANCE).withTimeout(3.5),

            
            new LEDSetCommand(robot.getLEDController(), LEDColor.RED_SOLID),
            new TimeoutCommand(new FeedBallsCommand(robot.getFunnel(), robot.getChimney()), 1.0),
            
            new LEDSetCommand(robot.getLEDController(), LEDColor.GREEN_SOLID),
            // new DrivetrainMovementCommand(drivetrain, ANGLE_TO_ACQUIRE_FROM_TRENCH_IN_DEGREES, 0).setTimeout(1.0),
            // new DrivetrainMovementCommand(drivetrain, 0, DISTANCE_TO_ACQUIRE_THIRD_BALL_IN_FEET).setSpeed(0.5).setTimeout(1.0),
            new DrivetrainMovementCommand(robot.getDrivetrain(), 0, DISTANCE_TO_ACQUIRE_THIRD_BALL_IN_FEET).setMaxSpeed(DRIVETRAIN_SPEED_LIMIT).withTimeout(2.0),
            
            new LEDSetCommand(robot.getLEDController(), LEDColor.BLUE_SOLID),
            new DrivetrainGoalCommand(robot.getDrivetrain(), Alignment.TRENCH_DISTANCE).withTimeout(4),
          
            new LEDSetCommand(robot.getLEDController(), LEDColor.BLUE_SOLID),
            new ParallelCommandGroup(
                new FeedBallsCommand(robot.getFunnel(), robot.getChimney()),
                new IntakeAcquireCommand(robot.getIntake())
            )
        );
    }
}