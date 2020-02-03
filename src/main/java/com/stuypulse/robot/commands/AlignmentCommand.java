package com.stuypulse.robot.commands;

import com.stuypulse.robot.subsystems.Drivetrain;
import com.stuypulse.robot.commands.DriveInstructions;
import com.stuypulse.robot.Constants.Alignment;

import com.stuypulse.stuylib.control.Controller;
import com.stuypulse.stuylib.streams.filters.LowPassFilter;

public class AlignmentCommand extends DriveInstructions {

    public interface Aligner {
        public double getSpeedError();
        public double getAngleError();
    }

    // Controllers for Alignment
    private Controller mSpeed;
    private Controller mAngle;

    // Distance that the command will try to align with
    private Aligner mAligner;

    /**
     * This creates a command that aligns the robot
     * 
     * @param drivetrain Drivetrain used by command to move
     * @param distance   target distance for robot to drive to
     * @param speed      controller used to align distance
     * @param angle      controller used to align the angle
     */
    public AlignmentCommand(Drivetrain drivetrain, Aligner aligner, Controller speed, Controller angle) {
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
        mAligner = aligner;
    }

    // Get the Speed Controller
    public Controller getSpeedController() {
        return mSpeed;
    }

    // Get the Angle controller
    public Controller getAngleController() {
        return mAngle;
    }

    // Update the speed if the angle is aligned
    public double getSpeed() {
        if ( // Check if the angle is aligned before moving forward
        mAngle.getError() < Alignment.Speed.kMaxAngleErr && mAngle.getVelocity() < Alignment.Speed.kMaxAngleVel) {
            return mSpeed.update(mAligner.getSpeedError());
        } else {
            return 0.0;
        }
    }

    // Update angle 
    public double getAngle() {
        return mAngle.update(mAligner.getAngleError());
    }
}