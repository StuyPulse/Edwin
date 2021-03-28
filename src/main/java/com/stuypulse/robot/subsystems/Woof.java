/* Copyright (c) 2021 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import com.stuypulse.robot.Constants.Colors;
import com.stuypulse.robot.Constants.Ports;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Woof extends SubsystemBase {
    // Motor and Encoder
    private CANSparkMax motor;
    private CANEncoder encoder;
    // private ColorSensor colorSensor;

    public Woof() {
        motor = new CANSparkMax(Ports.Woof.MOTOR_PORT, MotorType.kBrushless);
        encoder = motor.getEncoder();
        // colorSensor = new ColorSensor();

        // Add Children to Subsystem
        //  addChild("Color Sensor", colorSensor);
    }

    // Controlling the motor
    public void turn(double speed) {
        motor.set(speed);
    }

    public void stop() {
        motor.stopMotor();
    }

    // Get number of rotations the motor has made
    public double getRotations() {
        return encoder.getPosition();
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
        // SmartDashboard
        SmartDashboard.putNumber("Woof/Woof Rotations", getRotations());
    }
}
