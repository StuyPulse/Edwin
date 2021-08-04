package com.stuypulse.robot.commands.auton.routines;

import com.stuypulse.robot.RobotContainer;
import com.stuypulse.robot.Constants.Alignment;
import com.stuypulse.robot.Constants.AutoSettings;

import com.stuypulse.robot.commands.*;
import com.stuypulse.robot.subsystems.LEDController.LEDColor;
import com.stuypulse.robot.subsystems.Shooter.ShooterMode;

import edu.wpi.first.wpilibj2.command.*;

/**
 * 3 Ball
 */
public class ThreeBallAuton extends SequentialCommandGroup {
    
    public ThreeBallAuton(RobotContainer robot) {

        // Extend the intake, setup the shooter, and start running the intake.
        addCommands(
            new LEDSetCommand(robot.getLEDController(), LEDColor.WHITE_SOLID),
            
            new IntakeExtendCommand(robot.getIntake()),
            new ShooterControlCommand(robot.getShooter(), ShooterMode.INITIATION_LINE),

            new WaitCommand(0.1),
            new IntakeAcquireForeverCommand(robot.getIntake()),
            new WaitCommand(1.0)
        );
        
        // Align the robot to the target using the limelight
        addCommands(
            new LEDSetCommand(robot.getLEDController(), LEDColor.RED_SOLID),

            // This command currently will cause the drivetrain encoders to reset
            // make sure to keep this in mind when adding future motion profiling autons
            new ParallelDeadlineGroup(
                new DrivetrainGoalCommand(robot.getDrivetrain(), -1),
                
                new FeedBallsInAutoCommand(
                    robot.getFunnel(), 
                    robot.getChimney()
                )
            ).withTimeout(3.0)
        );

        // Feed balls until the timeout
        addCommands(
            new LEDSetCommand(robot.getLEDController(), LEDColor.GREEN_PULSE),
            new FeedBallsCommand(robot.getFunnel(), robot.getChimney()).withTimeout(5.0)
        );

        /** 
         * MOVE!
         */
        addCommands(
            new LEDSetCommand(robot.getLEDController(), LEDColor.WHITE_SOLID),
            new DrivetrainMovementCommand(
                robot.getDrivetrain(), 
                0, 
                -2.0
            )
        );
        
    }
}