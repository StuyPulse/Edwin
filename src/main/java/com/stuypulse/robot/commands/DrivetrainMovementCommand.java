/* Copyright (c) 2021 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.robot.commands;

import com.stuypulse.stuylib.math.Angle;

import com.stuypulse.robot.subsystems.Drivetrain;

/**
 * Extends off of the DrivetrainPIDAlignmentCommand and uses its controllers to align the robot very
 * specifically. It uses the robots encoders and navx to get these values, and will do other things
 * like, drive in a straight line while making sure the angle is correct, and turning before
 * driving.
 *
 * <p>WARNING: It is not recommended to combine angle and distance in one command as turning the
 * robot can lead to bad measurements with distance and visa-versa. It is only included to make code
 * more specific.
 */
public class DrivetrainMovementCommand extends DrivetrainAlignmentCommand {

    /**
     * This aligner uses encoders and the navx on the drivetrain to move the drivetrain a very
     * specific amount. First it turns to the desired angle and then it moves the desired amount.
     */
    public static class Aligner implements DrivetrainAlignmentCommand.Aligner {

        private Drivetrain drivetrain;

        private Angle angle;
        private double distance;

        private Angle goalAngle;
        private double goalDistance;

        public Aligner(Drivetrain drivetrain, double angle, double distance) {
            this.drivetrain = drivetrain;

            this.angle = Angle.fromDegrees(angle);
            this.distance = distance;

            init();
        }

        public Aligner(Drivetrain drivetrain, double angle) {
            this(drivetrain, angle, 0.0);
        }

        private Angle getGyroAngle() {
            return drivetrain.getAngle();
        }

        private double getDistance() {
            return drivetrain.getDistance();
        }

        /** Set goals based on when the command is initialized */
        public void init() {
            goalAngle = getGyroAngle().add(angle);
            goalDistance = getDistance() + distance;
        }

        public double getSpeedError() {
            return goalDistance - getDistance();
        }

        public Angle getAngleError() {
            return goalAngle.sub(getGyroAngle());
        }
    }

    /** This command lets you drive forward x amount of feet */
    public static class DriveCommand extends DrivetrainMovementCommand {

        /**
         * @param drivetrain drivetrain used to move
         * @param distance number of feet used to turn
         */
        public DriveCommand(Drivetrain drivetrain, double distance) {
            super(drivetrain, 0, distance);
        }
    }

    /** This command lets you turn a certain angle */
    public static class TurnCommand extends DrivetrainMovementCommand {

        /**
         * @param drivetrain drivetrain used to turn
         * @param angle angle that you want to turn
         */
        public TurnCommand(Drivetrain drivetrain, double angle) {
            super(drivetrain, angle);
        }
    }

    /**
     * Creates command that moves drivetrain very specific amounts
     *
     * @param drivetrain the drivetrain you want to move
     * @param angle the angle you want it to turn before moving (this may* affect distance)
     * @param distance the distance you want it to travel (feet)
     */
    public DrivetrainMovementCommand(Drivetrain drivetrain, double angle, double distance) {
        super(drivetrain, new Aligner(drivetrain, angle, distance));
    }

    /**
     * Creates command that moves drivetrain very specific amounts
     *
     * @param drivetrain the drivetrain you want to move
     * @param angle the angle you want it to turn
     */
    public DrivetrainMovementCommand(Drivetrain drivetrain, double angle) {
        super(drivetrain, new Aligner(drivetrain, angle));
    }
}
