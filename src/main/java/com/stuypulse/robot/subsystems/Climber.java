package com.stuypulse.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.stuypulse.robot.Constants;

public class Climber extends SubsystemBase {
    
    private CANSparkMax liftMotor;
    private CANSparkMax yoyoMotor;

    private Solenoid liftSolenoid;


    public Climber() {
        liftMotor = new CANSparkMax(Constants.CLIMBER_LIFT_MOTOR_PORT, MotorType.kBrushless);
        yoyoMotor = new CANSparkMax(Constants.CLIMBER_YOYO_MOTOR_PORT, MotorType.kBrushless);
        liftSolenoid = new Solenoid(Constants.CLIMBER_LIFT_SOLENOID_CHANNEL);

        liftMotor.setInverted(true);
    }

    public void setNeutralMode(IdleMode mode) {
        liftMotor.setIdleMode(mode);
    }

    public void moveLiftDown() {
        moveLift(Constants.MOVE_LIFT_DOWN_SPEED);
    }

    public void moveLiftUp() {
        moveLift(Constants.MOVE_LIFT_UP_SPEED);
    }

    public void moveLiftDownSlow() {
        moveLift(-Constants.CLIMBER_MOVE_SLOW_SPEED);
    }

    public void moveLift(double speed) {
        liftMotor.set(speed);
    }

    public void moveYoyo(double speed) {
        yoyoMotor.set(speed);
    }

    public void stopClimber() {
        liftMotor.stopMotor();
    }

    public void stopYoyo() {
        yoyoMotor.stopMotor();
    }

    public void toggleLiftBrake() {
        liftSolenoid.set(!liftSolenoid.get());
    }

    public void enableLiftBrake() {
        if (liftSolenoid.get()) {
            liftSolenoid.set(false);
        }
    }

    public void releaseLiftBrake() {
        if (!liftSolenoid.get()) {
            liftSolenoid.set(true);
        }
    }

    public boolean isAtBottom() {
        return false;
        // return limitSwitch.get();
    }
}