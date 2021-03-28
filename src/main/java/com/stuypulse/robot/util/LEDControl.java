/* Copyright (c) 2021 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.robot.util;

import com.stuypulse.stuylib.input.Gamepad;

import com.stuypulse.robot.RobotContainer;
import com.stuypulse.robot.subsystems.Drivetrain;
import com.stuypulse.robot.subsystems.Intake;
import com.stuypulse.robot.util.LEDController.Color;

public class LEDControl implements Runnable {

    private LEDController controller;
    private Drivetrain drivetrain;
    private Intake intake;
    private Gamepad driver;

    public LEDControl(RobotContainer robot) {
        controller = robot.getLEDController();
        drivetrain = robot.getDrivetrain();
        intake = robot.getIntake();
        driver = robot.getDriver();
    }

    public void controlLEDs() {
        while (true) {
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                System.out.println("oof" + e);
            }

            if (!controller.inPartyMode()) {
                if ((driver.getRawTopButton() || driver.getRawLeftButton())) {
                    controller.setColor(Color.YELLOW_SOLID);
                } else if (drivetrain.getIsAligned()) {
                    controller.setColor(Color.LIME_FLASH);
                    drivetrain.setIsAligned(false);
                } else if (intake.isBallDetected()) {
                    controller.setColor(Color.GREEN_SOLID);
                } else {
                    controller.setColor(Color.OFF);
                    // if (shooter.getShooterMode() == ShooterMode.SHOOT_FROM_INITIATION_LINE) {
                    //     if (shooter.isReady()) {
                    //         controller.setColor(Color.WHITE_SOLID);
                    //     } else {
                    //         controller.setColor(Color.WHITE_PULSE);
                    //     }
                    // } else if (shooter.getShooterMode() == ShooterMode.SHOOT_FROM_TRENCH) {
                    //     if (shooter.isReady()) {
                    //         controller.setColor(Color.PINK_SOLID);
                    //     } else {
                    //         controller.setColor(Color.PINK_PULSE);
                    //     }
                    // } else if (shooter.getShooterMode() == ShooterMode.SHOOT_FROM_FAR) {
                    //     if (shooter.isReady()) {
                    //         controller.setColor(Color.RED_SOLID);
                    //     } else {
                    //         controller.setColor(Color.RED_PULSE);
                    //     }
                    // } else {
                    // }
                }
            }
        }
    }

    @Override
    public void run() {
        controlLEDs();
    }
}
