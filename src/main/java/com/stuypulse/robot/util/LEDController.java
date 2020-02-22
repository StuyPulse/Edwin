package com.stuypulse.robot.util;

import edu.wpi.first.wpilibj.PWMSparkMax;
import edu.wpi.first.wpilibj.Timer;

public class LEDController {

    private static PWMSparkMax controller;

    public LEDController(int port) {
        controller = new PWMSparkMax(port);
    }

    public void setValue(double value) {
        controller.set(value);
    }

    public double getValue() {
        return controller.get();
    }

    public enum Color {
        ORANGE_SOLID, 
        GREEN_SOLID,
        BLUE_SOLID,
        PURPLE_SOLID,

        WHITE_SOLID,  // Shoot from initation line
        PINK_SOLID,   // Shoot from trench
        RED_SOLID,    // Shoot from far
        WHITE_PULSE,  // Charging shot from initation line
        PINK_PULSE,   // Charging shot from trench
        RED_PULSE,    // Charging shot from far
        YELLOW_SOLID, // Aligning
        LIME_FLASH,   // Aligned
        BLUE_FLASH,   // Ball detected in intake
        OFF
    }

    public void setColor(Color color) {
        switch (color) {
            case ORANGE_SOLID:
                setValue(0.65);
                break;
            case GREEN_SOLID:
                setValue(0.77);
                break;
            case BLUE_SOLID:
                setValue(0.87);
                break;
            case PURPLE_SOLID:
                setValue(0.91);
                break;

            case WHITE_SOLID:
                setValue(0.93);
                break;
            case PINK_SOLID:
                setValue(0.57);
                break;
            case RED_SOLID:
                setValue(0.61);
                break;
            case WHITE_PULSE:
                setValue(0.93);
                Timer.delay(0.5);
                setValue(0.99);
                Timer.delay(0.1);
                break;
            case PINK_PULSE:
                setValue(0.57);
                Timer.delay(0.5);
                setValue(0.99);
                Timer.delay(0.1);
                break;
            case RED_PULSE:
                setValue(0.61);
                Timer.delay(0.5);
                setValue(0.99);
                Timer.delay(0.1);
            case YELLOW_SOLID:
                setValue(0.69);
                break;
            case LIME_FLASH:
                if (getValue() != 0.99) {
                    setValue(0.73);
                    Timer.delay(0.5);
                    setValue(0.99); 
                } 
                break;
            case BLUE_FLASH:
                setValue(0.87);
                Timer.delay(0.5);
                setValue(0.99);
                break;
            case OFF:
                setValue(0.99);
                break;
        }
    }
}
