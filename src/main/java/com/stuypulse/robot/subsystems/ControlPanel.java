package com.stuypulse.robot.subsystems;

import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.stuypulse.robot.Constants;
import com.stuypulse.robot.util.ColorSensor;
import com.stuypulse.stuylib.file.FRCLogger.Loggable;

public class ControlPanel extends SubsystemBase implements Loggable {
    private enum State {
        NONE(""),
        TURN("Turning"),
        STOP("Stopping"),
        COLOR("Color: ");

        private String message;
        State(String message) {
            this.message = message;
        } 

        public String getMessage() {
            return message;
        }

        public void setMessage(String newMessage) {
            message = newMessage;
        }
    }

    private CANSparkMax motor;
    private ColorSensor cs;
    private State state;

    public ControlPanel() {
        
        cs = new ColorSensor();
        motor = new CANSparkMax(Constants.CONTROL_PANEL_MOTOR_PORT, MotorType.kBrushless);
        state = State.NONE;
    }

    public void turn(double speed) {
        motor.set(speed);
        state = State.TURN;
    }

    public Color getColor() {
        state = State.COLOR;
        state.setMessage("Color: " + cs.getRawDetectedColor());
        return cs.getRawDetectedColor();
    }

    public void stop() {
        motor.set(0);
        state = State.STOP;
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

