package com.stuypulse.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.stuypulse.robot.Constants;

public class Chute extends SubsystemBase {

    // CANSpark Motor and Encoder
    private CANSparkMax chuteMotor;

    // IR Sensor
    private DigitalInput lowerChuteSensor;
    private DigitalInput upperChuteSensor;

    public Chute() {
        chuteMotor = new CANSparkMax(Constants.CHUTE_LIFT_MOTOR_PORT, MotorType.kBrushless);

        lowerChuteSensor = new DigitalInput(Constants.CHUTE_LOWER_SENSOR_PORT);
        upperChuteSensor = new DigitalInput(Constants.CHUTE_UPPER_SENSOR_PORT);
    }

    // IR SENSOR VALUES
    public boolean getLowerChuteValue() {
        return lowerChuteSensor.get();
    }
    public boolean getUpperChuteValue() {
        return upperChuteSensor.get();
    }

    // MOVE THE MOTORS
    public void liftUp() {
        chuteMotor.set(Constants.CHUTE_LIFT_UP_SPEED);
    }

    public void liftDown() {
        chuteMotor.set(-Constants.CHUTE_LIFT_UP_SPEED);
    }

    public void stopChute() {
        chuteMotor.stopMotor();
    }

}