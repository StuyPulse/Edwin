package com.stuypulse.robot.commands;

import com.stuypulse.robot.subsystems.Drivetrain;

/**
 * Extends off of the DrivetrainPIDAlignmentCommand and uses its controllers to
 * align the robot very specifically. It uses the robots encoders and navx to
 * get these values, and will do other things like, drive in a straight line
 * while making sure the angle is correct, and turning before driving.
 * 
 * WARNING: It is not recommended to combine angle and distance in one command
 * as turning the robot can lead to bad measurements with distance and
 * visa-versa. It is only included to make code more specific.
 */
public class DrivetrainMovementCommand extends DrivetrainPIDAlignmentCommand {

    /**
     * This aligner uses encoders and the navx on the drivetrain to move the
     * drivetrain a very specific amount. First it turns to the desired angle and
     * then it moves the desired amount.
     */
    public static class Aligner implements DrivetrainAlignmentCommand.Aligner {

        private Drivetrain mDrivetrain;

        private double mGoalAngle;
        private double mGoalDistance;
        private boolean mJustTurning;

        public Aligner(Drivetrain drivetrain, double angle, double distance) {
            mDrivetrain = drivetrain;
            mJustTurning = false;

            mGoalAngle = (mDrivetrain.getGyroAngle() + angle + 360) % 360;
            mGoalDistance = mDrivetrain.getGreyhillDistance() + distance;
        }

        public Aligner(Drivetrain drivetrain, double angle) {
            mDrivetrain = drivetrain;
            mJustTurning = true;

            mGoalAngle = (mDrivetrain.getGyroAngle() + angle + 360) % 360;
        }

        public double getSpeedError() {
            if (mJustTurning) {
                return 0.0;
            } else {
                return mGoalDistance - mDrivetrain.getGreyhillDistance();
            }
        }

        public double getAngleError() {
            double angleError = mGoalAngle - mDrivetrain.getGyroAngle();

            if (angleError > 180) {
                angleError -= 360;
            }

            if (angleError < -180) {
                angleError += 360;
            }

            return angleError;
        }
    }

    /**
     * Creates command that moves drivetrain very specific amounts
     * 
     * @param drivetrain the drivetrain you want to move
     * @param angle      the angle you want it to turn before moving (this may*
     *                   affect distance)
     * @param distance   the distance you want it to travel
     */
    public DrivetrainMovementCommand(Drivetrain drivetrain, double angle, double distance) {
        super(drivetrain, new Aligner(drivetrain, angle, distance));
    }

    /**
     * Creates command that moves drivetrain very specific amounts
     * 
     * @param drivetrain the drivetrain you want to move
     * @param angle      the angle you want it to turn
     */
    public DrivetrainMovementCommand(Drivetrain drivetrain, double angle) {
        super(drivetrain, new Aligner(drivetrain, angle));
    }
}