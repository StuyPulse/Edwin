package com.stuypulse.frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.stuypulse.frc.robot.Constants;
import com.stuypulse.frc.util.NEOEncoder;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase {

    //Shooter Motors
    private CANSparkMax leftShooterMotor;
    private CANSparkMax rightShooterMotor;
    private CANSparkMax middleShooterMotor;

    //Shooter Encoders
    private NEOEncoder leftShooterEncoder;
    private NEOEncoder rightShooterEncoder; 
    private NEOEncoder middleShooterEncoder;

    //Feeder Motor
    private CANSparkMax feederMotor;

    //Hood Solenoid
    private Solenoid hoodSolenoid;

    //SpeedControllerGroup
    private SpeedControllerGroup shooterMotors; 

    //PIDController
    private PIDController shooterController;

    public Shooter() {
        leftShooterMotor = new CANSparkMax(Constants.LEFT_SHOOTER_MOTOR_PORT, MotorType.kBrushless);
        rightShooterMotor = new CANSparkMax(Constants.RIGHT_SHOOTER_MOTOR_PORT, MotorType.kBrushless);
        middleShooterMotor = new CANSparkMax(Constants.MIDDLE_SHOOTER_MOTOR_PORT, MotorType.kBrushless);

        leftShooterMotor.setInverted(true);

        leftShooterEncoder = new NEOEncoder(leftShooterMotor.getEncoder());
        rightShooterEncoder = new NEOEncoder(rightShooterMotor.getEncoder());
        middleShooterEncoder = new NEOEncoder(middleShooterMotor.getEncoder());

        feederMotor = new CANSparkMax(Constants.FEEDER_MOTOR_PORT, MotorType.kBrushless);

        hoodSolenoid = new Solenoid(Constants.HOOD_SOLENOID_PORT);

        shooterMotors = new SpeedControllerGroup(leftShooterMotor, rightShooterMotor, middleShooterMotor);

        shooterController = new PIDController(Constants.SHOOTER_SHOOT_KP, Constants.SHOOTER_SHOOT_KI, Constants.SHOOTER_SHOOT_KD);
    }

    public double getError(double targetVelocity) {
        return targetVelocity - getCurrentShooterVelocity();
    }
    
    public double getRawMaxShooterVelocity() {
        return Math.max(leftShooterEncoder.getVelocity(), Math.max(middleShooterEncoder.getVelocity(), rightShooterEncoder.getVelocity())); 
    }

    public double getCurrentShooterVelocity() {
        return getRawMaxShooterVelocity() * Constants.SHOOTER_VELOCITY_EMPIRICAL_MULTIPLER;
    }

    public double getScaledVelocity(double targetVelocity) {
        return (shooterController.calculate(getError(targetVelocity), 0)); // / Constants.SHOOTER_MAX_VELOCITY
    }

    public void startShooter(double targetVelocity) {
        shooterMotors.set(getScaledVelocity(targetVelocity));
    }

    public void stopShooter() {
        shooterMotors.set(0.0);
    }

    public void startFeeder() {
        feederMotor.set(1.0);
    }

    public void stopFeeder() {
        feederMotor.set(0.0);
    }

    public void vomit() {
        feederMotor.set(-1.0);
    }

    public void extendHoodSolenoid() {
        hoodSolenoid.set(true);
    }

    public void retractHoodSolenoid() {
        hoodSolenoid.set(false);
    }

    public void setDefaultSolenoidPosition() {
        hoodSolenoid.set(true);
    }


}