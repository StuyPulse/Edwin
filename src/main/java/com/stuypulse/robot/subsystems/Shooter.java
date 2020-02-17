package com.stuypulse.robot.subsystems;

import java.util.Arrays;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.stuypulse.robot.Constants;
import com.stuypulse.stuylib.network.SmartNumber;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase {

    public enum Mode {
        NONE, 
        SHOOT_FROM_INITIATION_LINE, 
        SHOOT_FROM_TRENCH, 
        SHOOT_FROM_FAR,
        CHARGING_FROM_INITATION_LINE,
        CHARGING_FROM_TRENCH,
        CHARGING_FROM_FAR
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
    private final SmartNumber targetShooterVelocity;
    private final SmartNumber currentShooterVelocity;

    private final SmartNumber currentFeederVelocity;

    private Mode mode = Mode.NONE;

    public Shooter() {
        leftShooterMotor = new CANSparkMax(Constants.LEFT_SHOOTER_MOTOR_PORT, MotorType.kBrushless);
        rightShooterMotor = new CANSparkMax(Constants.RIGHT_SHOOTER_MOTOR_PORT, MotorType.kBrushless);
        middleShooterMotor = new CANSparkMax(Constants.MIDDLE_SHOOTER_MOTOR_PORT, MotorType.kBrushless);

        leftShooterMotor.setInverted(true);

        leftShooterEncoder = new CANEncoder(leftShooterMotor);
        rightShooterEncoder = new CANEncoder(rightShooterMotor);
        middleShooterEncoder = new CANEncoder(middleShooterMotor);

        feederMotor = new CANSparkMax(Constants.FEEDER_MOTOR_PORT, MotorType.kBrushless);

        hoodSolenoid = new Solenoid(Constants.HOOD_SOLENOID_PORT);

        shooterMotors = new SpeedControllerGroup(leftShooterMotor, rightShooterMotor, middleShooterMotor);

        targetShooterVelocity = new SmartNumber("Shooter Target Vel", 69420);
        currentShooterVelocity = new SmartNumber("Shooter Current Vel", -1);

        currentFeederVelocity = new SmartNumber("Feeder Current Vel", -1);
    }

    public double getRawMedianShooterVelocity() {
        final double[] speeds = { leftShooterEncoder.getVelocity(), middleShooterEncoder.getVelocity(),
                rightShooterEncoder.getVelocity() };

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
        final double speed = getRawMedianShooterVelocity() * Constants.SHOOTER_VELOCITY_EMPIRICAL_MULTIPLER;
        currentShooterVelocity.set(speed);
        return speed;
    }

    public double getRawFeederVelocity() {
        return feederEncoder.getVelocity();
    }

    public double getCurrentFeederVelocityInRPM() {
        final double speed = getRawFeederVelocity() * Constants.FEEDER_VELOCITY_EMPIRICAL_MULTIPLER;
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

    public void reverseShooter() {
        shooterMotors.set(-1.0);
    }

    public void stopShooter() {
        shooterMotors.stopMotor();
    }

    public void runFeeder() {
        feederMotor.set(Constants.FEEDER_SPEED);
    }

    public void reverseFeed() {
        feederMotor.set(-1.0);
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

    public void setShooterMode(Mode mode) {
        this.mode = mode;
    }

    public Mode getShooterMode() {
        return mode;
    }

    public boolean isAtTargetVelocity() {
        return (Math.abs(getTargetVelocity() - getCurrentShooterVelocityInRPM()) <= Constants.SHOOTER_TOLERANCE);
    }
}