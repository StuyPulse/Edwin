/* Copyright (c) 2021 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.robot.subsystems;

import com.revrobotics.ColorMatch;
import com.revrobotics.ColorSensorV3;

import com.stuypulse.robot.Constants;
import com.stuypulse.robot.Constants.Colors;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ColorSensor extends SubsystemBase {

    // Woof Color enum
    public static enum WColor {
        // Create all colors on the control panel
        YELLOW,
        CYAN,
        GREEN,
        RED,

        // To avoid null pointer exceptions when returning error states
        NONE;

        // Each color will store information about its position
        private WColor next;

        // Use a static block to setup circular references
        static {
            /*
            Control Panel Reference:
            https://firstfrc.blob.core.windows.net/frc2021/Manual/Sections/2021FRCGameManualSection03.pdf
            */

            // Setup each color's next reference
            YELLOW.next = CYAN;
            CYAN.next = GREEN;
            GREEN.next = RED;
            RED.next = YELLOW;
            NONE.next = NONE;
        }

        // Getters
        public WColor getNextColor() {
            return this.next;
        }

        public WColor getRotatedColor() {
            return this.next.next;
        }
    };

    private ColorSensorV3 colorSensor;
    private ColorMatch colorMatcher;

    public ColorSensor() {
        colorSensor = new ColorSensorV3(Port.kOnboard);
        colorMatcher = new ColorMatch();

        colorMatcher.addColorMatch(Colors.YELLOW_TARGET);
        colorMatcher.addColorMatch(Colors.CYAN_TARGET);
        colorMatcher.addColorMatch(Colors.GREEN_TARGET);
        colorMatcher.addColorMatch(Colors.RED_TARGET);
    }

    /** @return reports the color that should be under the GAME sensor */
    public WColor getFMSColor() {
        String gameData = DriverStation.getInstance().getGameSpecificMessage();

        if (gameData == null || gameData.length() <= 0) return WColor.NONE;

        switch (gameData.charAt(0)) {
            case 'Y':
                return WColor.YELLOW;
            case 'B':
                return WColor.CYAN;
            case 'G':
                return WColor.GREEN;
            case 'R':
                return WColor.RED;
            default:
                return WColor.NONE;
        }
    }

    /** @return reports the color that should be under OUR sensor (based on the FMS color) */
    public WColor getTargetColor() {
        return getFMSColor().getRotatedColor();
    }

    /** @return gets the color reported by OUR sensor */
    public Color getRawColor() {
        return colorSensor.getColor();
    }

    /** @return gets the color reported by OUR sensor matched to the list of valid colors */
    public Color getRawDetectedColor() {
        return colorMatcher.matchClosestColor(getRawColor()).color;
    }

    /** @return gets the color reported by OUR sensor */
    public WColor getDetectedColor() {
        final Color color = getRawDetectedColor();

        if (color == Colors.YELLOW_TARGET) return WColor.YELLOW;
        if (color == Colors.CYAN_TARGET) return WColor.CYAN;
        if (color == Colors.GREEN_TARGET) return WColor.GREEN;
        if (color == Colors.RED_TARGET) return WColor.RED;

        return WColor.NONE;
    }

    @Override
    public void periodic() {
        // SmartDashboard

        if(Constants.DEBUG_MODE.get()) {

            // This Causes Loop Overrun
            // SmartDashboard.putNumber("Color Sensor/Raw Color Red", getRawColor().red);
            // SmartDashboard.putNumber("Color Sensor/Raw Color Blue", getRawColor().blue);
            // SmartDashboard.putNumber("Color Sensor/Raw Color Green", getRawColor().green);
    
            // SmartDashboard.putNumber("Color Sensor/Raw Detected Color Red", getRawDetectedColor().red);
            // SmartDashboard.putNumber(
            //         "Color Sensor/Raw Detected Color Blue", getRawDetectedColor().blue);
            // SmartDashboard.putNumber(
            //         "Color Sensor/Raw Detected Color Green", getRawDetectedColor().green);
    
            SmartDashboard.putString("Color Sensor/FMS Color", getFMSColor().name());
            SmartDashboard.putString("Color Sensor/Rotated FMS Color", getTargetColor().name());

            SmartDashboard.putString("Color Sensor/Detected Color", getDetectedColor().name());
        }
    }
}
