package com.stuypulse.robot.util;

import edu.wpi.first.wpilibj.util.Color;

import com.revrobotics.*;
import edu.wpi.first.wpilibj.I2C.Port;
import com.stuypulse.robot.Constants;

public class ColorSensor {

    private ColorSensorV3 m_colorSensor;
    private ColorMatch m_colorMatcher;

    private static final Color kCyanTarget = ColorMatch.makeColor(Constants.CYAN_RED, Constants.CYAN_GREEN, Constants.CYAN_BLUE);
    private static final Color kGreenTarget = ColorMatch.makeColor(Constants.GREEN_RED, Constants.GREEN_GREEN, Constants.GREEN_BLUE);
    private static final Color kRedTarget = ColorMatch.makeColor(Constants.RED_RED, Constants.RED_GREEN, Constants.RED_BLUE);
    private static final Color kYellowTarget = ColorMatch.makeColor(Constants.YELLOW_RED, Constants.YELLOW_GREEN, Constants.YELLOW_BLUE);

    public ColorSensor() {
        m_colorSensor = new ColorSensorV3(Port.kOnboard);
        m_colorMatcher = new ColorMatch();

        m_colorMatcher.addColorMatch(kCyanTarget);
        m_colorMatcher.addColorMatch(kGreenTarget);
        m_colorMatcher.addColorMatch(kRedTarget);
        m_colorMatcher.addColorMatch(kYellowTarget);
    }

    public Color getRawDetectedColor() {
        return m_colorMatcher.matchClosestColor(m_colorSensor.getColor()).color;
    }

    public String getDetectedColor() {
        Color detectedColor = getRawDetectedColor();

        if (detectedColor == kCyanTarget) {
            return "Cyan";
          } else if (detectedColor == kRedTarget) {
            return "Red";
          } else if (detectedColor == kGreenTarget) {
            return "Green";
          } else if (detectedColor == kYellowTarget) {
            return "Yellow";
          } else {
            return "Unknown";
          }
    }
}