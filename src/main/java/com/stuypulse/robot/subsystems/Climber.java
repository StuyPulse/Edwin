/* Copyright (c) 2021 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import com.stuypulse.robot.Constants.ClimberSettings;
import com.stuypulse.robot.Constants.Ports;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Climber extends SubsystemBase {

    private CANSparkMax liftMotor;
    private CANSparkMax yoyoMotor;

    private Solenoid liftSolenoid;

    public Climber() {
        liftMotor = new CANSparkMax(Ports.Climber.LIFT_MOTOR_PORT, MotorType.kBrushless);
        yoyoMotor = new CANSparkMax(Ports.Climber.YOYO_MOTOR_PORT, MotorType.kBrushless);
        liftSolenoid = new Solenoid(Ports.Climber.LIFT_SOLENOID_CHANNEL);

        liftMotor.setInverted(true);

        // Add Children to Subsystem
        addChild("Life Solenoid", liftSolenoid);
    }

    public void setNeutralMode(IdleMode mode) {
        liftMotor.setIdleMode(mode);
    }

    public void moveLiftDown() {
        moveLift(ClimberSettings.MOVE_LIFT_DOWN_SPEED);
    }

    public void moveLiftUp() {
        moveLift(ClimberSettings.MOVE_LIFT_UP_SPEED);
    }

    public void moveLiftDownSlow() {
        moveLift(-ClimberSettings.MOVE_SLOW_SPEED);
    }

    public void moveLift(double speed) {
        liftMotor.set(speed);
    }

    public void moveYoyo(double speed) {
        yoyoMotor.set(speed);
    }

    public void stopClimber() {
        liftMotor.stopMotor();
    }

    public void stopYoyo() {
        yoyoMotor.stopMotor();
    }

    public void toggleLiftBrake() {
        liftSolenoid.set(!liftSolenoid.get());
    }

    public void enableLiftBrake() {
        if (liftSolenoid.get()) {
            liftSolenoid.set(false);
        }
    }

    public void releaseLiftBrake() {
        if (!liftSolenoid.get()) {
            liftSolenoid.set(true);
        }
    }

    public boolean isAtBottom() {
        return false;
        // return limitSwitch.get();
    }

    @Override
    public void periodic() {
        // SmartDashboard
        SmartDashboard.putBoolean("Climber/Is At Bottom", isAtBottom());
    }
}
