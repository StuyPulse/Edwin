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
            if (driver.getRawTopButton() || driver.getRawLeftButton()) {
                controller.setColor(Color.YELLOW_SOLID);
                System.out.println("ALIGNING - yellow solid");
            } else if(drivetrain.getIsAligned()) {
                controller.setColor(Color.LIME_FLASH);
                drivetrain.setIsAligned(false);
                System.out.println("IS ALIGNED - lime flash");
            } else if(intake.isBallDetected() && controller.getValue() != 0.99) {
                controller.setColor(Color.BLUE_SOLID);
                System.out.println("BALL DETECTED - blue flash");
            } else {
                if (shooter.getShooterMode() == ShooterMode.SHOOT_FROM_INITIATION_LINE) {
                    if (shooter.isAtTargetVelocity()) {
                        controller.setColor(Color.WHITE_SOLID);
                        System.out.println("AT INITIATION LINE SPEED - white solid");
                    } else {
                        controller.setColor(Color.WHITE_PULSE);
                        System.out.println("INITIATION LINE MODE - white pulse");
                    }
                } else if (shooter.getShooterMode() == ShooterMode.SHOOT_FROM_TRENCH) {
                    if (shooter.isAtTargetVelocity()) {
                        controller.setColor(Color.PINK_SOLID);
                        System.out.println("AT TRENCH SPEED - pink solid");
                    } else {
                        controller.setColor(Color.PINK_PULSE);
                        System.out.println("TRENCH MODE - pink pulse");
                    }
                } else if (shooter.getShooterMode() == ShooterMode.SHOOT_FROM_FAR) {
                    if (shooter.isAtTargetVelocity()) {
                        controller.setColor(Color.RED_SOLID);
                        System.out.println("AT FAR SPEED - red solid");
                    } else {
                        controller.setColor(Color.RED_PULSE);
                        System.out.println("FAR MODE - red pulse");
                    }
                } else {
                    controller.setColor(Color.OFF);
                    System.out.println("NOTHING DETECTED - off");
                }
            }
        }
    }

    @Override
    public void run() {
        controlLEDs();
    }
}