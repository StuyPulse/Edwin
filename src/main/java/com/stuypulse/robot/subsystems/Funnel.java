/* Copyright (c) 2021 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import com.stuypulse.robot.Constants;
import com.stuypulse.robot.Constants.FunnelSettings;
import com.stuypulse.robot.Constants.Ports;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Funnel extends SubsystemBase {

    private final CANSparkMax motor;

    public Funnel() {
        motor = new CANSparkMax(Ports.FUNNEL, MotorType.kBrushless);
    }

    public void funnel() {
        motor.set(FunnelSettings.FUNNEL_SPEED);
    }

    public void unfunnel() {
        motor.set(FunnelSettings.UNFUNNEL_SPEED);
    }

    public void stop() {
        motor.stopMotor();
    }

    @Override
    public void periodic() {
        // SmartDashboard
        
        if(Constants.DEBUG_MODE.get()) {
            SmartDashboard.putNumber("Funnel/Motor Speed", motor.get());
        }
    }
}
