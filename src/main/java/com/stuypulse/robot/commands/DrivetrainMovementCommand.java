package com.stuypulse.robot.commands;

import com.stuypulse.robot.subsystems.Drivetrain;

/**
 * A command that extends DrivetrainPIDAlignmentCommand and uses a
 * DrivetrainMovementAligner to move the drivetrain a very specific amount.
 */
public class DrivetrainMovementCommand extends DrivetrainPIDAlignmentCommand {

    /**
     * Creates command that moves drivetrain very specific amounts
     * 
     * @param drivetrain the drivetrain you want to move
     * @param angle      the angle you want it to turn before moving
     * @param distance   the distance you want it to travel
     */
    public DrivetrainMovementCommand(Drivetrain drivetrain, double angle, double distance) {
        super(drivetrain, new DrivetrainMovementAligner(drivetrain, angle, distance));
    }
}