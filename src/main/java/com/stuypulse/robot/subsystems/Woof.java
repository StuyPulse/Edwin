package com.stuypulse.robot.subsystems;

import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.stuypulse.robot.Constants;
import com.stuypulse.robot.util.BrownoutProtection;
import com.stuypulse.robot.util.ColorSensor;

public class Woof extends SubsystemBase implements BrownoutProtection{
    private CANSparkMax motor;
    private CANEncoder encoder;
    // private ColorSensor sensor;

    public Woof() {
        
        // sensor = new ColorSensor();
        motor = new CANSparkMax(Constants.WOOF_MOTOR_PORT, MotorType.kBrushless);
        encoder = new CANEncoder(motor);
        // encoder.setPositionConversionFactor(0.05);
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

    @Override
    public void enableBrownout() {
        motor.setSmartCurrentLimit(Constants.CURRENT_LIMIT); 
    }

    @Override
    public void disableBrownout() {
        motor.setSmartCurrentLimit(0);
    }

}
