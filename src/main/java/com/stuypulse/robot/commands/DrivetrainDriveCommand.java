package com.stuypulse.robot.commands;

import com.stuypulse.robot.subsystems.Drivetrain;
import com.stuypulse.robot.Constants.DrivetrainSettings;
import com.stuypulse.robot.commands.DrivetrainCommand;

import com.stuypulse.stuylib.input.Gamepad;

import com.stuypulse.stuylib.streams.IStream;
import com.stuypulse.stuylib.streams.FilteredIStream;
import com.stuypulse.stuylib.streams.filters.OrderedLowPassFilter;
import com.stuypulse.stuylib.math.SLMath;

/**
 * DrivetrainDriveCommand takes in a drivetrain and a gamepad and feeds the
 * signals to the drivetrain through a DriveCommand
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

        // Create an IStream that gets the speed from the controller
        this.rawSpeed = () -> {
            return this.gamepad.getRawRightTriggerAxis() - this.gamepad.getRawLeftTriggerAxis();
        };

        // Create an IStream that gets the angle from the controller
        this.rawAngle = () -> {
            return this.gamepad.getLeftX();
        };

        // Create an IStream that filters the raw speed from the controller
        this.speed = new FilteredIStream(this.rawSpeed, 
            (x) -> SLMath.deadband(x, DrivetrainSettings.SPEED_DEADBAND),
            (x) -> SLMath.spow(x, DrivetrainSettings.SPEED_POWER), 
            new OrderedLowPassFilter(DrivetrainSettings.SPEED_FILTER, DrivetrainSettings.SPEED_ORDER)
        );

        // Create an IStream that filters the raw angle from the controller
        this.angle = new FilteredIStream(this.rawAngle, 
            (x) -> SLMath.deadband(x, DrivetrainSettings.ANGLE_DEADBAND),
            (x) -> SLMath.spow(x, DrivetrainSettings.ANGLE_POWER), 
            new OrderedLowPassFilter(DrivetrainSettings.ANGLE_FILTER, DrivetrainSettings.ANGLE_ORDER)
        );
    }

    // Give the IStream's result for speed when the drivetrain wants it
    public double getSpeed() {

        if(gamepad.getRawBottomButton()) {
            drivetrain.setLowGear();
        } else {
            drivetrain.setHighGear();
        }

        double s = speed.get();

        if(DrivetrainSettings.COOL_RUMBLE) {
            gamepad.setRumble(Math.abs(s) * DrivetrainSettings.COOL_RUMBLE_MAG);
        }

        return s;
    }

    // Give the IStream's result for angle when the drivetrain wants it
    public double getAngle() {
        return angle.get();
    }

    // Humans need curvature drive because they're st00p1d
    public boolean useCurvatureDrive() {
        return true;
    }
}
