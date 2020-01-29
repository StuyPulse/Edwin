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

    public void turnToColor(Color color) {
        while (color != getColor()) {
            turnClockwise(Constants.CONTROL_PANEL_TURN_SPEED);
        }
        motor.set(0);
    }

    public void turnNumberOfTimes(int times) {
        int colorCount = 0;
        int goal = times * 8;
        Color prevColor = getColor();
        while (colorCount < goal) {
            turnClockwise(Constants.CONTROL_PANEL_TURN_SPEED);
            Color currColor = getColor();
            if (currColor != prevColor) {
                colorCount += 1;
            }
        }
    }

    public void turnClockwise(double speed) {
        motor.set(speed);
    }

    public void turnCounterClockwise(double speed) {
        turnClockwise(-speed);
    }

    public Color getColor() {
       return cs.getRawDetectedColor();
    }
}