package com.stuypulse.robot.subsystems;

import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.stuypulse.robot.Constants;
import com.stuypulse.robot.util.BrownoutProtection;
import com.stuypulse.robot.util.ColorSensor;

public class ControlPanel extends SubsystemBase implements BrownoutProtection {
    private CANSparkMax motor;
    private ColorSensor sensor;

    public ControlPanel() {
        
        sensor = new ColorSensor();
        motor = new CANSparkMax(Constants.CONTROL_PANEL_MOTOR_PORT, MotorType.kBrushless);
    
    }

    public void turn(double speed) {
        motor.set(speed);
    }

    public Color getColor() {
       return sensor.getRawDetectedColor();
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

