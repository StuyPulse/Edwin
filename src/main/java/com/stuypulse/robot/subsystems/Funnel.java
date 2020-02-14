package com.stuypulse.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.stuypulse.robot.Constants;
import com.stuypulse.robot.util.MotorStall;

public class Funnel extends SubsystemBase implements MotorStall, BrownoutProtection {

    private final CANSparkMax motor;
    private final CANEncoder encoder;

    private boolean isStalled;
    private double startEncoderVal;
    private int stallCounter;

    private boolean isRunning;

    public Funnel() {
        motor = new CANSparkMax(Constants.FUNNEL_MOTOR_PORT, MotorType.kBrushless);
        encoder = motor.getEncoder();
    }

    public void funnel() {
        motor.set(Constants.FUNNEL_SPEED);
    }

    public void unfunnel() {
        motor.set(Constants.UNFUNNEL_SPEED);
    }

    public void stop() {
        motor.stopMotor();
    }

    @Override
    public void setStalled(boolean value) {
        isStalled = value;
    }

    @Override
    public boolean isStalling() {
        return isStalled;
    }

    @Override
    public double getEncoderApproachStallThreshold() {
        return Constants.FUNNEL_ENCODER_APPROACH_STALL_THRESHOLD;
    }

    @Override
    public void setStartEncoderVal(double val) {
        startEncoderVal = val;
    }

    @Override
    public double getStartEncoderVal() {
        return startEncoderVal;
    }

    @Override
    public double getCurrentEncoderVal() {
        return encoder.getPosition();
    }

    @Override
    public int getStallCounter() {
        return stallCounter;
    }

    @Override
    public void incrementStallCounter() {
        stallCounter++;
    }

    @Override
    public void resetStallCounter() {
        stallCounter = 0;
    }

    @Override
    public boolean isRunning() {
        return isRunning;
    }

    @Override
    public void setRunning(boolean val) {
        isRunning = val;
    }

    @Override
    public void enableBrownout() {
        motor.setSmartCurrentLimit(Constants.CURRENT_LIMIT); 
    }

    @Override
    public void disableBrownout() {
        motor.setSmartCurrentLimit(0);
    }
    
}
