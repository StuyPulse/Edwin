/* Copyright (c) 2021 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.robot.subsystems;

import com.stuypulse.stuylib.control.Controller;
import com.stuypulse.stuylib.control.PIDController;
import com.stuypulse.stuylib.math.SLMath;
import com.stuypulse.stuylib.network.SmartBoolean;
import com.stuypulse.stuylib.network.SmartNumber;
import com.stuypulse.stuylib.streams.filters.IFilter;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import com.stuypulse.robot.Constants.Ports;
import com.stuypulse.robot.Constants.ShooterSettings;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Units;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase {
    public enum ShooterMode {
        // Disable The Shooter
        DISABLED(
                new SmartNumber("Shooting/DISABLED/Distance", -1.0),
                new SmartNumber("Shooting/DISABLED/RPM", 0),
                new SmartBoolean("Shooting/DISABLED/Hood Extended", false)),

        // THIS IS THE INITIATION LINE SHOT
        GREEN_ZONE(
                new SmartNumber("Shooting/Green Zone/Distance", Units.feetToMeters(7)),
                new SmartNumber("Shooting/Green Zone/RPM", 2075),
                new SmartBoolean("Shooting/Green Zone/Hood Extended", true)),

        // TODO: TUNE THIS
        YELLOW_ZONE(
                new SmartNumber("Shooting/Yellow Zone/Distance", Units.feetToMeters(11)),
                new SmartNumber("Shooting/Yellow Zone/RPM", 2500),
                new SmartBoolean("Shooting/Yellow Zone/Hood Extended", true)),

        // THIS IS THE TRENCH SHOT
        BLUE_ZONE(
                new SmartNumber("Shooting/Blue Zone/Distance", Units.feetToMeters(16.5)),
                new SmartNumber("Shooting/Blue Zone/RPM", 3000),
                new SmartBoolean("Shooting/Blue Zone/Hood Extended", false)),

        // TODO: TUNE THIS
        RED_ZONE(
                new SmartNumber("Shooting/Red Zone/Distance", Units.feetToMeters(21)),
                new SmartNumber("Shooting/Red Zone/RPM", 3750),
                new SmartBoolean("Shooting/Red Zone/Hood Extended", false));

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
    private ShooterMode currentMode;

    // PID Controllers for the shooter and feeder
    private Controller shooterController;
    private Controller feederController;

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
        currentMode = ShooterMode.DISABLED;

        // Add Children to Subsystem
        addChild("Hood Solenoid", hoodSolenoid);
    }

    /************
     * SHOOTING *
     ************/

    public double getTargetRPM() {
        return getMode().rpm.get();
    }

    public double getShooterRPM() {
        return (Math.abs(shooterEncoderA.getVelocity())
                        + Math.abs(shooterEncoderB.getVelocity())
                        + Math.abs(shooterEncoderC.getVelocity()))
                / 3.0;
    }

    public double getFeederRPM() {
        return feederEncoder.getVelocity();
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

        // Set the speed according to the mode
        if (getMode().equals(ShooterMode.DISABLED) || getTargetRPM() < ShooterSettings.TOLERANCE) {
            shooterMotor.stopMotor();
            feederMotor.stopMotor();
        } else {
            // Feed forward
            double shootSpeed = getTargetRPM() * ShooterSettings.Shooter.FF.get();
            shootSpeed += shooterController.update(getTargetRPM(), getShooterRPM());

            double feederSpeed = getTargetRPM() * ShooterSettings.Feeder.FF.get();
            feederSpeed += feederController.update(getTargetRPM(), getShooterRPM());

            // Set the speeds of the motors
            shooterMotor.set(shootSpeed);
            feederMotor.set(feederSpeed);
        }

        // SmartDashboard
        SmartDashboard.putString("Shooter/Mode", getMode().name());
        SmartDashboard.putNumber("Shooter/Target RPM", getTargetRPM());
        SmartDashboard.putNumber("Shooter/Shooter RPM", getShooterRPM());
        SmartDashboard.putNumber("Shooter/Feeder RPM", getFeederRPM());
        SmartDashboard.putBoolean("Shooter/Hood Extended", hoodSolenoid.get());
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
