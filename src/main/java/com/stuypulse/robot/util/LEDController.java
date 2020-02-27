package com.stuypulse.robot.util;

import edu.wpi.first.wpilibj.PWMSparkMax;
import edu.wpi.first.wpilibj.Timer;

public class LEDController {

    private static PWMSparkMax controller;
    private boolean partyMode;

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
        RAINBOW,
        SINELON,
        CONFETTI,
        BEAT,
        TWINKLE,
        WAVE,

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
            case RAINBOW:
                setValue(-0.97);
                break;
            case SINELON:
                setValue(-0.77);
                break;
            case CONFETTI:
                setValue(-0.87);
                break;
            case BEAT:
                setValue(-0.67);
                break;
            case TWINKLE:
                setValue(-0.53);
                break;
            case WAVE:
                setValue(-0.43);
                break;

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

    public void togglePartyMode() {
        partyMode = !partyMode;
    }

    public boolean inPartyMode() {
        return partyMode;
    }

    public void startParty() {
        int rand = (int)(Math.random() * 6);
        switch (rand) {
            case 0:
                setColor(Color.RAINBOW);
                break;
            case 1:
                setColor(Color.SINELON);
                break;
            case 2:
                setColor(Color.CONFETTI);
                break;
            case 3:
                setColor(Color.BEAT);
                break;
            case 4:
                setColor(Color.TWINKLE);
                break;
            case 5:
                setColor(Color.WAVE);
                break;
            default:
                setColor(Color.OFF);
                break;
        }
    }
}
