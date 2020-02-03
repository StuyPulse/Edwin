package com.stuypulse.robot.commands;

import java.util.HashSet;
import java.util.Set;

import com.stuypulse.robot.subsystems.Drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;

public abstract class DriveInstructions extends CommandBase {

    // Where the drivetrain is stored
    private Drivetrain mDrivetrain;

    public DriveInstructions(Drivetrain drivetrain) {
        // Store the drivetrain
        mDrivetrain = drivetrain;

        // Add the drivetrain to the list of requirements
        addRequirements(mDrivetrain);
    }

    // Override these two functions in other commands
    public abstract double getSpeed();

    public abstract double getAngle();

    // Update the drivetrain with the new speed and angle
    public void execute() {
        mDrivetrain.curvatureDrive(this.getSpeed(), this.getAngle());
    }
}