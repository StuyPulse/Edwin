package com.stuypulse.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.stuypulse.robot.Constants;

public class Funnel extends SubsystemBase {

    private CANSparkMax motor;

    public Funnel() {
        motor = new CANSparkMax(Constants.FUNNEL_MOTOR_PORT, MotorType.kBrushless);
    }

    public void funnel() {
        motor.set(Constants.FUNNEL_SPEED);
    }

    public void unfunnel() {
        motor.set(Constants.UNFUNNEL_SPEED);
    }

}