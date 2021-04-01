/* Copyright (c) 2021 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.robot.subsystems;

import com.stuypulse.robot.RobotContainer;

import edu.wpi.first.wpilibj.PWMSparkMax;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LEDController extends SubsystemBase {

    // Enum that represents and calculates values for
    public enum LEDColor {
        RAINBOW(-0.97, false),
        SINELON(-0.77, false),
        CONFETTI(-0.87, false),
        BEAT(-0.67, false),
        TWINKLE(-0.53, false),
        WAVE(-0.43, false),

        WHITE_SOLID(0.93, false), // Shoot from initation line
        PINK_SOLID(0.57, false), // Shoot from trench
        RED_SOLID(0.61, false), // Shoot from far
        ORANGE_SOLID(0.65, false),
        GREEN_SOLID(0.77, false),
        LIME_SOLID(0.73, false),
        BLUE_SOLID(0.87, false),
        PURPLE_SOLID(0.91, false),
        YELLOW_SOLID(0.69, false), // Aligning

        WHITE_PULSE(0.93, true), // Charging shot from initation line
        PINK_PULSE(0.57, true), // Charging shot from trench
        RED_PULSE(0.61, true), // Charging shot from far
        ORANGE_PULSE(0.65, true),
        GREEN_PULSE(0.77, true),
        LIME_PULSE(0.73, true), // Aligned
        BLUE_PULSE(0.87, true), // Ball detected in intake
        PURPLE_PULSE(0.91, true),
        YELLOW_PULSE(0.69, true),

        OFF(0.99, false);

        private final double color;
        private final boolean pulse;

        LEDColor(double color, boolean pulse) {
            this.color = color;
            this.pulse = pulse;
        }

        double get() {
            if (pulse) {
                // Get time in millis, used for pulsing
                long time = System.currentTimeMillis();
                time = Math.abs(time % 500L);

                // Detect if the color should be on or off
                if (time >= 400) {
                    return LEDColor.OFF.color;
                } else {
                    return this.color;
                }
            } else {
                return color;
            }
        }
    }

    // Motor that controlls the LEDs
    private PWMSparkMax controller;

    // The current color to set the LEDs to
    private LEDColor currentColor;

    // The robot container to get information from
    private RobotContainer robotContainer;

    public LEDController(int port, RobotContainer container) {
        this.controller = new PWMSparkMax(port);
        this.currentColor = LEDColor.OFF;

        this.robotContainer = container;
    }

    public LEDController(int port) {
        this(port, null);
    }

    // Set the LED color
    public void setColor(LEDColor color) {
        currentColor = color;
    }

    // Update the LED color depending on what is happening with the robot
    public void updateColors() {
        if (robotContainer != null) {
            Intake intake = robotContainer.getIntake();
            Shooter shooter = robotContainer.getShooter();

            if (intake.isBallDetected()) {
                this.setColor(LEDColor.RAINBOW);
            } else {
                if (shooter.isReady()) {
                    switch (shooter.getMode()) {
                        case GREEN_ZONE:
                            setColor(LEDColor.GREEN_SOLID);
                            break;
                        case YELLOW_ZONE:
                            setColor(LEDColor.YELLOW_SOLID);
                            break;
                        case BLUE_ZONE:
                            setColor(LEDColor.BLUE_SOLID);
                            break;
                        case RED_ZONE:
                            setColor(LEDColor.RED_SOLID);
                            break;
                        default:
                            setColor(LEDColor.OFF);
                    }
                } else {
                    switch (shooter.getMode()) {
                        case GREEN_ZONE:
                            setColor(LEDColor.GREEN_PULSE);
                            break;
                        case YELLOW_ZONE:
                            setColor(LEDColor.YELLOW_PULSE);
                            break;
                        case BLUE_ZONE:
                            setColor(LEDColor.BLUE_PULSE);
                            break;
                        case RED_ZONE:
                            setColor(LEDColor.RED_PULSE);
                            break;
                        default:
                            setColor(LEDColor.OFF);
                    }
                }
            }
        }
    }

    // Update the LEDs
    @Override
    public void periodic() {
        // Get LED info about the robot
        updateColors();

        // Set the controller to the color defined by the ENUM
        this.controller.set(currentColor.get());
    }
}
