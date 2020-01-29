package com.stuypulse.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.stuypulse.robot.Constants;
import com.stuypulse.robot.util.FRCLogger.Loggable;

public class Funnel extends SubsystemBase implements Loggable {

    private CANSparkMax motor;
    private boolean stateChanged;

    public Funnel() {
        motor = new CANSparkMax(Constants.FUNNEL_MOTOR_PORT, MotorType.kBrushless);
        stateChanged = false;
    }

    public void funnel() {
        motor.set(Constants.FUNNEL_SPEED);
        stateChanged = true;
    }

    public void unfunnel() {
        motor.set(Constants.UNFUNNEL_SPEED);
        stateChanged = true;
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
        (motor.get() == Constants.FUNNEL_SPEED ? "Funneled" : "Unfunneled");
    }

}