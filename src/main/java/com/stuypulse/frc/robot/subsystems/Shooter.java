package com.stuypulse.frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;
//import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;

public class Shooter extends PIDSubsystem {
    //Shooter Motors
    private CANSparkMax leftShooterMotor;
    private CANSparkMax rightShooterMotor;
    private CANSparkMax middleShooterMotor;

    //Shooter Encoders
    private CANEncoder leftShooterEncoder;
    private CANEncoder rightShooterEncoder; 
    private CANEncoder middleShooterEncoder;

    //Feeder Motor
    private CANSparkMax feederMotor;

    //Hood Solenoid
    private Solenoid hoodSolenoid;

    // SpeedControllerGroup
    private SpeedControllerGroup shooterMotors;

    // TODO: implement a feedforward 

    public Shooter() {
        // TODO: implement PID fully

        super(new PIDController(-1, -1, -1));
        leftShooterMotor = new CANSparkMax(-1, MotorType.kBrushless);
        rightShooterMotor = new CANSparkMax(-1, MotorType.kBrushless);
        middleShooterMotor = new CANSparkMax(-1, MotorType.kBrushless);

        leftShooterEncoder = new CANEncoder(leftShooterMotor);
        rightShooterEncoder = new CANEncoder(rightShooterMotor);
        middleShooterEncoder = new CANEncoder(middleShooterMotor);

        feederMotor = new CANSparkMax(-1, MotorType.kBrushless);

        hoodSolenoid = new Solenoid(-1);

        shooterMotors = new SpeedControllerGroup(leftShooterMotor, rightShooterMotor, middleShooterMotor);
    }

    @Override
    protected void useOutput(double output, double setpoint) {
        shooterMotors.set(output); // sets the speed of the shooterMotors to the PIDOutput
    }

    @Override
    protected double getMeasurement() {
        return 0;
    }
    
    public double getMaxShooterVelocity() {
        // returns the velocity in RPM of the fastest shooterMotor
        if ((leftShooterEncoder.getVelocity() > middleShooterEncoder.getVelocity()) && (leftShooterEncoder.getVelocity() > rightShooterEncoder.getVelocity())) {
            return leftShooterEncoder.getVelocity();
        } else if (middleShooterEncoder.getVelocity() > rightShooterEncoder.getVelocity()) {
            return middleShooterEncoder.getVelocity();        
        } return rightShooterEncoder.getVelocity();
    }

    public void runShooterMotors() {
        shooterMotors.set(1.0);
    }

    public void stopShooterMotors() {
        shooterMotors.set(0.0);
    }

    public void feed() {
        feederMotor.set(1.0);
    }

    public void vomit() {
        feederMotor.set(-1.0);
    }

    public void stopFeeder() {
        feederMotor.set(0.0);
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