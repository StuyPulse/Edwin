package com.stuypulse.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.stuypulse.robot.Constants;

public class Climber extends SubsystemBase {
    
    private CANSparkMax liftMotor;
    private CANSparkMax yoyoMotor;

    private Solenoid liftSolenoid;
    private Solenoid yoyoSolenoid;

    public Climber() {
        liftMotor = new CANSparkMax(Constants.CLIMBER_LIFT_MOTOR_PORT, MotorType.kBrushless);
        yoyoMotor = new CANSparkMax(Constants.CLIMBER_YOYO_MOTOR_PORT, MotorType.kBrushless);
        liftSolenoid = new Solenoid(Constants.CLIMBER_LIFT_SOLENOID_CHANNEL);
        yoyoSolenoid = new Solenoid(Constants.CLIMBER_YOYO_SOLENOID_CHANNEL);
    }

    public void climbUp() {
        liftMotor.set(Constants.CLIMB_UP_SPEED);
    }

    public void climbDown() {
        liftMotor.set(Constants.CLIMB_DOWN_SPEED);
    }

    public void moveYoyo(double speed) {
        yoyoMotor.set(speed);
    }

    public void stopClimber() {
        liftMotor.set(0);
    }

    public void stopYoyo() {
        yoyoMotor.set(0);
    }

    public void toggleLiftBrake() {
        liftSolenoid.set(!liftSolenoid.get());
    }

    public void enableYoyoBrake() {
        yoyoSolenoid.set(true);
    }

    public void disableYoyoBrake() {
        yoyoSolenoid.set(false);
    }
}