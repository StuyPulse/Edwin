package com.stuypulse.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.stuypulse.robot.Constants.Ports;
import com.stuypulse.robot.Constants.IntakeSettings;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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

        // Add Children to Subsystem
        addChild("Double Solenoid", solenoid);
        addChild("Sensor", sensor);
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

    @Override
    public void periodic() {
        // SmartDashboard
        SmartDashboard.putBoolean("Intake/Ball Detected", isBallDetected());
    }
}