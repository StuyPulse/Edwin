package com.stuypulse.robot.commands.auton.routines;

import com.stuypulse.robot.Constants;
import com.stuypulse.robot.commands.DrivetrainAlignmentCommand;
import com.stuypulse.robot.commands.DrivetrainGoalAligner;
import com.stuypulse.robot.commands.DrivetrainInnerGoalAligner;
import com.stuypulse.robot.commands.DrivetrainMovementCommand;
import com.stuypulse.robot.commands.DrivetrainStopCommand;
import com.stuypulse.robot.subsystems.Drivetrain;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class ShootThreeAutonCommand extends SequentialCommandGroup {

    private final double FEET_TO_MOVE_AFTER_SHOOTING = 1;

    public ShootThreeAutonCommand(Drivetrain drivetrain) {
        addCommands(
            new DrivetrainAlignmentCommand(drivetrain, new DrivetrainGoalAligner(Constants.SHOOT_FROM_START_TO_GOAL)),
            new DrivetrainAlignmentCommand(drivetrain, new DrivetrainInnerGoalAligner()),
        // TODO: Add shoot 3
            new DrivetrainMovementCommand(drivetrain, 0, FEET_TO_MOVE_AFTER_SHOOTING),
            new DrivetrainStopCommand(drivetrain)
        );
    }
    
}