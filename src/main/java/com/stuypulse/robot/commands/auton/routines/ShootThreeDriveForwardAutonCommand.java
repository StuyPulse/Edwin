package com.stuypulse.robot.commands.auton.routines;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import com.stuypulse.robot.commands.DrivetrainDriveCommand;
import com.stuypulse.robot.commands.DrivetrainGoalAligner;
import com.stuypulse.robot.commands.DrivetrainPIDAlignmentCommand;
import com.stuypulse.robot.subsystems.Drivetrain;
import com.stuypulse.stuylib.input.Gamepad;
import com.stuypulse.robot.Constants;

public class ShootThreeDriveForwardAutonCommand extends SequentialCommandGroup {
    private final Drivetrain drivetrain = new Drivetrain();
    private final Gamepad gamepad = new Gamepad();
    public ShootThreeDriveForwardAutonCommand() {
        addCommands(new DrivetrainPIDAlignmentCommand(drivetrain, new DrivetrainGoalAligner(Constants.DISTANCE_FROM_START_TO_GOAL)));
        //Add shoot 3 command
        addCommands(new DrivetrainDriveCommand(drivetrain, gamepad));
    }
    
}