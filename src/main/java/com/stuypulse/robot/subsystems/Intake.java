package com.stuypulse.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.stuypulse.robot.Constants;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {

    private final CANSparkMax motor;
    private final DoubleSolenoid solenoid;

    private DigitalInput sensor;

    public Intake() {
        motor = new CANSparkMax(Constants.INTAKE_MOTOR_PORT, MotorType.kBrushless);
        solenoid = new DoubleSolenoid(Constants.INTAKE_SOLENOID_PORT_A, Constants.INTAKE_SOLENOID_PORT_B);

        sensor = new DigitalInput(Constants.INTAKE_SENSOR_PORT);
        
        motor.setInverted(true);
    }

    public void extend() {
        // if (solenoid.get() == Value.kReverse) {
            solenoid.set(Value.kReverse);
        // }
    }

    public void retract() {
        // if (solenoid.get() == Value.kForward) {
            solenoid.set(Value.kForward);
        // }
    }

    // public void toggle() {
    //     if (solenoid.get() == Value.kForward) {
    //         retract();
    //     } else {
    //         extend();
    //     }
    // }

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

    public boolean isBallDetected() {
        return sensor.get();
    }

}