/* Copyright (c) 2021 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import com.stuypulse.robot.Constants.Colors;
import com.stuypulse.robot.Constants.Ports;
import com.stuypulse.robot.Constants.WoofSettings;
import com.stuypulse.stuylib.streams.filters.IFilter;
import com.stuypulse.stuylib.streams.filters.LowPassFilter;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Woof extends SubsystemBase {
    // Motor and Encoder
    private final CANSparkMax motor;
    private final CANEncoder encoder;
    // private ColorSensor colorSensor;

    private double turnSpeed;
    private final IFilter turnFilter;

    public Woof() {
        motor = new CANSparkMax(Ports.Woof.MOTOR_PORT, MotorType.kBrushless);
        encoder = motor.getEncoder();

        turnSpeed = 0.0;
        turnFilter = new LowPassFilter(WoofSettings.TURN_FILTER);

        // colorSensor = new ColorSensor();

        // Add Children to Subsystem
        //  addChild("Color Sensor", colorSensor);
    }

    // Controlling the motor
    public void turn(double speed) {
        turnSpeed = turnFilter.get(speed);
    }

    public void stop() {
        turnSpeed = 0;
    }

    // Get number of rotations the motor has made
    public double getMotorRotations() {
        return encoder.getPosition();
    }

    public double getWoofRotations() {
        return getMotorRotations() / WoofSettings.WOOF_GEAR;
    }

    public double getControlPanelRotations() {
        return getWoofRotations() / WoofSettings.CONTROL_PANEL_RATIO;
    }

    public void reset() {
        encoder.setPosition(0);
    }

    // Get the color the color sensor detects
    public Color getColor() {
        return Colors.CYAN_TARGET;
        // return sensor.getRawDetectedColor();
    }

    public ColorSensor getRawColorSensor() {
        // return colorSensor;
        return null;
    }

    @Override
    public void periodic() {
        // Update The Woof Speed
        motor.set(turnFilter.get(turnSpeed));

        // SmartDashboard
        SmartDashboard.putNumber("Woof/Motor Rotations", getMotorRotations());
        SmartDashboard.putNumber("Woof/Woof Rotations", getWoofRotations());
        SmartDashboard.putNumber("Woof/Control Panel Rotations", getControlPanelRotations());
    }
}
