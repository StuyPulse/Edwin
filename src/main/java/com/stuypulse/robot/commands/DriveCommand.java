package com.stuypulse.robot.commands;

import com.stuypulse.robot.subsystems.Drivetrain;
import com.stuypulse.robot.commands.DriveInstructions;

import com.stuypulse.stuylib.input.Gamepad;

import com.stuypulse.stuylib.streams.IStream;
import com.stuypulse.stuylib.streams.FilteredIStream;
import com.stuypulse.stuylib.streams.filters.LowPassFilter;

import com.stuypulse.stuylib.math.SLMath;

public class DriveCommand extends DriveInstructions {

    private Gamepad mGamepad;

    private IStream mRawSpeed;
    private IStream mRawAngle;

    private IStream mSpeed;
    private IStream mAngle;

    public DriveCommand(Drivetrain drivetrain, Gamepad gamepad) {
        // Pass Drivetrain to the super class
        super(drivetrain);

        // Store the gamepad
        mGamepad = gamepad;

        // Create an IStream that gets the speed from the controller
        mRawSpeed = () -> {
            return mGamepad.getRawRightTriggerAxis() - mGamepad.getRawLeftTriggerAxis();
        };

        // Create an IStream that gets the angle from the controller
        mRawAngle = () -> {
            return mGamepad.getLeftX();
        };

        // Create an IStream that filters the raw speed from the controller
        mSpeed = new FilteredIStream(mRawSpeed, (x) -> SLMath.square(x), new LowPassFilter(0.7));

        // Create an IStream that filters the raw angle from the controller
        mAngle = new FilteredIStream(mRawAngle, (x) -> SLMath.square(x), new LowPassFilter(0.7));
    }

    // Give the IStream's result for speed when the drivetrain wants it
    public double getSpeed() {
        return mSpeed.get();
    }

    // Give the IStream's result for angle when the drivetrain wants it
    public double getAngle() {
        return mAngle.get();
    }
}