package com.stuypulse.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.stuypulse.robot.Constants;
import com.stuypulse.robot.util.FRCLogger.Loggable;

public class Climber extends SubsystemBase implements Loggable {

    private CANSparkMax liftMotor;
    private CANSparkMax yoyoMotor;

    private String stateChanged;

    public Climber() {
        liftMotor = new CANSparkMax(Constants.CLIMBER_LIFT_MOTOR_PORT, MotorType.kBrushless);
        yoyoMotor = new CANSparkMax(Constants.CLIMBER_YOYO_MOTOR_PORT, MotorType.kBrushless);
        stateChanged = "none";
    }

    public void climbUp() {
        liftMotor.set(Constants.CLIMB_UP_SPEED);
        stateChanged = "Climbing up";
    }

    public void climbDown() {
        liftMotor.set(Constants.CLIMB_DOWN_SPEED);
        stateChanged = "Climbing down";
    }

    public void moveLeft(double speed) {
        yoyoMotor.set(speed);
        stateChanged = "Moving left";
    }

    public void moveRight(double speed) {
        yoyoMotor.set(speed);
        stateChanged = "Moving right";
    }

    public boolean logThisIteration() {
        if(!stateChanged.equals("none")) {
            stateChanged = "none";
            return true;
        }
        return false;
    }

    public String getLogData() {
        return "State changed to: " + stateChanged;
    }

}