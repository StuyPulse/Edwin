package com.stuypulse.robot.util.gear;

import com.stuypulse.robot.Constants.DrivetrainSettings.Encoders;

public enum Gear {

    // Create all the gear settings with their ratios

    HIGH(Encoders.HIGH_GEAR_DISTANCE_PER_ROTATION),
    LOW(Encoders.LOW_GEAR_DISTANCE_PER_ROTATION);

    // Store the gear ratio

    private final Number ratio;

    private Gear(Number ratio) {
        this.ratio = ratio;
    }

    // Functions to use the gear ratio

    public Number getRatio() {
        return this.ratio;
    }

    public double getScaledDistance(double rotations) {
        return getRatio().doubleValue() * rotations;
    }

}
