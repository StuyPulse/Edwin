package com.stuypulse.robot.commands;

import com.stuypulse.robot.Constants.Alignment;
import com.stuypulse.stuylib.math.Angle;
import com.stuypulse.stuylib.network.SmartNumber;
import com.stuypulse.stuylib.network.limelight.Limelight;

/**
 * The drivetrain goal aligner is an aligner that uses the limelight to send
 * instructions to the drivetrain on how it should move to align with the
 * target.
 */
public class DrivetrainGoalAligner implements DrivetrainAlignmentCommand.Aligner {

    private static SmartNumber currentDistance = new SmartNumber("GOAL Current Distance");
    private static SmartNumber targetDistance = new SmartNumber("GOAL Target Distance");
    private static SmartNumber distanceError = new SmartNumber("GOAL Target Error");

    private double distance;

    public DrivetrainGoalAligner(double distance) {
        this.distance = distance;
    }

    public void init() {
        // Turn on LEDs for CV
        Limelight.setLEDMode(Limelight.LEDMode.FORCE_ON);
    }

    public double getSpeedError() {
        if(Limelight.hasValidTarget()) {
            double goal_pitch = Limelight.getTargetYAngle() + Alignment.Measurements.Limelight.PITCH;
            double goal_height = Alignment.Measurements.GOAL_HEIGHT - Alignment.Measurements.Limelight.HEIGHT;
            double goal_dist = goal_height / Math.tan(Math.toRadians(goal_pitch))
                    - Alignment.Measurements.Limelight.DISTANCE;
    
            currentDistance.set(goal_dist);
            targetDistance.set(distance);
            distanceError.set(goal_dist - distance);

            if(goal_dist < Alignment.MIN_DISTANCE) {
                return 0;
            } else if(goal_dist > Alignment.MAX_DISTANCE) {
                return 0;
            }

            // Return the error from the target distance
            return 0 - (goal_dist - distance);
        } else {
            return 0;
        }
    }

    public Angle getAngleError() {
        if(Limelight.hasValidTarget()) {
            return Angle.fromDegrees(Limelight.getTargetXAngle() + Alignment.Measurements.Limelight.YAW.doubleValue());
        } else {
            return Angle.fromDegrees(0);
        }
    }

}