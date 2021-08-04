package com.stuypulse.robot.commands.auton.routines;

import com.stuypulse.robot.RobotContainer;
import com.stuypulse.robot.Constants.Alignment;
import com.stuypulse.robot.Constants.AutoSettings;

import com.stuypulse.robot.commands.*;
import com.stuypulse.robot.subsystems.LEDController.LEDColor;
import com.stuypulse.robot.subsystems.Shooter.ShooterMode;

import edu.wpi.first.wpilibj2.command.*;

/**
 * Mobility
 */
public class MobilityAuton extends SequentialCommandGroup {
    
    public MobilityAuton(RobotContainer robot) {

        /** 
         * MOVE!
         */
        addCommands(
            new LEDSetCommand(robot.getLEDController(), LEDColor.WHITE_SOLID),
            new DrivetrainMovementCommand(
                robot.getDrivetrain(), 
                0, 
                2.0
            )
        );
        
    }
}