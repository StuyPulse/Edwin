package com.stuypulse.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.stuypulse.robot.Constants;
import com.stuypulse.robot.MotorStall;

public class Funnel extends SubsystemBase implements MotorStall {

    private CANSparkMax motor;
    private CANEncoder encoder;

    public Funnel() {
        motor = new CANSparkMax(Constants.FUNNEL_MOTOR_PORT, MotorType.kBrushless);
        encoder = motor.getEncoder();
    }

    public void funnel() {
        motor.set(Constants.FUNNEL_SPEED);
    }

    public void unfunnel() {
        motor.set(Constants.UNFUNNEL_SPEED);
    }

    public void stop() {
        motor.set(0);
    }

    public double getEncoderVal() {
        return encoder.getPosition();
    }

    @Override
    public void enableStalling() {
        // TODO insert gamepad vibration here;
    }

    @Override
    public boolean isStalling() {
        return false;
    }

    @Override
    public double getEncoderApproachStallThreshold() {
        return 3.0;
    }

}