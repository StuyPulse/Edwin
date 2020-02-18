package com.stuypulse.robot.subsystems;

import java.util.Arrays;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.stuypulse.robot.Constants.Ports;
import com.stuypulse.stuylib.network.SmartNumber;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase {

    // Motors
    private CANSparkMax leftShooterMotor;
    private CANSparkMax rightShooterMotor;
    private CANSparkMax middleShooterMotor;
    private CANSparkMax feederMotor;

    // Encoders
    private CANEncoder leftShooterEncoder;
    private CANEncoder rightShooterEncoder;
    private CANEncoder middleShooterEncoder;
    private CANEncoder feederEncoder;

    // Hood Solenoid
    private Solenoid hoodSolenoid;

    // SpeedControllerGroup
    private SpeedControllerGroup shooterMotors;

    // SmartNumbers for SmartDashboard
    private SmartNumber targetShooterVelocity;
    private SmartNumber currentShooterVelocity;

    private SmartNumber currentFeederVelocity;

    public Shooter() {
        leftShooterMotor = new CANSparkMax(Ports.Shooter.LEFT, MotorType.kBrushless);
        rightShooterMotor = new CANSparkMax(Ports.Shooter.RIGHT, MotorType.kBrushless);
        middleShooterMotor = new CANSparkMax(Ports.Shooter.MIDDLE, MotorType.kBrushless);

        leftShooterMotor.setInverted(true);

        leftShooterEncoder = new CANEncoder(leftShooterMotor);
        rightShooterEncoder = new CANEncoder(rightShooterMotor);
        middleShooterEncoder = new CANEncoder(middleShooterMotor);

        feederMotor = new CANSparkMax(Ports.Shooter.FEEDER, MotorType.kBrushless);

        hoodSolenoid = new Solenoid(Ports.HOOD_SOLENOID);

        shooterMotors = new SpeedControllerGroup(leftShooterMotor, rightShooterMotor, middleShooterMotor);

        targetShooterVelocity = new SmartNumber("Shooter Target Vel", 30);
        currentShooterVelocity = new SmartNumber("Shooter Current Vel", -1);

        currentFeederVelocity = new SmartNumber("Feeder Current Vel", -1);

        feederMotor.setIdleMode(IdleMode.kCoast);
        feederMotor.setInverted(true);
        feederEncoder = new CANEncoder(feederMotor);
    }

    public double getRawMedianShooterVelocity() {
        double[] speeds = { 
            leftShooterEncoder.getVelocity(), 
            middleShooterEncoder.getVelocity(),
            rightShooterEncoder.getVelocity() 
        };

        Arrays.sort(speeds);

        return speeds[1];
    }

    public double getRawMaxShooterVelocity() {
        return Math.max(leftShooterEncoder.getVelocity(),
                Math.max(middleShooterEncoder.getVelocity(), rightShooterEncoder.getVelocity()));
    }

    public double getRawMinShooterVelocity() {
        return Math.min(leftShooterEncoder.getVelocity(),
                Math.min(middleShooterEncoder.getVelocity(), rightShooterEncoder.getVelocity()));
    }

    public double getCurrentShooterVelocityInRPM() {
        double speed = getRawMedianShooterVelocity();
        currentShooterVelocity.set(speed);
        return speed;
    }

    public double getRawFeederVelocity() {
        return feederEncoder.getVelocity();
    }

    public double getCurrentFeederVelocityInRPM() {
        double speed = getRawFeederVelocity();
        currentFeederVelocity.set(speed);
        return speed;
    }

    public void setShooterSpeed(double speed) {
        shooterMotors.set(speed);
    }

    public void setFeederSpeed(double speed) {
        feederMotor.set(speed);
    }

    public void setTargetVelocity(double targetVelocity) {
        this.targetShooterVelocity.set(targetVelocity);
    }

    public double getTargetVelocity() {
        return this.targetShooterVelocity.get();
    }

    public void stopShooter() {
        shooterMotors.stopMotor();
    }

    public void stopFeeder() {
        feederMotor.stopMotor();
    }

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