package com.stuypulse.robot.commands;

import com.stuypulse.robot.subsystems.Drivetrain;

/**
 * Extends off of the DrivetrainPIDAlignmentCommand and uses its controllers to
 * align the robot very specifically. It uses the robots encoders and navx to
 * get these values, and will do other things like, drive in a straight line
 * while making sure the angle is correct, and turning before driving.
 * 
 * WARNING: It is HIGHLY recommended NOT to combine angle and distance commands
 * as turning the robot can lead to bad measurements with distance and
 * visa-versa. It is only included to make code more specific
 */
public class DrivetrainMovementCommand extends DrivetrainPIDAlignmentCommand {

    /**
     * This aligner uses encoders and the navx on the drivetrain to move the
     * drivetrain a very specific amount. First it turns to the desired angle and
     * then it moves the desired amount.
     */
    private static class DrivetrainMovementAligner implements DrivetrainAlignmentCommand.Aligner {

        private Drivetrain mDrivetrain;

        private double mGoalAngle;
        private double mGoalDistance;
        private boolean mJustTurning;

        public DrivetrainMovementAligner(Drivetrain drivetrain, double angle, double distance, boolean justTurning) {
            mDrivetrain = drivetrain;
            mJustTurning = justTurning;
        }

        public double getSpeedError() {
            if (mJustTurning) {
                return 0.0;
            }

            // TODO: MEASURE CURRENT DISTANCE DRIVEN AND SUBRACT IT FROM GOAL
            return 0.0;
        }

        public double getAngleError() {
            // TODO: MEASURE CURRENT ANGLE AND SUBRACT IT FROM GOAL
            return 0.0;
        }
    }

    /**
     * Creates command that moves drivetrain very specific amounts
     * 
     * @param drivetrain the drivetrain you want to move
     * @param angle      the angle you want it to turn before moving (DO NOT USE)
     * @param distance   the distance you want it to travel
     */
    public DrivetrainMovementCommand(Drivetrain drivetrain, double angle, double distance) {
        super(drivetrain, new DrivetrainMovementAligner(drivetrain, angle, distance, false));
    }

    /**
     * Creates command that moves drivetrain very specific amounts
     * 
     * @param drivetrain the drivetrain you want to move
     * @param angle      the angle you want it to turn
     */
    public DrivetrainMovementCommand(Drivetrain drivetrain, double angle) {
        super(drivetrain, new DrivetrainMovementAligner(drivetrain, angle, 0.0, true));
    }
}