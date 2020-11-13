package com.stuypulse.robot.commands;

import com.stuypulse.robot.subsystems.Drivetrain;
import com.stuypulse.stuylib.math.Angle;

/**
 */
public class DrivetrainArcCommand extends DrivetrainAlignmentCommand {

    /**
     * This aligner uses encoders and the navx on the drivetrain to move the
     * drivetrain a very specific amount. First it turns to the desired angle and
     * then it moves the desired amount.
     */
    public static class Aligner implements DrivetrainAlignmentCommand.Aligner {

        private Drivetrain drivetrain;

        private double angle;
        private double distance;

        private double startAngle;
        private double startDistance;

        public Aligner(Drivetrain drivetrain, double angle, double distance) {
            this.drivetrain = drivetrain;

            this.angle = angle;
            this.distance = distance;

            init();
        }

        /**
         * Set goals based on when the command is initialized
         */
        public void init() {
            startAngle = drivetrain.getGyroAngle().toDegrees();
            startDistance = drivetrain.getDistance();
        }

        public double getSpeedError() {
            return startDistance + distance - drivetrain.getDistance();
        }

        public Angle getAngleError() {
            return Angle.fromDegrees(startAngle + (angle * (1.0 - getSpeedError() / distance))).add(drivetrain.getGyroAngle());
        }
    }

    // Speed the drivetrain moves forward at
    private double speed;

    /**
     * @param drivetrain drivetrain used to move around
     * @param angle amount turned throughout arc
     * @param distance length of the arc
     * @param speed the speed that the drivetrain moves at
     */
    public DrivetrainArcCommand(Drivetrain drivetrain, double angle, double distance, double speed) {
        super(drivetrain, new Aligner(drivetrain, angle, distance));
        this.speed = speed;
    }

    /**
     * @param drivetrain drivetrain used to move around
     * @param angle amount turned throughout arc
     * @param distance length of the arc
     */
    public DrivetrainArcCommand(Drivetrain drivetrain, double angle, double distance) {
        this(drivetrain, angle, distance, 1.0);
    }

    public double getSpeed() {
        return super.getSpeed() * speed;
    }
}