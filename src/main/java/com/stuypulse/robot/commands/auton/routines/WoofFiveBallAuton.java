package com.stuypulse.robot.commands.auton.routines;

import com.stuypulse.robot.RobotContainer;

import com.stuypulse.robot.commands.*;
import com.stuypulse.robot.subsystems.LEDController.LEDColor;
import com.stuypulse.robot.subsystems.Shooter.ShooterMode;

import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

/**
 * This auton is made to pick up the two balls from 
 * the other teams trench and then score them.
 * 
 * It is currently untested, but given that it is based on
 * Motion Profiling from Infinite Recharge at Home, it is
 * probably a good auton.
 * 
 * @author Sam Belliveau (sam.belliveau@gmail.com)
 */
public class WoofFiveBallAuton extends SequentialCommandGroup {
    
    private static final String START_PATH = "WoofFiveBall/output/Start.wpilib.json";
    private static final String TO_GOAL_PATH = "WoofFiveBall/output/ToGoal.wpilib.json";

    // These Deadlines are not supposed to be reached, 
    // but act as a guarentee that the auton will complete ontime
    private static final double MAX_PATH_TIME = 15.0; // TODO: Time how long this takes and + 1 second
    
    private static final double MAX_ALIGN_TIME = 3.0; // TODO: Time how long this takes and + 1 second

    /**
     * This is the list of sequential commands that will execute during this auton.
     */
    public WoofFiveBallAuton(RobotContainer robot) {

        // Extend the intake, setup the shooter, and start running the intake.
        addCommands(
            new LEDSetCommand(robot.getLEDController(), LEDColor.WHITE_SOLID),
            
            new IntakeExtendCommand(robot.getIntake()),
            new ShooterControlCommand(robot.getShooter(), ShooterMode.TRENCH_SHOT),

            new WaitCommand(0.1),
            new IntakeAcquireForeverCommand(robot.getIntake()),
            new WaitCommand(1.0)
        );
        
        // Move the drivetrain back to the 2 balls in the opposite trench
        // Also reapproach the goal to shoot
        addCommands(
            new LEDSetCommand(robot.getLEDController(), LEDColor.YELLOW_SOLID),
            
            new DrivetrainRamseteCommand(
                robot.getDrivetrain(), 
                START_PATH,
                TO_GOAL_PATH
            ).robotRelative().withTimeout(MAX_PATH_TIME)
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
            ).withTimeout(MAX_ALIGN_TIME)
        );

        // Feed balls until the end of the Auton
        addCommands(
            new LEDSetCommand(robot.getLEDController(), LEDColor.GREEN_PULSE),
            new FeedBallsCommand(robot.getFunnel(), robot.getChimney()) 
        );
    }
}