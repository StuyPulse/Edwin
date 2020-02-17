package com.stuypulse.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.stuypulse.robot.Constants;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {

    private final CANSparkMax motor;
    private final Solenoid solenoid;

    public Intake() {
        motor = new CANSparkMax(Constants.INTAKE_MOTOR_PORT, MotorType.kBrushless);
        solenoid = new Solenoid(3);

        motor.setInverted(true);
    }

    public void extend() {
        if (solenoid.get()) {
            solenoid.set(false);
        }
    }

    public void retract() {
        if (!solenoid.get()) {
            solenoid.set(true);
        }
    }

    public void toggle() {
        if (solenoid.get()) {
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
        motor.stopMotor();
    }

    public void setMotor(final double speed) {
        motor.set(speed);
    }


}