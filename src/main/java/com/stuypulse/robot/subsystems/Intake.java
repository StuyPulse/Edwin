package com.stuypulse.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.stuypulse.robot.Constants.Ports;
import com.stuypulse.robot.Constants.IntakeSettings;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {

    private final CANSparkMax motor;
    private final DoubleSolenoid solenoid;

    private DigitalInput sensor;

    public Intake() {
        motor = new CANSparkMax(Ports.Intake.MOTOR_PORT, MotorType.kBrushless);
        solenoid = new DoubleSolenoid(Ports.Intake.SOLENOID_PORT_A, Ports.Intake.SOLENOID_PORT_B);

        sensor = new DigitalInput(Ports.Intake.SENSOR_PORT);

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
        setMotor(IntakeSettings.MOTOR_SPEED);
    }

    public void deacquire() {
        setMotor(-IntakeSettings.MOTOR_SPEED);
    }

    public boolean isBallDetected() {
        return !sensor.get();
    }

    /************************
     * SENDABLE INFORMATION *
     ************************/

    @Override
    public void initSendable(SendableBuilder builder) {
        super.initSendable(builder);

        builder.addBooleanProperty(
            "Ball Detected", 
            () -> isBallDetected(), 
            (x) -> {});
    }

}