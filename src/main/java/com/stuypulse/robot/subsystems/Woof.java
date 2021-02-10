package com.stuypulse.robot.subsystems;

import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.stuypulse.robot.Constants;

public class Woof extends SubsystemBase {
    private CANSparkMax motor;
    private CANEncoder encoder;

    public Woof() {
        motor = new CANSparkMax(Constants.WOOF_MOTOR_PORT, MotorType.kBrushless);
        encoder = new CANEncoder(motor);
    }

    public void turn(double speed) {
        motor.set(speed);
    }

    public double getEncoderValue() {
        return encoder.getPosition();
    }

    public void resetEncoderValue() {
        encoder.setPosition(0);
    }

    public Color getColor() {
        return Constants.CYAN_TARGET;
        // return sensor.getRawDetectedColor();
    }

    public void stop() {
        motor.stopMotor();
    }
}
