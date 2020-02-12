package com.stuypulse.robot.util;

import edu.wpi.first.wpilibj.util.Color;

import edu.wpi.first.wpilibj.I2C.Port;

import com.revrobotics.ColorMatch;
import com.revrobotics.ColorSensorV3;
import com.stuypulse.robot.Constants;

public class ColorSensor {

    private ColorSensorV3 colorSensor;
    private ColorMatch colorMatcher;

    public ColorSensor() {
        colorSensor = new ColorSensorV3(Port.kOnboard);
        colorMatcher = new ColorMatch();

        colorMatcher.addColorMatch(Constants.CYAN_TARGET);
        colorMatcher.addColorMatch(Constants.GREEN_TARGET);
        colorMatcher.addColorMatch(Constants.RED_TARGET);
        colorMatcher.addColorMatch(Constants.YELLOW_TARGET);
    }

    public Color getRawDetectedColor() {
        return colorMatcher.matchClosestColor(colorSensor.getColor()).color;
    }

    public String getDetectedColor() {
        Color detectedColor = getRawDetectedColor();

        if (detectedColor == Constants.CYAN_TARGET) {
            return "Cyan";
          } else if (detectedColor == Constants.RED_TARGET) {
            return "Red";
          } else if (detectedColor == Constants.GREEN_TARGET) {
            return "Green";
          } else if (detectedColor == Constants.YELLOW_TARGET) {
            return "Yellow";
          } else {
            return "Unknown";
          }
    }
}