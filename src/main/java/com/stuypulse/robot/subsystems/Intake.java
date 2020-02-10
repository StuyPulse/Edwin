package com.stuypulse.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.stuypulse.robot.Constants;
import com.stuypulse.robot.util.FRCLogger.Loggable;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class Intake extends SubsystemBase implements Loggable {
    private enum State {
        NONE(""),
        EXTEND("Extended intake solenoid"),
        RETRACT("Retracted intake solenoid"),
        ACQUIRE("Acquired"),
        DEACQUIRE("Deacquired");

        private String message;
        State(String message) {
            this.message = message;
        } 

        public String getMessage() {
            return message;
        }
    }

    private CANSparkMax intakeMotor;
    private Solenoid intakeSolenoid;
    private State state;
  
    public Intake() {
        intakeMotor = new CANSparkMax(Constants.INTAKE_MOTOR_PORT, MotorType.kBrushless);
        intakeSolenoid = new Solenoid(Constants.INTAKE_SOLENOID_PORT);
        state = State.NONE;
    }

    public boolean isExtended() {
        return intakeSolenoid.get();
    }

    public void extend() {
        intakeSolenoid.set(true);
        state = State.EXTEND;
    }

    public void retract() {
        intakeSolenoid.set(false);
        state = State.RETRACT;
    }

    public void acquire() {
        intakeMotor.set(1);
        state = State.ACQUIRE;
    }

    public void deacquire() {
        intakeMotor.set(-1);
        state = State.DEACQUIRE;
    }

    public void setMotor(double speed) {
        intakeMotor.set(speed);
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