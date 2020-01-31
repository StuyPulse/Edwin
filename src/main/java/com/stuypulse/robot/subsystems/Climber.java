package com.stuypulse.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.stuypulse.robot.Constants;
import com.stuypulse.robot.util.FRCLogger.Loggable;

public class Climber extends SubsystemBase implements Loggable {

    private CANSparkMax liftMotor;
    private CANSparkMax yoyoMotor;

    private boolean stateChanged;

    public Climber() {
        liftMotor = new CANSparkMax(Constants.CLIMBER_LIFT_MOTOR_PORT, MotorType.kBrushless);
        yoyoMotor = new CANSparkMax(Constants.CLIMBER_YOYO_MOTOR_PORT, MotorType.kBrushless);
    }

    public void climbUp() {
        liftMotor.set(Constants.CLIMB_UP_SPEED);
        stateChanged = true;
    }

    public void climbDown() {
        liftMotor.set(Constants.CLIMB_DOWN_SPEED);
        stateChanged = true;
    }

    public void moveLeft(double speed) {
        yoyoMotor.set(speed);
    }

    public void moveRight(double speed) {
        yoyoMotor.set(speed);
    }

    public boolean logThisIteration() {
        if(stateChanged) {
            stateChanged = false;
            return true;
        }
        return false;
    }

    public String getLogData() {
        return 
        "State changed to:" +
        (liftMotor.get() == Constants.CLIMB_UP_SPEED ? "Started climb up" : "Started climb down");
    }
    
}