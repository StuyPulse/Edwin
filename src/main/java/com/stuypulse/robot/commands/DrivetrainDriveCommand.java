/* Copyright (c) 2021 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.robot.commands;

import com.stuypulse.stuylib.input.Gamepad;
import com.stuypulse.stuylib.math.SLMath;
import com.stuypulse.stuylib.streams.FilteredIStream;
import com.stuypulse.stuylib.streams.IStream;
import com.stuypulse.stuylib.streams.filters.LowPassFilter;

import com.stuypulse.robot.Constants.DrivetrainSettings;
import com.stuypulse.robot.subsystems.Drivetrain;
import com.stuypulse.robot.util.gear.Gear;

/**
 * DrivetrainDriveCommand takes in a drivetrain and a gamepad and feeds the signals to the
 * drivetrain through a DriveCommand
 */
public class DrivetrainDriveCommand extends DrivetrainCommand {

    private Gamepad gamepad;

    private IStream rawSpeed;
    private IStream rawAngle;

    private IStream speed;
    private IStream angle;

    public DrivetrainDriveCommand(Drivetrain drivetrain, Gamepad gamepad) {
        // Pass Drivetrain to the super class
        super(drivetrain);

        // Store the gamepad
        this.gamepad = gamepad;
    }

    public void initialize() {
        super.initialize();

        // Create an IStream that gets the speed from the controller
        this.rawSpeed =
                () -> {
                    return this.gamepad.getRightTrigger() - this.gamepad.getLeftTrigger();
                };

        // Create an IStream that gets the angle from the controller
        this.rawAngle =
                () -> {
                    return this.gamepad.getLeftX();
                };

        // Create an IStream that filters the raw speed from the controller
        this.speed =
                new FilteredIStream(
                        this.rawSpeed,
                        (x) -> SLMath.deadband(x, DrivetrainSettings.SPEED_DEADBAND.get()),
                        (x) -> SLMath.spow(x, DrivetrainSettings.SPEED_POWER.get()),
                        new LowPassFilter(DrivetrainSettings.SPEED_FILTER));

        // Create an IStream that filters the raw angle from the controller
        this.angle =
                new FilteredIStream(
                        this.rawAngle,
                        (x) -> SLMath.deadband(x, DrivetrainSettings.ANGLE_DEADBAND.get()),
                        (x) -> SLMath.spow(x, DrivetrainSettings.ANGLE_POWER.get()),
                        new LowPassFilter(DrivetrainSettings.ANGLE_FILTER));
    }

    // Give the IStream's result for speed when the drivetrain wants it
    public double getSpeed() {
        double s = speed.get();

        // Bottom Button is for driving backwards
        if (gamepad.getRawBottomButton()) {
            return -s;
        }

        return s;
    }

    // Give the IStream's result for angle when the drivetrain wants it
    public double getAngle() {
        double a = angle.get();

        return a;
    }

    // If the drivetrain goes into high or low gear
    public Gear getGear() {
        if (gamepad.getRawRightButton()) {
            return Gear.LOW;
        } else {
            return Gear.HIGH;
        }
    }

    // Humans need curvature drive because they're st00p1d
    public boolean useCurvatureDrive() {
        return true;
    }
}
