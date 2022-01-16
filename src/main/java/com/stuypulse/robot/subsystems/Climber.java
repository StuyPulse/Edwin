/* Copyright (c) 2021 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import com.stuypulse.robot.Constants;
import com.stuypulse.robot.Constants.ClimberSettings;
import com.stuypulse.robot.Constants.Ports;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Climber extends SubsystemBase {

    private CANSparkMax liftMotor;
    private Solenoid liftSolenoid;

    public Climber() {
        liftMotor = new CANSparkMax(Ports.Climber.LIFT_MOTOR_PORT, MotorType.kBrushless);
        liftSolenoid = new Solenoid(PneumaticsModuleType.CTREPCM, Ports.Climber.LIFT_SOLENOID_CHANNEL);

        liftMotor.setInverted(true);

        // Add Children to Subsystem
        addChild("Life Solenoid", liftSolenoid);
    }

    public void setNeutralMode(IdleMode mode) {
        liftMotor.setIdleMode(mode);
    }

    public void moveLiftDown() {
        moveLift(ClimberSettings.MOVE_LIFT_DOWN_SPEED.get());
    }

    public void moveLiftUp() {
        moveLift(ClimberSettings.MOVE_LIFT_UP_SPEED.get());
    }

    public void moveLiftDownSlow() {
        moveLift(-ClimberSettings.MOVE_SLOW_SPEED.get());
    }

    public void moveLift(double speed) {
        liftMotor.set(speed);
    }

    public void stopClimber() {
        liftMotor.stopMotor();
    }

    public void toggleLiftBrake() {
        liftSolenoid.set(!liftSolenoid.get());
    }

    public void enableLiftBrake() {
        liftSolenoid.set(false);
    }

    public void releaseLiftBrake() {
        liftSolenoid.set(true);
    }

    public boolean isAtBottom() {
        return false;
        // return limitSwitch.get();
    }

    @Override
    public void periodic() {
        // SmartDashboard

        if(Constants.DEBUG_MODE.get()) {
            SmartDashboard.putBoolean("Climber/Is At Bottom", isAtBottom());
        }
    }
}