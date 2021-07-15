package com.stuypulse.robot.commands.auton.routines;

import com.stuypulse.robot.RobotContainer;

import com.stuypulse.robot.commands.*;
import com.stuypulse.robot.subsystems.LEDController.LEDColor;
import com.stuypulse.robot.subsystems.Shooter.ShooterMode;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

/**
 * 
 * 
 * @author Sam Belliveau (sam.belliveau@gmail.com)
 */
public class WoofFiveBallAuton extends SequentialCommandGroup {
    
    private static final String START_PATH = "BarrelRacingPath/Start.wpilib.json";
    private static final String TO_GOAL_PATH = "BarrelRacingPath/ToGoal.wpilib.json";

    // These Deadlines are not supposed to be reached, 
    // but act as a guarentee that the auton will complete ontime
    private static final double MAX_START_TIME = 4.0;
    private static final double MAX_TO_GOAL_TIME = 6.0;
    
    private static final double MAX_ALIGN_TIME = 3.0;

    public WoofFiveBallAuton(RobotContainer robot) {

        // Extend the intake, setup the shooter, and start running the intake.
        addCommands(
            new LEDSetCommand(robot.getLEDController(), LEDColor.WHITE_SOLID),
            
            new IntakeExtendCommand(robot.getIntake()),
            new ShooterControlCommand(robot.getShooter(), ShooterMode.INITIATION_LINE),

            new WaitCommand(0.1),
            new IntakeAcquireForeverCommand(robot.getIntake()),
            new WaitCommand(1.0)
        );
        
        // Move the drivetrain back to the 2 balls in the opposite trench
        addCommands(
            new LEDSetCommand(robot.getLEDController(), LEDColor.YELLOW_SOLID),
            new DrivetrainRamseteCommand(robot.getDrivetrain(), START_PATH).robotRelative().withTimeout(MAX_START_TIME)
        );
        
        // Then move the drivetrain closer to the goal and feed the balls until they are up in the shooter
        addCommands(
            new LEDSetCommand(robot.getLEDController(), LEDColor.ORANGE_SOLID),
            new ParallelCommandGroup(
                new FeedBallsAutomaticCommand(robot.getChimney(), robot.getFunnel()),
                new DrivetrainRamseteCommand(robot.getDrivetrain(), TO_GOAL_PATH).fieldRelative()
            ).withTimeout(MAX_TO_GOAL_TIME)
        ); 

        // Align the robot to the target using the limelight
        addCommands(
            new LEDSetCommand(robot.getLEDController(), LEDColor.RED_SOLID),
            new DrivetrainAutomaticAlign(robot.getDrivetrain(), robot.getShooter()).withTimeout(MAX_ALIGN_TIME)
        );

        // Feed balls until the end of the Auton
        addCommands(
            new LEDSetCommand(robot.getLEDController(), LEDColor.GREEN_PULSE),
            new FeedBallsCommand(robot.getFunnel(), robot.getChimney()) 
        );
    }
}