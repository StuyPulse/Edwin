package com.stuypulse.robot.commands;

import com.stuypulse.robot.Constants.Alignment;
import com.stuypulse.stuylib.network.limelight.Limelight;

public class OuterGoalAligner implements AlignmentCommand.Aligner {

    private double mTargetDistance;

    public OuterGoalAligner(double distance) {
        mTargetDistance = distance;
    }

    public double getSpeedError() {
        // TODO: have CV replace this command
        double goal_pitch = Limelight.getTargetYAngle() + Alignment.Measurements.Limelight.kPitch;
        double goal_height = Alignment.Measurements.kGoalHeight - Alignment.Measurements.Limelight.kHeight;
        double goal_dist = goal_height / Math.tan(Math.toRadians(goal_pitch))
                - Alignment.Measurements.Limelight.kDistance;

        // Return the error from the target distance
        return goal_dist - mTargetDistance;
    }

    public double getAngleError() {
        // TODO: have CV replace this command
        return Limelight.getTargetXAngle() + Alignment.Measurements.Limelight.kYaw;
    }
}