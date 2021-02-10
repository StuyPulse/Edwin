package com.stuypulse.robot.subsystems;

import com.stuypulse.robot.Constants;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Pump extends SubsystemBase {

    private boolean compressing;
    private final Compressor compressor;
    private final AnalogInput pressureGauge;

    public Pump() {
        compressing = false;
        compressor = new Compressor();
        pressureGauge = new AnalogInput(Constants.Pneumatics.ANALOG_PRESSURE_SWITCH_PORT);
    }

    // Start Compressing the Robot
    public void compress() {
        compressor.start();
    }

    // Stop Compressing
    public void stop() {
        compressor.stop();
    }

    // Get the current pressure of te pneumatics
    public double getPressure() {
        return 250.0 * (pressureGauge.getValue() / Constants.Pneumatics.ANALOG_PRESSURE_SWITCH_VOLTAGE_SUPPLY) - 25.0;
    }

    // Run the compressor if the boolean is set
    public void periodic() {
        if(compressing) {
            compress();
        } else {
            stop();
        }
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        super.initSendable(builder);
        
        builder.addDoubleProperty(
            "Robot Air Pressure", 
            () -> getPressure() / 1000.0, 
            (x) -> {});
        
        builder.addBooleanProperty(
            "Start Compressing", 
            () -> compressing, 
            (x) -> { compressing = x; });
    }

}