package com.stuypulse.robot.subsystems;

import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.revrobotics.ColorMatch;
import com.revrobotics.ColorSensorV3;
import com.stuypulse.robot.Constants.Colors;

public class ColorSensor extends SubsystemBase {

    private ColorSensorV3 colorSensor;
    private ColorMatch colorMatcher;

    public ColorSensor() {
        colorSensor = new ColorSensorV3(Port.kOnboard);
        colorMatcher = new ColorMatch();

        colorMatcher.addColorMatch(Colors.CYAN_TARGET);
        colorMatcher.addColorMatch(Colors.GREEN_TARGET);
        colorMatcher.addColorMatch(Colors.RED_TARGET);
        colorMatcher.addColorMatch(Colors.YELLOW_TARGET);
    }

    public Color getRawDetectedColor() {
        return colorMatcher.matchClosestColor(colorSensor.getColor()).color;
    }

    public String getDetectedColor() {
        Color detectedColor = getRawDetectedColor();

        if (detectedColor == Colors.CYAN_TARGET) {
            return "Cyan";
        } else if (detectedColor == Colors.RED_TARGET) {
            return "Red";
        } else if (detectedColor == Colors.GREEN_TARGET) {
            return "Green";
        } else if (detectedColor == Colors.YELLOW_TARGET) {
            return "Yellow";
        } else {
            return "Unknown";
        }
    }

    @Override
    public void periodic() {
        // SmartDashboard
        SmartDashboard.putNumber("Color Sensor/Raw Detected Color Red", getRawDetectedColor().red);
        SmartDashboard.putNumber("Color Sensor/Raw Detected Color Blue", getRawDetectedColor().blue);
        SmartDashboard.putNumber("Color Sensor/Raw Detected Color Green", getRawDetectedColor().green);

        SmartDashboard.putString("Color Sensor/Detected Color", getDetectedColor());
    }

}