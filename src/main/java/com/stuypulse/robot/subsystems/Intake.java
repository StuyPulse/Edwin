package com.stuypulse.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.stuypulse.robot.Constants;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class Intake extends SubsystemBase {

    private CANSparkMax intakeMotor;
    private Solenoid intakeSolenoid;
  
    public Intake() {
        intakeMotor = new CANSparkMax(Constants.INTAKE_MOTOR_PORT, MotorType.kBrushless);
        intakeSolenoid = new Solenoid(Constants.INTAKE_SOLENOID_PORT);
    }

    public boolean isExtended() {
        return intakeSolenoid.get();
    }

    public void extend() {
        if (!intakeSolenoid.get()) {
            intakeSolenoid.set(true);
        }
    }

    public void retract() {
        if (intakeSolenoid.get()) {
            intakeSolenoid.set(false);
        }
    }

    public void acquire() {
        intakeMotor.set(1);
    }

    public void deacquire() {
        intakeMotor.set(-1);
    }

    public void setMotor(double speed) {
        intakeMotor.set(speed);
    }
}