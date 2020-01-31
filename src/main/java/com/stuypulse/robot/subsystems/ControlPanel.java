package com.stuypulse.robot.subsystems;

import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.stuypulse.robot.Constants;
import com.stuypulse.robot.util.ColorSensor;

public class ControlPanel extends SubsystemBase {
    private CANSparkMax motor;
    private ColorSensor cs;

    public ControlPanel() {
        
        cs = new ColorSensor();
        motor = new CANSparkMax(Constants.CONTROL_PANEL_MOTOR_PORT, MotorType.kBrushless);
    
    }

    public void turn(double speed) {
        motor.set(speed);
    }

    public Color getColor() {
       return cs.getRawDetectedColor();
    }
}

