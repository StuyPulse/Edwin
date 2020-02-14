package com.stuypulse.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.stuypulse.robot.Constants;
import com.stuypulse.robot.util.BrownoutProtection;

public class Climber extends SubsystemBase implements BrownoutProtection{

    private CANSparkMax liftMotor;
    private CANSparkMax yoyoMotor;
    
    
    public Climber() {
        liftMotor = new CANSparkMax(Constants.CLIMBER_LIFT_MOTOR_PORT, MotorType.kBrushless);
        yoyoMotor = new CANSparkMax(Constants.CLIMBER_YOYO_MOTOR_PORT, MotorType.kBrushless);
    }

    public void climbUp() {
        liftMotor.set(Constants.CLIMB_UP_SPEED);
    }

    public void climbDown() {
        liftMotor.set(Constants.CLIMB_DOWN_SPEED);
    }

    public void moveLeft(double speed) {
        yoyoMotor.set(speed);
    }

    public void moveRight(double speed) {
        yoyoMotor.set(speed);
    }

    @Override
    public void enableBrownout() {
        liftMotor.setSmartCurrentLimit(Constants.CURRENT_LIMIT); 
        yoyoMotor.setSmartCurrentLimit(Constants.CURRENT_LIMIT);
    }

    @Override
    public void disableBrownout() {
        liftMotor.setSmartCurrentLimit(0);
        yoyoMotor.setSmartCurrentLimit(0);
    }
}