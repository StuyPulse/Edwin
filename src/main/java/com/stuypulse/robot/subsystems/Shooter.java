/* Copyright (c) 2021 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.robot.subsystems;

import com.stuypulse.stuylib.control.PIDCalculator;
import com.stuypulse.stuylib.control.PIDController;
import com.stuypulse.stuylib.math.SLMath;
import com.stuypulse.stuylib.network.SmartBoolean;
import com.stuypulse.stuylib.network.SmartNumber;
import com.stuypulse.stuylib.streams.filters.IFilter;
import com.stuypulse.stuylib.streams.filters.TimedRateLimit;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import com.stuypulse.robot.Constants;
import com.stuypulse.robot.Constants.Ports;
import com.stuypulse.robot.Constants.ShooterSettings;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase {
    public enum ShooterMode {
        DISABLED(
                new SmartNumber("Shooting/DISABLED/Distance", -1.0),
                new SmartNumber("Shooting/DISABLED/RPM", 0),
                new SmartBoolean("Shooting/DISABLED/Hood Extended", false)),

        INITIATION_LINE(
                new SmartNumber("Shooting/Green Zone/Distance", 2.24),
                new SmartNumber("Shooting/Green Zone/RPM", 2075),
                new SmartBoolean("Shooting/Green Zone/Hood Extended", true)),

        TRENCH_SHOT(
                new SmartNumber("Shooting/Yellow Zone/Distance", 5.5),
                new SmartNumber("Shooting/Yellow Zone/RPM", 3000),
                new SmartBoolean("Shooting/Yellow Zone/Hood Extended", false)),

        SUPER_TRENCH_SHOT(
                new SmartNumber("Shooting/Pink Zone/Distance", 6.0),
                new SmartNumber("Shooting/Pink Zone/RPM", 3600),
                new SmartBoolean("Shooting/Pink Zone/Hood Extended", false));

        public final SmartNumber distance;
        public final SmartNumber rpm;
        public final SmartBoolean extendHood;

        ShooterMode(SmartNumber distance, SmartNumber rpm, SmartBoolean extendHood) {
            this.distance = distance;
            this.rpm = rpm;
            this.extendHood = extendHood;
        }
    }

    public static final IFilter INTEGRAL_FILTER =
            (x) -> SLMath.clamp(x, ShooterSettings.I_LIMIT.doubleValue());

    // Motors
    private final CANSparkMax shooterMotor;
    private final CANSparkMax shooterFollowerA;
    private final CANSparkMax shooterFollowerB;
    private final CANSparkMax feederMotor;

    // Encoders
    private final CANEncoder shooterEncoderA;
    private final CANEncoder shooterEncoderB;
    private final CANEncoder shooterEncoderC;
    private final CANEncoder feederEncoder;

    // Hood Solenoid
    private final Solenoid hoodSolenoid;

    // Target RPM
    private IFilter targetRPM;
    private ShooterMode currentMode;

    // PID Controllers for the shooter and feeder
    private PIDController shooterController;
    private PIDController feederController;

    private PIDCalculator shooterCalculator;
    private PIDCalculator feederCalculator;

    public Shooter() {
        // Shooter Stuff
        shooterMotor = new CANSparkMax(Ports.Shooter.MIDDLE, MotorType.kBrushless);
        shooterFollowerA = new CANSparkMax(Ports.Shooter.LEFT, MotorType.kBrushless);
        shooterFollowerB = new CANSparkMax(Ports.Shooter.RIGHT, MotorType.kBrushless);

        shooterFollowerA.follow(shooterMotor, true);
        shooterFollowerB.follow(shooterMotor, false);

        shooterEncoderA = shooterMotor.getEncoder();
        shooterEncoderB = shooterFollowerA.getEncoder();
        shooterEncoderC = shooterFollowerB.getEncoder();

        // Feeder Stuff
        feederMotor = new CANSparkMax(Ports.Shooter.FEEDER, MotorType.kBrushless);

        feederMotor.setInverted(true);
        feederEncoder = feederMotor.getEncoder();

        // PID Stuff
        shooterController =
                new PIDController(
                                ShooterSettings.Shooter.P,
                                ShooterSettings.Shooter.I,
                                ShooterSettings.Shooter.D)
                        .setIntegratorFilter(
                                (i) -> {
                                    if (shooterController.isDone(
                                            ShooterSettings.I_RANGE.doubleValue()))
                                        return SLMath.clamp(
                                                i, ShooterSettings.I_LIMIT.doubleValue());
                                    else return 0;
                                });

        feederController =
                new PIDController(
                                ShooterSettings.Feeder.P,
                                ShooterSettings.Feeder.I,
                                ShooterSettings.Feeder.D)
                        .setIntegratorFilter(
                                (i) -> {
                                    if (feederController.isDone(
                                            ShooterSettings.I_RANGE.doubleValue()))
                                        return SLMath.clamp(
                                                i, ShooterSettings.I_LIMIT.doubleValue());
                                    else return 0;
                                });

        shooterCalculator = new PIDCalculator(ShooterSettings.Shooter.BANGBANG_SPEED);
        feederCalculator = new PIDCalculator(ShooterSettings.Shooter.BANGBANG_SPEED);

        // Hood Stuff
        hoodSolenoid = new Solenoid(Ports.Shooter.HOOD_SOLENOID);

        // Setting Modes Stuff
        shooterMotor.setIdleMode(IdleMode.kCoast);
        shooterFollowerA.setIdleMode(IdleMode.kCoast);
        shooterFollowerB.setIdleMode(IdleMode.kCoast);

        shooterMotor.setSmartCurrentLimit(ShooterSettings.CURRENT_LIMIT);
        shooterFollowerA.setSmartCurrentLimit(ShooterSettings.CURRENT_LIMIT);
        shooterFollowerB.setSmartCurrentLimit(ShooterSettings.CURRENT_LIMIT);

        feederMotor.setIdleMode(IdleMode.kCoast);
        feederMotor.setSmartCurrentLimit(ShooterSettings.CURRENT_LIMIT);

        // Set Current Shooter Mode to Disabled
        targetRPM = new TimedRateLimit(800);
        currentMode = ShooterMode.DISABLED;

        // Add Children to Subsystem
        addChild("Hood Solenoid", hoodSolenoid);
    }

    /************
     * SHOOTING *
     ************/

    public double getRawTargetRPM() {
        return getMode().rpm.get();
    }

    public double getTargetRPM() {
        return targetRPM.get(getRawTargetRPM());
    }

    public double getShooterRPM() {
        return (Math.abs(shooterEncoderA.getVelocity())
                        + Math.abs(shooterEncoderB.getVelocity())
                        + Math.abs(shooterEncoderC.getVelocity()))
                / 3.0;
    }

    public double getFeederRPM() {
        return Math.abs(feederEncoder.getVelocity());
    }

    public void setMode(ShooterMode mode) {
        this.currentMode = mode;
    }

    public ShooterMode getMode() {
        return this.currentMode;
    }

    public boolean isReady() {
        return shooterController.isDone(ShooterSettings.TOLERANCE)
                && feederController.isDone(ShooterSettings.TOLERANCE);
    }

    @Override
    public void periodic() {
        // Set the hood according to the mode
        if (getMode().extendHood.get()) {
            this.extendHoodSolenoid();
        } else {
            this.retractHoodSolenoid();
        }

        // If the shooter is in disabled mode, then just coast
        if (getMode().equals(ShooterMode.DISABLED)
                || getRawTargetRPM() < ShooterSettings.TOLERANCE) {
            shooterMotor.stopMotor();
            feederMotor.stopMotor();
        }

        // Otherwise control the shooter using a control algorithm
        else {
            // Feed forward
            double shootSpeed = getTargetRPM() * ShooterSettings.Shooter.FF.get();
            double feederSpeed = getTargetRPM() * ShooterSettings.Feeder.FF.get();

            // Automatically tune the PID controllers
            if (ShooterSettings.AUTOTUNE.get()) {
                shootSpeed += shooterCalculator.update(getTargetRPM(), getShooterRPM());
                feederSpeed += feederCalculator.update(getTargetRPM(), getFeederRPM());

                shooterController.setPID(shooterCalculator.getPIDController());
                feederController.setPID(feederCalculator.getPIDController());
            }

            // Just use normal PID control
            else {
                shootSpeed += shooterController.update(getTargetRPM(), getShooterRPM());
                feederSpeed += feederController.update(getTargetRPM(), getFeederRPM());
            }

            // Set the speeds of the motors, and prevent bad values
            shooterMotor.setVoltage(SLMath.clamp(shootSpeed, -1.0, 16));
            feederMotor.setVoltage(SLMath.clamp(feederSpeed, -1.0, 16));
        }

        // SmartDashboard
        
        if(Constants.DEBUG_MODE.get()) {
            SmartDashboard.putString("Shooter/Mode", getMode().name());
            SmartDashboard.putNumber("Shooter/Target RPM", getTargetRPM());
            SmartDashboard.putNumber("Shooter/Shooter RPM", getShooterRPM());
            SmartDashboard.putNumber("Shooter/Feeder RPM", getFeederRPM());
            SmartDashboard.putBoolean("Shooter/Hood Extended", hoodSolenoid.get());
        }
    }

    /********
     * HOOD *
     ********/

    public void extendHoodSolenoid() {
        hoodSolenoid.set(true);
    }

    public void retractHoodSolenoid() {
        hoodSolenoid.set(false);
    }

    public void setDefaultSolenoidPosition() {
        retractHoodSolenoid();
    }
}
