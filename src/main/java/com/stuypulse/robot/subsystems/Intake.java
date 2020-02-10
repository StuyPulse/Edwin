package com.stuypulse.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.stuypulse.robot.Constants;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class Intake extends SubsystemBase {
    
    private CANSparkMax motor;
    private Solenoid solenoid;
    
    private boolean extended;

    public Intake() {
        motor = new CANSparkMax(Constants.INTAKE_MOTOR_PORT, MotorType.kBrushless);
        solenoid = new Solenoid(Constants.INTAKE_SOLENOID_PORT);
    }

    public boolean isExtended() {
        return solenoid.get();
    }

    public void extend() {
        if(!extended) {
            solenoid.set(true);
            extended = true;
        }
    }

    public void retract() {
        if(extended) {
            solenoid.set(false);
            extended = false;
        }
    }
    public void acquire() {
        setMotor(1);
    }

    public void deacquire() {
        setMotor(-1);
    }

    public void stop() {
        setMotor(0);
    }

    public void setMotor(double speed) {
        motor.set(speed);
    }


}