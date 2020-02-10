package com.stuypulse.robot.subsystems;

import java.util.Arrays;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.stuypulse.robot.Constants;
import com.stuypulse.stuylib.network.SmartNumber;

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
    private CANEncoder leftShooterEncoder;
    private CANEncoder rightShooterEncoder; 
    private CANEncoder middleShooterEncoder;

    // Feeder Motor
    private CANSparkMax feederMotor;

    // Hood Solenoid
    private Solenoid hoodSolenoid;

    // SpeedControllerGroup
    private SpeedControllerGroup shooterMotors; 

    // PIDController
    private PIDController shooterController;

    // SmartNumbers for SmartDashboard
    private SmartNumber targetVelocity;
    private SmartNumber currentVelocity;

    public Shooter() {
        leftShooterMotor = new CANSparkMax(Constants.LEFT_SHOOTER_MOTOR_PORT, MotorType.kBrushless);
        rightShooterMotor = new CANSparkMax(Constants.RIGHT_SHOOTER_MOTOR_PORT, MotorType.kBrushless);
        middleShooterMotor = new CANSparkMax(Constants.MIDDLE_SHOOTER_MOTOR_PORT, MotorType.kBrushless);

        leftShooterMotor.setInverted(true); //engineering said that the left motor is inverted

        leftShooterEncoder = new CANEncoder(leftShooterMotor);
        rightShooterEncoder = new CANEncoder(rightShooterMotor);
        middleShooterEncoder = new CANEncoder(middleShooterMotor);

        feederMotor = new CANSparkMax(Constants.FEEDER_MOTOR_PORT, MotorType.kBrushless);

        hoodSolenoid = new Solenoid(Constants.HOOD_SOLENOID_PORT);

        shooterMotors = new SpeedControllerGroup(leftShooterMotor, rightShooterMotor, middleShooterMotor);

        shooterController = new PIDController(Constants.SHOOTER_SHOOT_P, Constants.SHOOTER_SHOOT_I, Constants.SHOOTER_SHOOT_D);

        targetVelocity = new SmartNumber("Shooter Target Vel", 69420);
        currentVelocity = new SmartNumber("Shooter Current Vel", -1);
    }

    public double getError() {
        return targetVelocity.doubleValue() - getCurrentShooterVelocityInRPM();
        // the erorr is calculated by subtracting the target velocity by the current velocity.
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
        return Math.max(leftShooterEncoder.getVelocity(), Math.max(middleShooterEncoder.getVelocity(), rightShooterEncoder.getVelocity()));
    }

    public double getRawMinShooterVelocity() {
        return Math.min(leftShooterEncoder.getVelocity(), Math.min(middleShooterEncoder.getVelocity(), rightShooterEncoder.getVelocity()));
    }

    public double getCurrentShooterVelocityInRPM() {
        double speed = getRawMedianShooterVelocity() * Constants.SHOOTER_VELOCITY_EMPIRICAL_MULTIPLER;
        currentVelocity.set(speed);
        return speed;
        // returns the velocity of the shooter motors IN RPM by multiplying by the empirical factor. (TODO)
    }


    public double getScaledVelocity() {
        return (shooterController.calculate(getError(), 0));
        // converts the output of the PID loop to the NEOEncoder unit (-1.0 to 1.0) by
        // dividing the PIDOutput by the maximum RPM possible of the CANSparkMax motor.
        // Sam said that this is already taken in account by the P value.

        // the calculate method takes in a (double measurement, double setpoint) where 
        // the measurement is the error and the setpoint is the target RPM.
        // we want the error to be 0, which means the targetVelocity has been reached.
    }

    public void runShooter() {
        shooterMotors.set(getScaledVelocity());
        // PID input = targetVeloctiy
        // sets the encoder to the scaled velocity output of the PID loop.
    }

    public void reverseShooter() {
        shooterMotors.set(-1.0);
    }

    public void stopShooter() {
        shooterMotors.set(0.0);
    }

    public void runFeeder() {
        feederMotor.set(Constants.FEEDER_SPEED);
    }

    public void reverseFeed() {
        feederMotor.set(-1.0);
    }

    public void stopFeeder() {
        feederMotor.stopMotor();;
    }
    
    public void extendHoodSolenoid() {
        hoodSolenoid.set(true);
    }

    public void retractHoodSolenoid() {
        hoodSolenoid.set(false);
    }

    public void setDefaultSolenoidPosition() {
        retractHoodSolenoid();
        // default position of the solenoid is fired
    }
}