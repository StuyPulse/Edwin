package com.stuypulse.robot.util;

import com.stuypulse.robot.subsystems.Chimney;
import com.stuypulse.robot.subsystems.Shooter;
import com.stuypulse.robot.subsystems.Shooter.Mode;
import com.stuypulse.stuylib.network.limelight.Limelight;

import edu.wpi.first.wpilibj.PWMSparkMax;
import edu.wpi.first.wpilibj.Timer;

public class LEDController {

    private static PWMSparkMax controller;
    private static Shooter shooter;
    private static Chimney chimney;

    private static boolean isAligned;

    public LEDController(int port, Shooter shooter, Chimney chimney) {
        controller = new PWMSparkMax(port);
        this.shooter = shooter;
        this.chimney = chimney;
    }

    public static void setValue(double value) {
        controller.set(value);
    }

    enum Color {
        ORANGE_SOLID, 
        PINK_SOLID, 
        WHITE_SOLID,
        LIME_SOLID, 
        YELLOW_SOLID,
        RED_SOLID, 
        LIME_FLASH,
        BLUE_FLASH,
        WHITE_SHOT,
        SINE_WAVE,
        OFF
    }

    // Switches the color on the thread you call it from
    private static void setColorOnThread(Color color) {
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
            case LIME_SOLID:
                setValue(0.73);
                break;
            case YELLOW_SOLID:
                setValue(0.69);
                break;
            case RED_SOLID:
                setValue(0.61);
                break;
            case LIME_FLASH:
                setValue(0.73);
                Timer.delay(0.5);
                break;
            case BLUE_FLASH:
                setValue(0.87);
                Timer.delay(0.5);
                setValue(0.99);
                break;
            case WHITE_SHOT:
                setValue(0.13); 
                break;
            case SINE_WAVE:
                setValue(0.55);
                break;
            case OFF:
                setValue(0.99);
                break;
        }
    }

    // Set color on new thread
    public static void setColor(Color color) {
        new Thread(() -> setColorOnThread(color)).start();
    }

    public static void controlLEDs() {
        if (Limelight.hasValidTarget()) {
            setColor(Color.YELLOW_SOLID);
            isAligned = true;
        } else if(isAligned) {
            setColor(Color.LIME_FLASH);
            isAligned = false;
        } else if (chimney.getLowerChimneyValue()) {
            setColor(Color.BLUE_FLASH);
        } else {
            if (!shooter.isAtTargetVelocity()) {
                setColor(Color.SINE_WAVE);    
            } else if(shooter.getShooterMode() == Mode.SHOOT_FROM_FAR) {
                setColor(Color.RED_SOLID);
            } else if(shooter.getShooterMode() == Mode.SHOOT_FROM_INITIATION_LINE) {
                setColor(Color.WHITE_SOLID);
            } else if(shooter.getShooterMode() == Mode.SHOOT_FROM_TRENCH) {
                setColor(Color.PINK_SOLID);
            } else {
                setColor(Color.OFF);
            }
        }
    }

}
