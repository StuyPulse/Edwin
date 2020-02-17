package com.stuypulse.robot.commands;

import com.stuypulse.robot.Constants.Alignment;
import com.stuypulse.stuylib.network.limelight.Limelight;

/**
 * The drivetrain goal aligner is an aligner that uses the limelight to send
 * instructions to the drivetrain on how it should move to align with the
 * target.
 */
public class DrivetrainGoalAligner implements DrivetrainAlignmentCommand.Aligner {

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
    
            // Return the error from the target distance
            return 0 - (goal_dist - distance);
        } else {
            return 0;
        }
    }

    public double getAngleError() {
        if(Limelight.hasValidTarget()) {
            return 0 - (Limelight.getTargetXAngle() + Alignment.Measurements.Limelight.YAW);
        } else {
            return 0;
        }
    }

}