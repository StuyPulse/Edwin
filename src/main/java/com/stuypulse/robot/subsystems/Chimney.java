package com.stuypulse.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.stuypulse.robot.Constants;
import com.stuypulse.robot.util.BrownoutProtection;

public class Chimney extends SubsystemBase implements BrownoutProtection{

    // CANSpark Motor and Encoder
    private CANSparkMax motor;

    // IR Sensor
    private DigitalInput lowerSensor;
    private DigitalInput upperSensor;

    public Chimney() {
        motor = new CANSparkMax(Constants.CHIMNEY_LIFT_MOTOR_PORT, MotorType.kBrushless);

        lowerSensor = new DigitalInput(Constants.CHIMNEY_LOWER_SENSOR_PORT);
        upperSensor = new DigitalInput(Constants.CHIMNEY_UPPER_SENSOR_PORT);
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
        motor.set(Constants.CHIMNEY_LIFT_UP_SPEED);
    }

    public void liftDown() {
        motor.set(-Constants.CHIMNEY_LIFT_UP_SPEED);
    }

    public void stop() {
        motor.stopMotor();
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