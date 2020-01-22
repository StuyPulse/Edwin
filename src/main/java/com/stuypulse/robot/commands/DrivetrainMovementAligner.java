package com.stuypulse.robot.commands;

import com.stuypulse.robot.subsystems.Drivetrain;

/**
 * This aligner uses encoders and the navx on the drivetrain to move the
 * drivetrain a very specific amount. First it turns to the desired angle and
 * then it moves the desired amount.
 */
public class DrivetrainMovementAligner implements DrivetrainAlignmentCommand.Aligner {

    private Drivetrain mDrivetrain;

    private double mGoalAngle;
    private double mGoalDistance;

    public DrivetrainMovementAligner(Drivetrain drivetrain, double angle, double distance) {
        mDrivetrain = drivetrain;
    }

    public double getSpeedError() {
        // TODO: MEASURE CURRENT DISTANCE DRIVEN AND SUBRACT IT FROM GOAL
        return 0.0;
    }

    public double getAngleError() {
        // TODO: MEASURE CURRENT ANGLE AND SUBRACT IT FROM GOAL
        return 0.0;
    }

}