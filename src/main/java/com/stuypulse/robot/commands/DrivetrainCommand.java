package com.stuypulse.robot.commands;

import com.stuypulse.robot.subsystems.Drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * The DrivetrainCommand is an abstract class where getSpeed and getAngle are
 * overridden and sent to the drivetrain through a modified curvature drive.
 */
public abstract class DrivetrainCommand extends CommandBase {

    // Where the drivetrain is stored
    protected Drivetrain drivetrain;

    public DrivetrainCommand(Drivetrain drivetrain) {
        // Store the drivetrain
        this.drivetrain = drivetrain;

        // Add the drivetrain to the list of requirements
        addRequirements(this.drivetrain);
    }

    // Override these two functions in other commands
    public abstract double getSpeed();

    public abstract double getAngle();

    // Update the drivetrain with the new speed and angle
    public void execute() {
        drivetrain.curvatureDrive(this.getSpeed(), this.getAngle());
    }
}