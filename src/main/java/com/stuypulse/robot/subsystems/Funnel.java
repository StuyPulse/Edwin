package com.stuypulse.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
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

    /************************
     * SENDABLE INFORMATION *
     ************************/

    @Override
    public void initSendable(SendableBuilder builder) {
        super.initSendable(builder);

        builder.addDoubleProperty(
            "Motor Speed", 
            motor::get, 
            motor::set);
    }

}
