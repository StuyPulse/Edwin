package com.stuypulse.robot.util;

import com.stuypulse.robot.subsystems.Chimney;
import com.stuypulse.robot.subsystems.Drivetrain;
import com.stuypulse.robot.subsystems.Shooter;
import com.stuypulse.robot.subsystems.Chimney.ChimneyMode;
import com.stuypulse.robot.subsystems.Shooter.ShooterMode;
import com.stuypulse.stuylib.network.limelight.Limelight;

import edu.wpi.first.wpilibj.PWMSparkMax;
import edu.wpi.first.wpilibj.Timer;

public class LEDController {

    private static PWMSparkMax controller;
    private static Shooter shooter;
    private static Chimney chimney;
    private static Drivetrain drivetrain;

    public LEDController(int port, Shooter shooter, Chimney chimney, Drivetrain drivetrain) {
        controller = new PWMSparkMax(port);
        this.shooter = shooter;
        this.chimney = chimney;
        this.drivetrain = drivetrain;
    }

    public static void setValue(double value) {
        controller.set(value);
    }

    public static double getValue() {
        return controller.get();
    }

    enum Color {
        WHITE_SOLID,  // Shoot from initation line
        PINK_SOLID,   // Shoot from trench
        RED_SOLID,    // Shoot from far
        WHITE_PULSE,  // Charging shot from initation line
        PINK_PULSE,   // Charging shot from trench
        RED_PULSE,    // Charging shot from far
        YELLOW_SOLID, // Aligning
        LIME_FLASH,   // Aligned
        BLUE_FLASH,   // Ball detected in chute
        OFF
    }

    // Switches the color on the thread you call it from
    private static void setColorOnThread(Color color) {
        switch (color) {
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
                if (getValue() != 0.99) {
                    setValue(0.87);
                    Timer.delay(0.5);
                    setValue(0.99);
                } 
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
        } else if(drivetrain.getIsAligned()) {
            setColor(Color.LIME_FLASH);
            drivetrain.setIsAligned(false);
        } else if(chimney.getChimneyMode() == ChimneyMode.ACQUIRED_ONE_CELL) {
            setColor(Color.BLUE_FLASH);
        } else {
            if (!shooter.isAtTargetVelocity() && shooter.getShooterMode() == ShooterMode.SHOOT_FROM_INITIATION_LINE) {
                setColor(Color.WHITE_PULSE);    
            } else if(!shooter.isAtTargetVelocity() && shooter.getShooterMode() == ShooterMode.SHOOT_FROM_TRENCH) {
                setColor(Color.PINK_PULSE);
            } else if(!shooter.isAtTargetVelocity() && shooter.getShooterMode() == ShooterMode.SHOOT_FROM_FAR) {
                setColor(Color.RED_PULSE);
            } else {
                if (shooter.getShooterMode() == ShooterMode.SHOOT_FROM_INITIATION_LINE) {
                    setColor(Color.WHITE_SOLID);
                } else if(shooter.getShooterMode() == ShooterMode.SHOOT_FROM_TRENCH) {
                    setColor(Color.PINK_SOLID);
                } else if(shooter.getShooterMode() == ShooterMode.SHOOT_FROM_FAR) {
                    setColor(Color.RED_SOLID);
                } else {
                    setColor(Color.OFF);
                }
            }
        }
    }

}
