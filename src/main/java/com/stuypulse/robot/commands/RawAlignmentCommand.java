package com.stuypulse.robot.commands;

import com.stuypulse.robot.subsystems.Drivetrain;
import com.stuypulse.robot.commands.DriveInstructions;
import com.stuypulse.robot.Constants.Alignment;

import com.stuypulse.stuylib.control.Controller;
import com.stuypulse.stuylib.network.limelight.Limelight;
import com.stuypulse.stuylib.streams.filters.LowPassFilter;

public class RawAlignmentCommand extends DriveInstructions {

    // Controllers for Alignment
    private Controller mSpeed;
    private Controller mAngle;

    // Distance that the command will try to align with
    private double mTargetDistance;

    /**
     * This creates a command that aligns the robot
     * 
     * @param drivetrain Drivetrain used by command to move
     * @param distance   target distance for robot to drive to
     * @param speed      controller used to align distance
     * @param angle      controller used to align the angle
     */
    public RawAlignmentCommand(Drivetrain drivetrain, double distance, Controller speed, Controller angle) {
        // Pass Drivetrain to the super class
        super(drivetrain);

        // Initialize PID Controller for Speed
        mSpeed = speed;
        mSpeed.setErrorFilter(new LowPassFilter(Alignment.Speed.kInSmoothTime));
        mSpeed.setOutputFilter(new LowPassFilter(Alignment.Speed.kOutSmoothTime));

        // Initialize PID Controller for Angle
        mAngle = angle;
        mAngle.setErrorFilter(new LowPassFilter(Alignment.Angle.kInSmoothTime));
        mAngle.setOutputFilter(new LowPassFilter(Alignment.Angle.kOutSmoothTime));

        // Target distance for the Alignment Command
        mTargetDistance = distance;
    }

    /**
     * @return gets the difference from distance of robot and target distance
     */
    public final double getSpeedError() {
        double goal_pitch = Limelight.getTargetYAngle() + Alignment.Measurements.Limelight.kPitch;

        // Get the height of the goal reletive to the limelight
        double goal_height = Alignment.Measurements.kGoalHeight - Alignment.Measurements.Limelight.kHeight;

        // Get the distance of the the target from the limelight using geometry
        double goal_dist = goal_height / Math.tan(Math.toRadians(goal_pitch))
                - Alignment.Measurements.Limelight.kDistance;

        // Return the error from the target distance
        return goal_dist - mTargetDistance;
    }

    /**
     * @return gets the angle of the target in the limelight
     */
    public final double getAngleError() {
        return Limelight.getTargetXAngle() + Alignment.Measurements.Limelight.kYaw;
    }

    public double getSpeed() {
        if ( // Check if the angle is aligned before moving forward
        mAngle.getError() < Alignment.Speed.kMaxAngleErr && mAngle.getVelocity() < Alignment.Speed.kMaxAngleVel) {
            return mSpeed.update(getSpeedError());
        } else {
            return 0.0;
        }
    }

    public double getAngle() {
        return mAngle.update(getAngleError());
    }

    public Controller getSpeedController() {
        return mSpeed;
    }

    public Controller getAngleController() {
        return mAngle;
    }
}