package com.stuypulse.robot.commands;

import com.stuypulse.robot.subsystems.Drivetrain;
import com.stuypulse.robot.Constants.DrivetrainSettings;
import com.stuypulse.robot.commands.DrivetrainCommand;

import com.stuypulse.stuylib.input.Gamepad;

import com.stuypulse.stuylib.streams.IStream;
import com.stuypulse.stuylib.streams.filters.IStreamFilter;
import com.stuypulse.stuylib.streams.filters.LowPassFilter;
import com.stuypulse.stuylib.streams.filters.SpeedProfile;
import com.stuypulse.stuylib.streams.FilteredIStream;
import com.stuypulse.stuylib.math.SLMath;

/**
 * DrivetrainDriveCommand takes in a drivetrain and a gamepad and feeds the
 * signals to the drivetrain through a DriveCommand
 */
public class DrivetrainDriveCommand extends DrivetrainCommand {

    private Gamepad gamepad;

    private boolean useFiltering;

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

        // Use filtering by default
        this.useFiltering = true;

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
            
            //new SpeedProfile(2.5 / 50.0, 1.0 / 420.0)
            new LowPassFilter(DrivetrainSettings.SPEED_FILTER)
        );

        // Create an IStream that filters the raw angle from the controller
        this.angle = new FilteredIStream(this.rawAngle, 
            (x) -> SLMath.deadband(x, DrivetrainSettings.ANGLE_DEADBAND),
            (x) -> SLMath.spow(x, DrivetrainSettings.ANGLE_POWER), 
            new LowPassFilter(DrivetrainSettings.ANGLE_FILTER)
        );
    }

    // Check DPad for enabling or disableing filters
    private boolean checkDPad() {
        if(gamepad.getRawDPadUp()) {
            return (useFiltering = true);
        }

        if(gamepad.getRawDPadDown()) {
            return (useFiltering = false);
        }

        return useFiltering;
    }

    // Give the IStream's result for speed when the drivetrain wants it
    public double getSpeed() {
        double s = speed.get();

        if(!checkDPad()) {
            s = rawSpeed.get();
        }

        return s;
    }

    // Give the IStream's result for angle when the drivetrain wants it
    public double getAngle() {
        double a = angle.get();

        if(!checkDPad()) {
            a = rawAngle.get();
        }

        return a;
    }

    // If the drivetrain goes into high or low gear
    public Drivetrain.Gear getGear() {
        if(gamepad.getRawBottomButton()) {
            return Drivetrain.Gear.LOW;
        } else {
            return Drivetrain.Gear.HIGH;
        }
    }

    // Humans need curvature drive because they're st00p1d
    public boolean useCurvatureDrive() {
        return true;
    }
}
