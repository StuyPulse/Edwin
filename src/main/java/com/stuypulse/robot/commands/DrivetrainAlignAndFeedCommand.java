package com.stuypulse.robot.commands;

import com.stuypulse.robot.RobotContainer;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;

/**
 * Utility command to align (with the limelight) while feeding balls as
 * far up as possible without shooting.
 * 
 * Useful for old-style auton commands with PID.
 * 
 * @author Myles Pasetsky (@selym3)
 * @author Kevin Li (@KevinLi0711)
 */
public class DrivetrainAlignAndFeedCommand extends ParallelDeadlineGroup {
    
    public DrivetrainAlignAndFeedCommand(RobotContainer robot, double distance) {
        
        super(
            // The deadline command -- aligning to the target is the focus
            new DrivetrainGoalCommand(robot.getDrivetrain(), distance),

            // All the other stuff we do while aligning (feed balls)... 
            new FeedBallsInAutoCommand(
                robot.getFunnel(), 
                robot.getChimney()
            )
        );
        
    }

}
