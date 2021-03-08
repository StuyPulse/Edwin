package com.stuypulse.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.stuypulse.robot.Constants.Ports;
import com.stuypulse.robot.Constants.FunnelSettings;

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
        SmartDashboard.putNumber("Funnel/Motor Speed", motor.get());
    }
}
