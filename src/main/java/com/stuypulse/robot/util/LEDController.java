package com.stuypulse.robot.util;

import com.stuypulse.robot.subsystems.Shooter;

import edu.wpi.first.wpilibj.PWMSparkMax;
import edu.wpi.first.wpilibj.Timer;

public class LEDController {

    private static PWMSparkMax controller;
    private static Shooter shooter;

    public LEDController(int port, Shooter shooter) {
        controller = new PWMSparkMax(port);
        this.shooter = shooter;
    }

    public static void setValue(double value) {
        controller.set(value);
    }

    enum Color {
        ORANGE_SOLID, 
        PINK_SOLID, 
        WHITE_SOLID, 
        ORANGE_PULSE, 
        PINK_PULSE, 
        WHITE_PULSE, 
        GREEN_SOLID, 
        YELLOW_SOLID, 
        RED_SOLID, 
        WHITE_CHASE_CHUTE
    }

    public static void setColor(Color color) {
        switch (color) {
            case ORANGE_SOLID:
                setValue(0.65);
                break;
            case PINK_SOLID:
                setValue(0.57);
                break;
            case WHITE_SOLID:
                setValue(0.93);
                break;
            case ORANGE_PULSE:
                setValue(0.65);
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
            case WHITE_PULSE:
                setValue(0.93);
                Timer.delay(0.5);
                setValue(0.99);
                Timer.delay(0.1);
                break;
            case GREEN_SOLID:
                setValue(0.77);
                break;
            case YELLOW_SOLID:
                setValue(0.69);
                break;
            case RED_SOLID:
                setValue(0.61);
                break;
            case WHITE_CHASE_CHUTE:
                setValue(0.01); //TODO: set speed and brightness on blinkin LED
                break;
        }
    }

    public static void controlLEDs() {
        
    }
}
