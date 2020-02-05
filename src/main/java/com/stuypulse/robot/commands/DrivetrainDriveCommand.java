package com.stuypulse.robot.commands;

import com.stuypulse.robot.subsystems.Drivetrain;
import com.stuypulse.robot.Constants.DrivetrainSettings;
import com.stuypulse.robot.commands.DrivetrainCommand;

import com.stuypulse.stuylib.input.Gamepad;

import com.stuypulse.stuylib.streams.IStream;
import com.stuypulse.stuylib.streams.FilteredIStream;
import com.stuypulse.stuylib.streams.filters.LowPassFilter;

import com.stuypulse.stuylib.math.SLMath;

/**
 * DrivetrainDriveCommand takes in a drivetrain and a gamepad and feeds the
 * signals to the drivetrain through a DriveCommand
 */
public class DrivetrainDriveCommand extends DrivetrainCommand {

    private Gamepad mGamepad;

    private IStream mRawSpeed;
    private IStream mRawAngle;

    private IStream mSpeed;
    private IStream mAngle;

    public DrivetrainDriveCommand(Drivetrain drivetrain, Gamepad gamepad) {
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
        mSpeed = new FilteredIStream(mRawSpeed, 
            (x) -> SLMath.square(x), 
            new LowPassFilter(DrivetrainSettings.kRCSpeed)
        );

        // Create an IStream that filters the raw angle from the controller
        mAngle = new FilteredIStream(mRawAngle, 
            (x) -> SLMath.square(x), 
            new LowPassFilter(DrivetrainSettings.kRCAngle)
        );
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
