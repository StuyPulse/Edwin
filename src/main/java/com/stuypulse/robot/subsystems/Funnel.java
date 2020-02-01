package com.stuypulse.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.stuypulse.robot.Constants;
import com.stuypulse.robot.util.FRCLogger.Loggable;

public class Funnel extends SubsystemBase implements Loggable {
    private enum State {
        NONE(""),
        FUNNEL("Funneled"),
        UNFUNNEL("Unfunneled");

        private String message;

        State(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

    private CANSparkMax motor;
    private State state;

    public Funnel() {
        motor = new CANSparkMax(Constants.FUNNEL_MOTOR_PORT, MotorType.kBrushless);
        state = State.NONE;
    }

    public void funnel() {
        motor.set(Constants.FUNNEL_SPEED);
        state = State.FUNNEL;
    }

    public void unfunnel() {
        motor.set(Constants.UNFUNNEL_SPEED);
        state = State.UNFUNNEL;
    }

    public boolean logThisIteration() {
        boolean result = state != State.NONE;
        if(result) {
            state = State.NONE;
        }
        return result;
    }

    public String getLogData() {
        return state.getMessage();
    }

}