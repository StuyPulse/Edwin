package com.stuypulse.robot.commands;

import com.stuypulse.robot.subsystems.Drivetrain;
import com.stuypulse.robot.commands.DriveInstructions;
import com.stuypulse.robot.Constants.Alignment;

import com.stuypulse.stuylib.control.PIDController;
import com.stuypulse.stuylib.network.limelight.Limelight;
import com.stuypulse.stuylib.streams.filters.LowPassFilter;

public class AlignmentCommand extends DriveInstructions {

    // PID Controllers for Alignment
    private PIDController mSpeedPID;
    private PIDController mAnglePID;

    private double mTargetDistance;

    public AlignmentCommand(Drivetrain drivetrain, double distance) {
        // Pass Drivetrain to the super class
        super(drivetrain);

        // Initialize PID Controller for Speed
        mSpeedPID = new PIDController(Alignment.Speed.kP, Alignment.Speed.kI, Alignment.Speed.kD);
        mSpeedPID.setErrorFilter(new LowPassFilter(Alignment.Speed.kInSmoothTime));
        mSpeedPID.setOutputFilter(new LowPassFilter(Alignment.Speed.kOutSmoothTime));

        // Initialize PID Controller for Angle
        mAnglePID = new PIDController(Alignment.Angle.kP, Alignment.Angle.kI, Alignment.Angle.kD);
        mAnglePID.setErrorFilter(new LowPassFilter(Alignment.Angle.kInSmoothTime));
        mAnglePID.setOutputFilter(new LowPassFilter(Alignment.Angle.kOutSmoothTime));

        // Target distance for the Alignment Command
        mTargetDistance = distance;
    }

    public double getSpeedError() {
        double goal_pitch = Limelight.getTargetYAngle() + Alignment.Measurements.Limelight.kPitch;
    
        // Get the height of the goal reletive to the limelight
        double goal_height = Alignment.Measurements.kGoalHeight - Alignment.Measurements.Limelight.kHeight;
    
        // Get the distance of the the target from the limelight using geometry
        double goal_dist = goal_height / Math.tan(Math.toRadians(goal_pitch)) - Alignment.Measurements.Limelight.kDistance;

        // Return the error from the target distance
        return goal_dist - mTargetDistance;
    }

    public double getAngleError() {
        return Limelight.getTargetXAngle() + Alignment.Measurements.Limelight.kYaw;
    }

    public double getSpeed() {
        if ( // Check if the angle is aligned before moving forward
        mAnglePID.getError() < Alignment.Speed.kMaxAngleErr && mAnglePID.getVelocity() < Alignment.Speed.kMaxAngleVel) {
            return mSpeedPID.update(getSpeedError());
        } else {
            return 0.0;
        }
    }

    public double getAngle() {
        return mAnglePID.update(getAngleError());
    }
}