package com.stuypulse.robot.commands.auton.routines;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import com.stuypulse.robot.commands.DrivetrainGoalAligner;
import com.stuypulse.robot.commands.DrivetrainMovementCommand;
import com.stuypulse.robot.commands.DrivetrainAlignmentCommand;
import com.stuypulse.robot.commands.DrivetrainStopCommand;
import com.stuypulse.robot.subsystems.Drivetrain;
import com.stuypulse.robot.Constants;

public class ShootThreeAutonCommand extends SequentialCommandGroup {
    public ShootThreeAutonCommand(Drivetrain drivetrain) {
        addCommands(
            new DrivetrainAlignmentCommand(drivetrain, new DrivetrainGoalAligner(Constants.SHOOT_FROM_START_TO_GOAL)),
        
        // TODO: Add shoot 3
            new DrivetrainMovementCommand(drivetrain, 0, 12),
            new DrivetrainStopCommand(drivetrain)
        );
    }
    
}