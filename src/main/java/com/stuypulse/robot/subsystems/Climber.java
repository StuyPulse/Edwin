package com.stuypulse.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.stuypulse.robot.Constants;
import com.stuypulse.robot.util.FRCLogger.Loggable;

public class Climber extends SubsystemBase implements Loggable {
    private enum State {
        NONE(""),
        UP("Climbed up"),
        DOWN("Climbed down");

        private String message;

        State(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

    private CANSparkMax liftMotor;
    private CANSparkMax yoyoMotor;

    private State state;

    public Climber() {
        liftMotor = new CANSparkMax(Constants.CLIMBER_LIFT_MOTOR_PORT, MotorType.kBrushless);
        yoyoMotor = new CANSparkMax(Constants.CLIMBER_YOYO_MOTOR_PORT, MotorType.kBrushless);
        state = State.NONE;
    }

    public void climbUp() {
        liftMotor.set(Constants.CLIMB_UP_SPEED);
        state = State.UP;
    }

    public void climbDown() {
        liftMotor.set(Constants.CLIMB_DOWN_SPEED);
        state = State.DOWN;
    }

    public void moveLeft() {
        yoyoMotor.set(Constants.YOYO_MOVE_LEFT_SPEED);
    }

    public void moveRight() {
        yoyoMotor.set(Constants.YOYO_MOVE_RIGHT_SPEED);
    }

    public void stopClimber() {
        liftMotor.set(0);
    }

    public void stopYoyo() {
        yoyoMotor.set(0);
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