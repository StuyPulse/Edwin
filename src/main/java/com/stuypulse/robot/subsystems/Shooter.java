package com.stuypulse.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.stuypulse.robot.Constants;
import com.stuypulse.util.NEOEncoder;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase {

    // Shooter Motors
    // note: engineering wants to change to 2 motors to free up a spot on the pdp
    private CANSparkMax leftShooterMotor;
    private CANSparkMax rightShooterMotor;
    private CANSparkMax middleShooterMotor;

    // Shooter Encoders
    private NEOEncoder leftShooterEncoder;
    private NEOEncoder rightShooterEncoder; 
    private NEOEncoder middleShooterEncoder;

    // Feeder Motor
    private CANSparkMax feederMotor;

    // Hood Solenoid
    private Solenoid hoodSolenoid;

    // SpeedControllerGroup
    private SpeedControllerGroup shooterMotors; 

    // PIDController
    private PIDController shooterController;

    public Shooter() {
        leftShooterMotor = new CANSparkMax(Constants.LEFT_SHOOTER_MOTOR_PORT, MotorType.kBrushless);
        rightShooterMotor = new CANSparkMax(Constants.RIGHT_SHOOTER_MOTOR_PORT, MotorType.kBrushless);
        middleShooterMotor = new CANSparkMax(Constants.MIDDLE_SHOOTER_MOTOR_PORT, MotorType.kBrushless);

        leftShooterMotor.setInverted(true); //engineering said that the left motor is inverted

        leftShooterEncoder = new NEOEncoder(leftShooterMotor.getEncoder());
        rightShooterEncoder = new NEOEncoder(rightShooterMotor.getEncoder());
        middleShooterEncoder = new NEOEncoder(middleShooterMotor.getEncoder());

        feederMotor = new CANSparkMax(Constants.FEEDER_MOTOR_PORT, MotorType.kBrushless);

        hoodSolenoid = new Solenoid(Constants.HOOD_SOLENOID_PORT);

        shooterMotors = new SpeedControllerGroup(leftShooterMotor, rightShooterMotor, middleShooterMotor);

        shooterController = new PIDController(Constants.SHOOTER_SHOOT_KP, Constants.SHOOTER_SHOOT_KI, Constants.SHOOTER_SHOOT_KD);
    }

    public double getError(double targetVelocity) {
        return targetVelocity - getCurrentShooterVelocityInRPM();
        // the erorr is calculated by subtracting the target velocity by the current velocity.
    }
    
    public double getRawMaxShooterVelocity() {
        return Math.max(leftShooterEncoder.getVelocity(), Math.max(middleShooterEncoder.getVelocity(), rightShooterEncoder.getVelocity())); 
        // gets the max raw velocity of the shooter motors
    }

    public double getCurrentShooterVelocityInRPM() {
        return getRawMaxShooterVelocity() * Constants.SHOOTER_VELOCITY_EMPIRICAL_MULTIPLER;
        // returns the velocity of the shooter motors IN RPM by multiplying by the empirical factor. (TODO)
    }

    public double getScaledVelocity(double targetVelocity) {
        return (shooterController.calculate(getError(targetVelocity), 0)) / Constants.SHOOTER_MAX_RPM;
        // converts the output of the PID loop to the NEOEncoder unit (-1.0 to 1.0) by
        // dividing the PIDOutput by the maximum RPM possible of the CANSparkMax motor.
        // Sam said that this is already taken in account by the P value.

        // the calculate method takes in a (double measuremnt, double setpoint) where 
        // the measurement is the error and the setpoint is the target RPM.
        // we want the error to be 0, which means the targetVelocity has been reached.
    }

    public void runShooter(double targetVelocity) {
        shooterMotors.set(getScaledVelocity(targetVelocity));
        // PID input = targetVeloctiy
        // sets the encoder to the scaled velocity output of the PID loop.
    }

    public void stopShooter() {
        shooterMotors.set(0.0);
    }

    public void runFeeder() {
        feederMotor.set(1.0);
    }

    public void stopFeeder() {
        feederMotor.set(0.0);
    }

    public void vomit() {
        feederMotor.set(-1.0);
        // reverse feeder motor in case of jams
        // method name taken from DEStiny
    }

    public void extendHoodSolenoid() {
        hoodSolenoid.set(true);
    }

    public void retractHoodSolenoid() {
        hoodSolenoid.set(false);
    }

    public void setDefaultSolenoidPosition() {
        hoodSolenoid.set(true);
        // default position of the solenoid is fired
    }
}