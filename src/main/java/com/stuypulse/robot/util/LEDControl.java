package com.stuypulse.robot.util;

import com.stuypulse.robot.RobotContainer;
import com.stuypulse.robot.subsystems.Drivetrain;
import com.stuypulse.robot.subsystems.Intake;
import com.stuypulse.robot.subsystems.Shooter;
import com.stuypulse.robot.subsystems.Shooter.ShooterMode;
import com.stuypulse.robot.util.LEDController.Color;
import com.stuypulse.stuylib.input.Gamepad;

public class LEDControl implements Runnable {

    private LEDController controller;
    private Drivetrain drivetrain;
    private Shooter shooter;
    private Intake intake;
    private Gamepad driver;

    public LEDControl(RobotContainer robot) {
        controller = robot.getLEDController();
        drivetrain = robot.getDrivetrain();
        shooter = robot.getShooter();
        intake = robot.getIntake();
        driver = robot.getDriver();
    }

    public void controlLEDs() {
        while (true) {
            try {
                Thread.sleep(250);
            } catch(InterruptedException e) {
                System.out.println("oof" + e);
            }
            
            if (!controller.inPartyMode()) {
                if ((driver.getRawTopButton() || driver.getRawLeftButton())) {
                    controller.setColor(Color.YELLOW_SOLID);
                } else if(drivetrain.getIsAligned()) {
                    controller.setColor(Color.LIME_FLASH);
                    drivetrain.setIsAligned(false);
                } else if(intake.isBallDetected()) {
                    controller.setColor(Color.GREEN_SOLID);
                } else {
                    if (shooter.getShooterMode() == ShooterMode.SHOOT_FROM_INITIATION_LINE) {
                        if (shooter.isReady()) {
                            controller.setColor(Color.WHITE_SOLID);
                        } else {
                            controller.setColor(Color.WHITE_PULSE);
                        }
                    } else if (shooter.getShooterMode() == ShooterMode.SHOOT_FROM_TRENCH) {
                        if (shooter.isReady()) {
                            controller.setColor(Color.PINK_SOLID);
                        } else {
                            controller.setColor(Color.PINK_PULSE);
                        }
                    } else if (shooter.getShooterMode() == ShooterMode.SHOOT_FROM_FAR) {
                        if (shooter.isReady()) {
                            controller.setColor(Color.RED_SOLID);
                        } else {
                            controller.setColor(Color.RED_PULSE);
                        }
                    } else {
                        controller.setColor(Color.OFF);
                    }
                }
            }
        }
    }

    @Override
    public void run() {
        controlLEDs();
    }
}
