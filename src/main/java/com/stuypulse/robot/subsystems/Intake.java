package com.stuypulse.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.stuypulse.robot.Constants;
import com.stuypulse.robot.util.FRCLogger.Loggable;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class Intake extends SubsystemBase implements Loggable {
    private CANSparkMax intakeMotor;
    private Solenoid intakeSolenoid;
    private String stateChanged;
  
    public Intake() {
        intakeMotor = new CANSparkMax(Constants.INTAKE_MOTOR_PORT, MotorType.kBrushless);
        intakeSolenoid = new Solenoid(Constants.INTAKE_SOLENOID_PORT);
        stateChanged = "none";
    }

    public void extend() {
        intakeSolenoid.set(true);
        stateChanged = "Extending";
    }

    public void retract() {
        intakeSolenoid.set(false);
        stateChanged = "Retracting";
    }

    public void acquire() {
        intakeMotor.set(1);
        stateChanged = "Acquiring";
    }

    public void deacquire() {
        intakeMotor.set(-1);
        stateChanged = "Deacquiring";
    }

    public void setMotor(double speed) {
        intakeMotor.set(speed);
    }

    public boolean logThisIteration() {
        if(!stateChanged.equals("none")) {
            stateChanged = "none";
            return true;
        }
        return false;
    }

    public String getLogData() {
        return "State changed to: " + stateChanged;
    }
}