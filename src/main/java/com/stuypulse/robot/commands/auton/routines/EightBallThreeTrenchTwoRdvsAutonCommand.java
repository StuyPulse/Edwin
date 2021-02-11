package com.stuypulse.robot.commands.auton.routines;

import com.stuypulse.robot.Constants.AutoSettings;
import com.stuypulse.robot.commands.*;
import com.stuypulse.robot.subsystems.Drivetrain;
import com.stuypulse.robot.subsystems.Intake;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class EightBallThreeTrenchTwoRdvsAutonCommand extends SequentialCommandGroup {
    public EightBallThreeTrenchTwoRdvsAutonCommand(Drivetrain drivetrain) {
        addCommands(
                new DrivetrainGoalCommand(drivetrain, AutoSettings.SHOOT_FROM_START_TO_GOAL),
                new DrivetrainMovementCommand(drivetrain, AutoSettings.ANGLE_FROM_START_TO_TRENCH),
                // new DrivetrainMovementCommand(drivetrain, 0,
                // AutoSettings.DISTANCE_FROM_START_TO_TRENCH),
                new DrivetrainMovementCommand(drivetrain, -AutoSettings.ANGLE_FROM_START_TO_TRENCH),
                // new IntakeAcquireCommand(intake),
                new DrivetrainMovementCommand(drivetrain, 0, AutoSettings.DISTANCE_FROM_BALL_TO_BALL),

                new DrivetrainMovementCommand(drivetrain, AutoSettings.ANGLE_FROM_TRENCH_TO_RDVS),
                // new IntakeAcquireCommand(intake),
                new DrivetrainMovementCommand(drivetrain, 0, AutoSettings.DISTANCE_FROM_TRENCH_TO_RDVS),
                new DrivetrainMovementCommand(drivetrain, AutoSettings.ANGLE_FROM_RDVS_TO_TWO_BALL),
                new DrivetrainMovementCommand(drivetrain, 0, AutoSettings.DISTANCE_BETWEEN_TWO_BALL),
                new DrivetrainMovementCommand(drivetrain, 90),
                new DrivetrainMovementCommand(drivetrain, 0,
                        AutoSettings.DISTANCE_FROM_RDVS_TO_INTERSECTION_BEWTWEEN_TWO_BALL_AND_GOAL),
                new DrivetrainMovementCommand(drivetrain, 80), // estimate
                new DrivetrainGoalCommand(drivetrain, 20),
                // shoot
                new DrivetrainStopCommand(drivetrain));
    }
}