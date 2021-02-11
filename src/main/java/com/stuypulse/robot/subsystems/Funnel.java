package com.stuypulse.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.stuypulse.robot.Constants.Ports;
import com.stuypulse.robot.Constants.FunnelSettings;

import com.stuypulse.robot.util.MotorStall;

// TODO: Figure out what MoterStall is supposed to do, and if we need it
public class Funnel extends SubsystemBase implements MotorStall {

    private final CANSparkMax motor;
    private final CANEncoder encoder;

    private boolean isStalled;
    private double startEncoderVal;
    private int stallCounter;

    private boolean isRunning;

    public Funnel() {
        motor = new CANSparkMax(Ports.FUNNEL, MotorType.kBrushless);
        encoder = motor.getEncoder();
    }

    public void funnel() {
        motor.set(FunnelSettings.FUNNEL_SPEED);
    }

    public void unfunnel() {
        motor.set(FunnelSettings.UNFUNNEL_SPEED);
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
        return FunnelSettings.ENCODER_APPROACH_STALL_THRESHOLD;
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

    /************************
     * SENDABLE INFORMATION *
     ************************/

    @Override
    public void initSendable(SendableBuilder builder) {
        super.initSendable(builder);
    }

}
