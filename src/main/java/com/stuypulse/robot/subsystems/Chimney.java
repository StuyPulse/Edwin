package com.stuypulse.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.stuypulse.robot.Constants;

public class Chimney extends SubsystemBase {

    public enum Mode {
        NONE,
        ACQUIRED_ONE_CELL,
        HOLDING_MAX_CELLS
    }

    // CANSpark Motor and Encoder
    private CANSparkMax motor;

    // IR Sensor
    private DigitalInput lowerSensor;
    private DigitalInput upperSensor;

    private Mode mode = Mode.NONE;

    public Chimney() {
        motor = new CANSparkMax(Constants.CHIMNEY_LIFT_MOTOR_PORT, MotorType.kBrushless);

        lowerSensor = new DigitalInput(Constants.CHIMNEY_LOWER_SENSOR_PORT);
        upperSensor = new DigitalInput(Constants.CHIMNEY_UPPER_SENSOR_PORT);
    }

    // IR SENSOR VALUES
    public boolean getLowerChimneyValue() {
        return lowerSensor.get();
    }
    public boolean getUpperChimneyValue() {
        return upperSensor.get();
    }

    // MOVE THE MOTORS
    public void liftUp() {
        motor.set(Constants.CHIMNEY_LIFT_UP_SPEED);
    }

    public void liftDown() {
        motor.set(-Constants.CHIMNEY_LIFT_UP_SPEED);
    }

    public void stop() {
        motor.stopMotor();
    }

    public void setChimneyMode(Mode mode) {
        this.mode = mode;
    }

    public Mode getChimneyMode() {
        return mode;
    }

}