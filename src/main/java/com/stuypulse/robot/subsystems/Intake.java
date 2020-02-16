package com.stuypulse.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.stuypulse.robot.Constants;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.stuypulse.robot.Constants;

public class Intake extends SubsystemBase {

    private CANSparkMax motor;
    private DoubleSolenoid solenoid;

    public Intake() {
        motor = new CANSparkMax(Constants.INTAKE_MOTOR_PORT, MotorType.kBrushless);
        solenoid = new DoubleSolenoid(Constants.INTAKE_SOLENOID_PORT_A, Constants.INTAKE_SOLENOID_PORT_B);
    }

    public void extend() {
        if(solenoid.get() == Value.kReverse) {
            solenoid.set(Value.kForward);
        }
    }

    public void retract() {
        if(solenoid.get() == Value.kForward) {
            solenoid.set(Value.kReverse);
        }
    }

    public void toggle() {
        if (solenoid.get() == Value.kForward) {
            retract();
        } else {
            extend();
        }
    }

    public void acquire() {
        setMotor(Constants.INTAKE_MOTOR_SPEED);
    }

    public void deacquire() {
        setMotor(-Constants.INTAKE_MOTOR_SPEED);
    }

    public void stop() {
        setMotor(0);
    }

    public void setMotor(double speed) {
        motor.set(speed);
    }


}