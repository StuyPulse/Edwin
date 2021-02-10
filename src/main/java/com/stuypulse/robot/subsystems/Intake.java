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

    /*** Extend / Retract ***/
    public void extend() {
        solenoid.set(Value.kReverse);
    }

    public void retract() {
        solenoid.set(Value.kForward);
    }

    /*** Extend / Retract ***/
    public void setMotor(final double speed) {
        motor.set(speed);
    }

    public void stop() {
        motor.stopMotor();
    }

    public void acquire() {
        setMotor(Constants.INTAKE_MOTOR_SPEED);
    }

    public void deacquire() {
        setMotor(-Constants.INTAKE_MOTOR_SPEED);
    }

    public boolean isBallDetected() {
        return !sensor.get();
    }

}