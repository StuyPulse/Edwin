package com.stuypulse.robot.commands.auton.routines;

import com.stuypulse.robot.RobotContainer;
import com.stuypulse.robot.commands.DrivetrainRamseteCommand;
import com.stuypulse.robot.commands.DrivetrainGoalCommand;
import com.stuypulse.robot.commands.FeedBallsCommand;
import com.stuypulse.robot.commands.IntakeAcquireForeverCommand;
import com.stuypulse.robot.commands.IntakeExtendCommand;
import com.stuypulse.robot.commands.LEDSetCommand;
import com.stuypulse.robot.commands.ShooterControlCommand;
import com.stuypulse.robot.subsystems.LEDController;
import com.stuypulse.robot.subsystems.LEDController.LEDColor;
import com.stuypulse.robot.subsystems.Shooter.ShooterMode;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class EightBallAuton extends SequentialCommandGroup {
    
    private static final String START_PATH = "EightBallAuto/output/Start.wpilib.json";
    private static final String TO_REND_PATH = "EightBallAuto/output/ToRendezvous.wpilib.json";
    private static final String R_BALLS_PATH = "EightBallAuto/output/RBalls.wpilib.json";
    private static final String TO_TRENCH_PATH = "EightBallAuto/output/ToTrench.wpilib.json";

    private static final double START_WAIT_TIME = 1.75;
    private static final double START_SHOOT_TIME = 0.5; 

    public EightBallAuton(RobotContainer robot) {
        
        final LEDController leds = robot.getLEDController();

        addCommands(
            new LEDSetCommand(leds, LEDColor.WHITE_SOLID),
            
            new ShooterControlCommand(robot.getShooter(), ShooterMode.TRENCH_SHOT),
            new IntakeExtendCommand(robot.getIntake()),
            new WaitCommand(0.05),
            new IntakeAcquireForeverCommand(robot.getIntake()),
            new WaitCommand(1.0)
        );

        addCommands(
            new LEDSetCommand(leds, LEDColor.GREEN_SOLID),
            
            new WaitCommand(START_WAIT_TIME),
            new FeedBallsCommand(
                robot.getFunnel(), 
                robot.getChimney()
            ).withTimeout(START_SHOOT_TIME)
        );

        addCommands(
            new LEDSetCommand(leds, LEDColor.RED_SOLID),

            new ParallelDeadlineGroup(
                new DrivetrainRamseteCommand(
                    robot.getDrivetrain(),
                    START_PATH,
                    TO_REND_PATH,
                    R_BALLS_PATH,
                    TO_TRENCH_PATH
                ).robotRelative(),
                
                new FeedBallsCommand(
                    robot.getFunnel(), 
                    robot.getChimney()
                ).withTimeout(3.0 * START_SHOOT_TIME)

            )
        );
        
        addCommands(
            new LEDSetCommand(leds, LEDColor.BLUE_SOLID),

            new ParallelDeadlineGroup(
                new DrivetrainGoalCommand(robot.getDrivetrain(), -1),

                new FeedBallsCommand(
                    robot.getFunnel(), 
                    robot.getChimney()
                )
            )
        );

        addCommands(
            new LEDSetCommand(leds, LEDColor.GREEN_SOLID),

            new FeedBallsCommand(
                robot.getFunnel(), 
                robot.getChimney()
            )
        );

    }

}
