package com.stuypulse.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.stuypulse.robot.Constants;

public class Chimney extends SubsystemBase {

    // CANSpark Motor and Encoder
    private CANSparkMax motor;

    // IR Sensor
    private DigitalInput lowerSensor;
    private DigitalInput upperSensor;

    public Chimney() {
        motor = new CANSparkMax(Constants.CHIMNEY_LIFT_MOTOR_PORT, MotorType.kBrushless);

        lowerSensor = new DigitalInput(Constants.CHIMNEY_LOWER_SENSOR_PORT);
        upperSensor = new DigitalInput(Constants.CHIMNEY_UPPER_SENSOR_PORT);

        motor.setIdleMode(IdleMode.kCoast);
    }

    // IR SENSOR VALUES
    public boolean getLowerChimneyValue() {
        return lowerSensor.get();
    }
    public boolean getUpperChimneyValue() {
        return !upperSensor.get();
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

    // SEND VALUES TO SMART DASHBOARD
    @Override
    public void initSendable(SendableBuilder builder) {
        super.initSendable(builder);

        builder.addBooleanProperty(
            "Upper Chimney Sensor", 
            () -> getUpperChimneyValue(), 
            (x) -> {});

        builder.addBooleanProperty(
            "Lower Chimney Sensor", 
            () -> getLowerChimneyValue(), 
            (x) -> {});
    }
}