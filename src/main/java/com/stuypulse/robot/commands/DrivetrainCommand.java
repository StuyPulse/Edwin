package com.stuypulse.robot.commands;

import com.stuypulse.robot.subsystems.Drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * The DrivetrainCommand is an abstract class where getSpeed and getAngle are
 * overridden and sent to the drivetrain through a modified curvature drive.
 */
public abstract class DrivetrainCommand extends CommandBase {

    // Where the drivetrain is stored
    private Drivetrain mDrivetrain;

    public DrivetrainCommand(Drivetrain drivetrain) {
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