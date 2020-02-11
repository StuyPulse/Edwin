package com.stuypulse.robot.commands.auton.routines;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import com.stuypulse.robot.commands.DrivetrainGoalAligner;
import com.stuypulse.robot.commands.DrivetrainMovementCommand;
import com.stuypulse.robot.commands.DrivetrainPIDAlignmentCommand;
import com.stuypulse.robot.commands.DrivetrainStopCommand;
import com.stuypulse.robot.commands.IntakeAcquireCommand;
import com.stuypulse.robot.subsystems.Drivetrain;
import com.stuypulse.robot.subsystems.Intake;
import com.stuypulse.robot.Constants;


public class SixBallFiveRdvsAutonCommand extends SequentialCommandGroup {
    public SixBallFiveRdvsAutonCommand(Drivetrain drivertrain, Intake intaker) {
        //Do 3
        addCommands(new DrivetrainPIDAlignmentCommand(drivertrain, new DrivetrainGoalAligner(Constants.DISTANCE_FROM_START_TO_GOAL)));
        // TODO: Add shoot 3
        addCommands(new DrivetrainMovementCommand(drivertrain, 0, 12));
        addCommands(new DrivetrainStopCommand(drivertrain));

        //Move forward
        addCommands(new DrivetrainMovementCommand(drivertrain, 0, Constants.DISTANCE_FROM_START_TO_RDVS));
        addCommands(new DrivetrainMovementCommand(drivertrain, Constants.ANGLE_FROM_RDVS_TO_BALL));
        addCommands(new IntakeAcquireCommand(intaker));
        addCommands(new DrivetrainMovementCommand(drivertrain, 0, Constants.DISTANCE_FOR_THREE_BALLS_IN_RDVS));
        //Get 2 more balls
        addCommands(new DrivetrainMovementCommand(drivertrain, Constants.ANGLE_FROM_THREE_BALL_TO_TWO_BALL_IN_RDVS));
        addCommands(new DrivetrainMovementCommand(drivertrain, 0, Constants.DISTANCE_FROM_THREE_BALL_TO_TWO_BALL_IN_RDVS));
        addCommands(new DrivetrainMovementCommand(drivertrain, -Constants.ANGLE_FROM_THREE_BALL_TO_TWO_BALL_IN_RDVS));
        addCommands();
        
        
        
        addCommands(new DrivetrainPIDAlignmentCommand(drivertrain, new DrivetrainGoalAligner(Constants.DISTANCE_FROM_START_TO_RDVS)));
        //Shoot 3  
    }
}