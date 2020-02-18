package com.stuypulse.robot.util;

import com.stuypulse.robot.Constants;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Compressor;

public class Pneumatics {

    private final Compressor compressor;
    private final AnalogInput pressureGauge;

    public Pneumatics() {
        compressor = new Compressor();
        pressureGauge = new AnalogInput(Constants.Pneumatics.ANALOG_PRESSURE_SWITCH_PORT);
    }

    public void startCompressing() {
        compressor.start();
    }

    public void stopCompressing() {
        compressor.stop();
    }

    public double getPressure() {
        return 250.0 * (pressureGauge.getValue() / Constants.Pneumatics.ANALOG_PRESSURE_SWITCH_VOLTAGE_SUPPLY) - 25.0;
    }

}