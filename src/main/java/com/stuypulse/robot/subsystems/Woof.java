package com.stuypulse.robot.subsystems;

import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.stuypulse.robot.Constants;
import com.stuypulse.robot.util.ColorSensor;

public class Woof extends SubsystemBase {
    private CANSparkMax motor;
    // private ColorSensor sensor;

    public Woof() {
        
        // sensor = new ColorSensor();
        motor = new CANSparkMax(Constants.WOOF_MOTOR_PORT, MotorType.kBrushless);
    
    }

    public void turn(double speed) {
        motor.set(speed);
    }

    public Color getColor() {
        return Constants.CYAN_TARGET;
        // return sensor.getRawDetectedColor();
    }

    public void stop() {
        motor.stopMotor();
    }
}
