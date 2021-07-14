package com.stuypulse.robot.commands.auton.routines;

import com.stuypulse.robot.RobotContainer;
import com.stuypulse.robot.Constants.Alignment;
import com.stuypulse.robot.Constants.AutoSettings;

import com.stuypulse.robot.commands.*;
import com.stuypulse.robot.subsystems.LEDController.LEDColor;
import com.stuypulse.robot.subsystems.Shooter.ShooterMode;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

/**
 * A revised version of the old six ball trench auton that used PID loops
 * instead of motion profiling.
 * 
 * @author Myles Pasetsky (@selym3)
 * @author Kevin Li (@KevinLi0711)
 */
public class OldSixBallTrenchAutonClean extends SequentialCommandGroup {
    
    public OldSixBallTrenchAutonClean(RobotContainer robot) {

        /** 
         * Extend the intake, setup the shooter, and start running the intake.
         *
         * Uses wait commands to prevent browning out (alternatively, current 
         * limiting can be used).
         */
        addCommands(
            new LEDSetCommand(robot.getLEDController(), LEDColor.WHITE_SOLID),
            
            new IntakeExtendCommand(robot.getIntake()),
            new ShooterControlCommand(robot.getShooter(), ShooterMode.TRENCH_SHOT),
            new WaitCommand(0.1),
            new IntakeAcquireForeverCommand(robot.getIntake()),
            new WaitCommand(1.0)
        );
        
        
        /**
         * Move the drivetrain back to the trench's two balls 
         * within 3 seconds.
         */
        addCommands(
            new LEDSetCommand(robot.getLEDController(), LEDColor.YELLOW_SOLID),
            
            new DrivetrainMovementCommand(
                robot.getDrivetrain(), 
                0, 
                AutoSettings.DISTANCE_FROM_START_TO_TRENCH_IN_FEET + AutoSettings.DISTANCE_TO_ACQUIRE_TWO_BALLS_IN_FEET
            ).setMaxSpeed(AutoSettings.DRIVETRAIN_SPEED_LIMIT).withTimeout(3)
            // new DrivetrainMovementCommand(drivetrain, 0, -DISTANCE_TO_ACQUIRE_TWO_BALLS_IN_FEET).setTimeout(1.5),
        );
        
        /**
         * Then, it aligns the drivetrain with the target from the 
         * trench distance. It also starts to bring up the balls in 
         * parallel.
         */
        addCommands(
            new LEDSetCommand(robot.getLEDController(), LEDColor.ORANGE_SOLID),

            // Have the balls be fed up to the top of the chute while the drivetrain
            // is aligning. Ends when the alignment ends or after 3.5 seconds.
            new DrivetrainAlignAndFeedCommand(
                robot, 
                Alignment.TRENCH_DISTANCE
            ).withTimeout(3.5)
        ); 

        /**
         * This actually shoots the balls once aligned with the target
         * from the target distance.
         */
        addCommands(
            new LEDSetCommand(robot.getLEDController(), LEDColor.RED_SOLID),

            // FeedBallsCommand will run forever, but we purposely stop it after 1 second
            new FeedBallsCommand(
                robot.getFunnel(), 
                robot.getChimney()
            ).withTimeout(1.0)
        );
        

        /**
         * Move back to get to the third ball in the trench.
         */
        addCommands(
            new LEDSetCommand(robot.getLEDController(), LEDColor.GREEN_SOLID),
            
            // new DrivetrainMovementCommand(drivetrain, ANGLE_TO_ACQUIRE_FROM_TRENCH_IN_DEGREES, 0).setTimeout(1.0),
            // new DrivetrainMovementCommand(drivetrain, 0, DISTANCE_TO_ACQUIRE_THIRD_BALL_IN_FEET).setSpeed(0.5).setTimeout(1.0),
            new DrivetrainMovementCommand(
                robot.getDrivetrain(), 
                0, 
                AutoSettings.DISTANCE_TO_ACQUIRE_THIRD_BALL_IN_FEET
            ).setMaxSpeed(AutoSettings.DRIVETRAIN_SPEED_LIMIT).withTimeout(2.0)
        );

        /**
         * Align to the limelight from the trench within 4 seconds.
         */
        addCommands(  
            new LEDSetCommand(robot.getLEDController(), LEDColor.BLUE_SOLID),

            // Feed the balls as far up as possible (without shooting) while aligning
            new DrivetrainAlignAndFeedCommand(
                robot, 
                Alignment.TRENCH_DISTANCE
            ).withTimeout(4)
        );

        /**
         * This actually shoots the balls once we are at the trench again.
         */
        addCommands(
            new LEDSetCommand(robot.getLEDController(), LEDColor.PURPLE_SOLID),
            
            // FeedBallsCommand will run forever here, but that's okay because end of the auton will
            // just cancel it
            new FeedBallsCommand(
                robot.getFunnel(), 
                robot.getChimney()
            )
        );
    }
}