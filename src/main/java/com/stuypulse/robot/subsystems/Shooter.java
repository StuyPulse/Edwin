package com.stuypulse.robot.subsystems;

import java.util.Arrays;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.stuypulse.robot.Constants;
import com.stuypulse.robot.Constants.Ports;
import com.stuypulse.robot.Constants.Shooting;
import com.stuypulse.robot.util.BrownoutProtection;
import com.stuypulse.stuylib.network.SmartNumber;
import com.stuypulse.stuylib.streams.filters.IStreamFilter;
import com.stuypulse.stuylib.streams.filters.IStreamFilterGroup;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase implements BrownoutProtection {

    public enum ShooterMode {
        NONE, 
        SHOOT_FROM_INITIATION_LINE, 
        SHOOT_FROM_TRENCH, 
        SHOOT_FROM_FAR
    };

    // Motors
    private final CANSparkMax leftShooterMotor;
    private final CANSparkMax rightShooterMotor;
    private final CANSparkMax middleShooterMotor;
    private final CANSparkMax feederMotor;

    // Encoders
    private final CANEncoder leftShooterEncoder;
    private final CANEncoder rightShooterEncoder;
    private final CANEncoder middleShooterEncoder;
    private CANEncoder feederEncoder;

    // Hood Solenoid
    private final Solenoid hoodSolenoid;

    // SpeedControllerGroup
    private final SpeedControllerGroup shooterMotors;

    // SmartNumbers for SmartDashboard
    private SmartNumber targetShooterVelocity;
    private SmartNumber currentShooterVelocity;

    private SmartNumber currentFeederVelocity;

    private ShooterMode mode = ShooterMode.NONE;

    public Shooter() {
        // Shooter Stuff
        leftShooterMotor = new CANSparkMax(Ports.Shooter.LEFT, MotorType.kBrushless);
        rightShooterMotor = new CANSparkMax(Ports.Shooter.RIGHT, MotorType.kBrushless);
        middleShooterMotor = new CANSparkMax(Ports.Shooter.MIDDLE, MotorType.kBrushless);

        leftShooterMotor.setInverted(true);

        leftShooterEncoder = new CANEncoder(leftShooterMotor);
        rightShooterEncoder = new CANEncoder(rightShooterMotor);
        middleShooterEncoder = new CANEncoder(middleShooterMotor);

        shooterMotors = new SpeedControllerGroup(leftShooterMotor, rightShooterMotor, middleShooterMotor);

        // Feeder Stuff
        feederMotor = new CANSparkMax(Ports.Shooter.FEEDER, MotorType.kBrushless);

        feederMotor.setInverted(true);
        feederEncoder = new CANEncoder(feederMotor);

        // Hood Stuff
        hoodSolenoid = new Solenoid(Ports.HOOD_SOLENOID);

        // Target Vel Stuff
        targetShooterVelocity = new SmartNumber("Shooter Target Vel", 30);
        currentShooterVelocity = new SmartNumber("Shooter Current Vel", -1);

        currentFeederVelocity = new SmartNumber("Feeder Current Vel", -1);

        // Setting Modes Stuff
        rightShooterMotor.setIdleMode(IdleMode.kCoast);
        leftShooterMotor.setIdleMode(IdleMode.kCoast);
        middleShooterMotor.setIdleMode(IdleMode.kCoast);

        feederMotor.setIdleMode(IdleMode.kCoast);

        rightShooterMotor.setSmartCurrentLimit(Shooting.CURRENT_LIMIT);
        leftShooterMotor.setSmartCurrentLimit(Shooting.CURRENT_LIMIT);
        middleShooterMotor.setSmartCurrentLimit(Shooting.CURRENT_LIMIT);

        feederMotor.setSmartCurrentLimit(Shooting.CURRENT_LIMIT);
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

    private IStreamFilter shooterFilter = new IStreamFilterGroup(
        (x) -> Math.max(0.0, x),
        (x) -> (targetShooterVelocity.doubleValue() < 100) ? 0.0 : x
    );

    private IStreamFilter feederFilter = new IStreamFilterGroup(
        (x) -> Math.max(0.0, x),
        (x) -> (targetShooterVelocity.doubleValue() < 100) ? 0.0 : x
    );

    public void setShooterSpeed(double speed) {
        shooterMotors.set(shooterFilter.get(speed));
    }

    public void setFeederSpeed(double speed) {
        feederMotor.set(feederFilter.get(speed));
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

    public void setShooterMode(ShooterMode mode) {
        this.mode = mode;
    }

    public ShooterMode getShooterMode() {
        return mode;
    }

    public boolean isAtTargetVelocity() {
        return (Math.abs(getTargetVelocity() - getCurrentShooterVelocityInRPM()) <= Constants.Shooting.TOLERANCE);
    }

    @Override
    public void enableBrownout() {
        leftShooterMotor.setSmartCurrentLimit(Constants.CURRENT_LIMIT);
        middleShooterMotor.setSmartCurrentLimit(Constants.CURRENT_LIMIT); 
        rightShooterMotor.setSmartCurrentLimit(Constants.CURRENT_LIMIT); 
        feederMotor.setSmartCurrentLimit(Constants.CURRENT_LIMIT); 
    }

    @Override
    public void disableBrownout() {
        leftShooterMotor.setSmartCurrentLimit(0);
        middleShooterMotor.setSmartCurrentLimit(0); 
        rightShooterMotor.setSmartCurrentLimit(0); 
        feederMotor.setSmartCurrentLimit(0); 
    }
}