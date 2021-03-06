package com.stuypulse.robot.subsystems;

import com.stuypulse.robot.Constants.Ports;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Pump extends SubsystemBase {

    private boolean enabled;
    private final Compressor compressor;
    private final AnalogInput pressureGauge;

    public Pump() {
        enabled = false;
        compressor = new Compressor();
        pressureGauge = new AnalogInput(Ports.Pneumatics.ANALOG_PRESSURE_SWITCH_PORT);
    }

    // Start Compressing the Robot
    public void compress() {
        enabled = true;
    }

    // Stop Compressing
    public void stop() {
        enabled = false;
    }

    // Get the current pressure of te pneumatics
    public double getPressure() {
        return 250.0 * (pressureGauge.getValue() / Ports.Pneumatics.ANALOG_PRESSURE_SWITCH_VOLTAGE_SUPPLY) - 25.0;
    }

    // Set the compressor
    public void set(boolean compressing) {
        enabled = compressing;
    }

    // Get the compressor
    public boolean get() {
        return enabled;
    }

    // Update the compressor to the current value
    @Override
    public void periodic() {
        super.periodic();

        if(get()) {
            compressor.start();
        } else {
            compressor.stop();
        }
    }

    /************************
     * SENDABLE INFORMATION *
     ************************/

    @Override
    public void initSendable(SendableBuilder builder) {
        super.initSendable(builder);

        builder.addDoubleProperty(
            "Robot Air Pressure", 
            () -> getPressure() / 1000.0, 
            (x) -> {});

        builder.addBooleanProperty(
            "Start Compressing", 
            this::get, 
            this::set);
    }

}