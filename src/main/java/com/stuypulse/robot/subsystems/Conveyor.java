package com.stuypulse.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.stuypulse.robot.constants.Ports;
import com.stuypulse.robot.constants.Settings;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Conveyor extends SubsystemBase {
    private final CANSparkMax funnelMotor;
    private final CANSparkMax conveyorMotor;

    private DigitalInput lowerSensor;
    private DigitalInput upperSensor;

    public Conveyor() {
        funnelMotor = new CANSparkMax(Ports.Conveyor.FUNNEL, MotorType.kBrushless);
        conveyorMotor = new CANSparkMax(Ports.Conveyor.LIFT_MOTOR_PORT, MotorType.kBrushless);

        lowerSensor = new DigitalInput(Ports.Conveyor.LOWER_SENSOR_PORT);
        upperSensor = new DigitalInput(Ports.Conveyor.UPPER_SENSOR_PORT);

        conveyorMotor.setIdleMode(IdleMode.kCoast);

        // Add Children to Subsystem
        addChild("Lower Sensor", lowerSensor);
        addChild("Upper Sensor", upperSensor);
    }

    public void funnel() {
        funnelMotor.set(Settings.Conveyor.FUNNEL_SPEED);
    }

    public void unfunnel() {
        funnelMotor.set(Settings.Conveyor.UNFUNNEL_SPEED);
    }

    public void stopFunnel() {
        funnelMotor.stopMotor();
    }

    public boolean isAtBottomConveyor() {
        return lowerSensor.get();
    }

    public boolean isAtTopConveyor() {
        return !upperSensor.get();
    }

    // MOVE THE MOTORS
    public void liftUp() {
        conveyorMotor.set(Settings.Conveyor.LIFT_UP_SPEED);
    }

    public void liftDown() {
        conveyorMotor.set(-Settings.Conveyor.LIFT_UP_SPEED);
    }

    public void stopLift() {
        conveyorMotor.stopMotor();
    }

    @Override
    public void periodic() {
        // SmartDashboard

        if (Settings.DEBUG_MODE.get()) {
            SmartDashboard.putBoolean("Chimney/Upper Chimney Sensor", isAtTopConveyor());
            SmartDashboard.putBoolean("Chimney/Lower Chimney Sensor", isAtBottomConveyor());
        }
    }
}
