package com.stuypulse.robot.commands.auton.routines;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import com.stuypulse.robot.commands.DrivetrainGoalAligner;
import com.stuypulse.robot.commands.DrivetrainMovementCommand;
import com.stuypulse.robot.commands.DrivetrainAlignmentCommand;
import com.stuypulse.robot.commands.DrivetrainStopCommand;
import com.stuypulse.robot.commands.IntakeAcquireCommand;
import com.stuypulse.robot.subsystems.Drivetrain;
import com.stuypulse.robot.subsystems.Intake;
import com.stuypulse.robot.Constants;


public class SixBallThreeRdvsAutonCommand extends SequentialCommandGroup {
    public SixBallThreeRdvsAutonCommand(Drivetrain drivetrain, Intake intake) {
        addCommands(
            new DrivetrainAlignmentCommand(drivetrain, new DrivetrainGoalAligner(Constants.SHOOT_FROM_START_TO_GOAL)),
            
            // TODO: Add shoot 3
            new DrivetrainMovementCommand(drivetrain, 0, 12),

            //Move forward
            new DrivetrainMovementCommand(drivetrain, 0, Constants.DISTANCE_FROM_START_TO_RDVS),
            new DrivetrainMovementCommand(drivetrain, Constants.ANGLE_FROM_START_POINT_TO_THREE_BALL),
            new IntakeAcquireCommand(intake),
            new DrivetrainMovementCommand(drivetrain, 0, Constants.DISTANCE_FOR_THREE_BALLS_IN_RDVS),
            new DrivetrainMovementCommand(drivetrain, -Constants.ANGLE_FROM_START_POINT_TO_THREE_BALL),
            new DrivetrainAlignmentCommand(drivetrain, new DrivetrainGoalAligner(Constants.DISTANCE_FROM_START_TO_RDVS)),
            new DrivetrainStopCommand(drivetrain)
            //Shoot 3  
            );
    }
}