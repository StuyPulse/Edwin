package com.stuypulse.robot.commands;

import com.stuypulse.robot.subsystems.Drivetrain;
import com.stuypulse.robot.commands.DriveInstructions;
import com.stuypulse.robot.Constants.Alignment;

import com.stuypulse.stuylib.control.PIDController;
import com.stuypulse.stuylib.streams.filters.LowPassFilter;

public class AlignmentCommand extends DriveInstructions {

    // PID Controllers for Alignment
    private PIDController mSpeedPID;
    private PIDController mAnglePID;

    public AlignmentCommand(Drivetrain drivetrain) {
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
    }

    public double getSpeedError() {
        // TODO: Get CV team to implement this function
        return 0.0;
    }

    public double getAngleError() {
        // TODO: Get CV team to implement this function
        return 0.0;
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