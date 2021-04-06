/* Copyright (c) 2021 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import com.stuypulse.robot.Constants.ChimneySettings;
import com.stuypulse.robot.Constants.Ports;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Chimney extends SubsystemBase {

    // CANSpark Motor and Encoder
    private CANSparkMax motor;

    // IR Sensor
    private DigitalInput lowerSensor;
    private DigitalInput upperSensor;

    public Chimney() {
        motor = new CANSparkMax(Ports.Chimney.LIFT_MOTOR_PORT, MotorType.kBrushless);

        lowerSensor = new DigitalInput(Ports.Chimney.LOWER_SENSOR_PORT);
        upperSensor = new DigitalInput(Ports.Chimney.UPPER_SENSOR_PORT);

        motor.setIdleMode(IdleMode.kCoast);

        // Add Children to Subsystem
        addChild("Lower Sensor", lowerSensor);
        addChild("Upper Sensor", upperSensor);
    }

    // IR SENSOR VALUES
    public boolean getLowerChimneyValue() {
        return lowerSensor.get();
    }

    public boolean getUpperChimneyValue() {
        return !upperSensor.get();
    }

    // MOVE THE MOTORS
    public void liftUp() {
        motor.set(ChimneySettings.LIFT_UP_SPEED);
    }

    public void liftDown() {
        motor.set(-ChimneySettings.LIFT_UP_SPEED);
    }

    public void stop() {
        motor.stopMotor();
    }

    @Override
    public void periodic() {
        // SmartDashboard
        SmartDashboard.putBoolean("Chimney/Upper Chimney Sensor", getUpperChimneyValue());
        SmartDashboard.putBoolean("Chimney/Lower Chimney Sensor", getLowerChimneyValue());
    }
}
