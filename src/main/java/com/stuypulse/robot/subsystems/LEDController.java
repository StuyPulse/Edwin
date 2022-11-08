/* Copyright (c) 2021 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.robot.subsystems;

import com.stuypulse.stuylib.input.Gamepad;
import com.stuypulse.stuylib.util.StopWatch;

import com.stuypulse.robot.Constants.LEDSettings;
import com.stuypulse.robot.RobotContainer;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LEDController extends SubsystemBase {

    // Enum that represents and calculates values for
    public enum LEDColor {
        RAINBOW(-0.97, false),
        SINELON(-0.77, false),
        CONFETTI(-0.87, false),
        BEAT(-0.67, false),
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
                // // Variables for detecting if we should be blinking or not
                double cT = Timer.getFPGATimestamp() % (LEDSettings.BLINK_TIME);
                double oT = (0.5 * LEDSettings.BLINK_TIME);

                // Detect if the color should be on or off
                if (cT >= oT) {
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

    // Stopwatch to check when to start overriding manual updates
    private StopWatch lastUpdate;

    // The current color to set the LEDs to
    private LEDColor currentColor;

    // The robot container to get information from
    private RobotContainer robotContainer;

    public LEDController(int port, RobotContainer container) {
        this.controller = new PWMSparkMax(port);

        this.lastUpdate = new StopWatch();
        this.currentColor = LEDColor.OFF;

        this.robotContainer = container;
    }

    public LEDController(int port) {
        this(port, null);
    }

    // Other things can update the LEDs without interrupting the loop
    public void setColor(LEDColor color) {
        currentColor = color;
        lastUpdate.reset();
    }

    // Set the LED color
    private void setColorDefault(LEDColor color) {
        if (!DriverStation.isAutonomous()
                && LEDSettings.MANUAL_UPDATE_TIME < lastUpdate.getTime()) {
            currentColor = color;
        }
    }

    // Update the LED color depending on what is happening with the robot
    public void updateColors() {
        if (robotContainer != null) {
            Gamepad driver = robotContainer.getDriver();
            Shooter shooter = robotContainer.getShooter();
            Intake intake = robotContainer.getIntake();

            // Low Gear LEDs
            if (driver.getRawRightButton())
                this.setColorDefault(LEDColor.BLUE_SOLID);

            // Fun Driver LEDs
            /**/ if (driver.getRawDPadUp())
                this.setColorDefault(LEDColor.RAINBOW);
            else if (driver.getRawDPadDown())
                this.setColorDefault(LEDColor.SINELON);
            else if (driver.getRawDPadLeft())
                this.setColorDefault(LEDColor.WAVE);
            else if (driver.getRawDPadRight())
                this.setColorDefault(LEDColor.BEAT);
            else if (driver.getRawLeftBumper())
                this.setColorDefault(LEDColor.RED_PULSE);
            else if (driver.getRawRightBumper())
                this.setColorDefault(LEDColor.BLUE_PULSE);

            // Shooter Modes have their own LEDs
            else if (intake.isBallDetected()) {
                this.setColorDefault(LEDColor.GREEN_SOLID);
            } else {
                if (shooter.isReady()) {
                    switch (shooter.getMode()) {
                        case INITIATION_LINE:
                            setColorDefault(LEDColor.WHITE_SOLID);
                            break;
                        case TRENCH_SHOT:
                            setColorDefault(LEDColor.RED_SOLID);
                            break;
                        case SUPER_TRENCH_SHOT:
                            setColorDefault(LEDColor.PINK_SOLID);
                            break;
                        default:
                            setColorDefault(LEDColor.OFF);
                    }
                } else {
                    switch (shooter.getMode()) {
                        case INITIATION_LINE:
                            setColorDefault(LEDColor.WHITE_PULSE);
                            break;
                        case TRENCH_SHOT:
                            setColorDefault(LEDColor.RED_PULSE);
                            break;
                        case SUPER_TRENCH_SHOT:
                            setColorDefault(LEDColor.PINK_PULSE);
                            break;
                        default:
                            setColorDefault(LEDColor.OFF);
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
