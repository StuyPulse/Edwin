package com.stuypulse.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.stuypulse.robot.Constants;

public class Chimney extends SubsystemBase {

    // CANSpark Motor and Encoder
    private CANSparkMax motor;

    // IR Sensor
    private DigitalInput lowerSensor;
    private DigitalInput upperSensor;

    public Chimney() {
        motor = new CANSparkMax(Constants.CHUTE_LIFT_MOTOR_PORT, MotorType.kBrushless);

        lowerSensor = new DigitalInput(Constants.CHUTE_LOWER_SENSOR_PORT);
        upperSensor = new DigitalInput(Constants.CHUTE_UPPER_SENSOR_PORT);
    }

    // IR SENSOR VALUES
    public boolean getLowerChuteValue() {
        return lowerSensor.get();
    }
    public boolean getUpperChuteValue() {
        return upperSensor.get();
    }

    // MOVE THE MOTORS
    public void liftUp() {
        motor.set(Constants.CHUTE_LIFT_UP_SPEED);
    }

    public void liftDown() {
        motor.set(-Constants.CHUTE_LIFT_UP_SPEED);
    }

    public void stop() {
        motor.stopMotor();
    }

}