package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.utils.NeoEncoder;

public class Chute extends SubsystemBase {

    private CANSparkMax chuteMotor;
    private NeoEncoder chuteEncoder;

    private DigitalInput lowerChuteSensor;
    private DigitalInput upperChuteSensor;

    public Chute() {
        chuteMotor = new CANSparkMax(Constants.CHUTE_LIFT_MOTOR_PORT, MotorType.kBrushless);

        chuteEncoder = new NeoEncoder(chuteMotor.getEncoder());

        lowerChuteSensor = new DigitalInput(Constants.CHUTE_LOWER_SENSOR_PORT);
        upperChuteSensor = new DigitalInput(Constants.CHUTE_UPPER_SENSOR_PORT);
    }

    public boolean getLowerChuteValue() {
        return lowerChuteSensor.get();
    }
    public boolean getUpperChuteValue() {
        return upperChuteSensor.get();
    }


    public void liftUp() {
        chuteMotor.set(Constants.CHUTE_LIFT_UP_SPEED);
    }

    public void liftDown() {
        chuteMotor.set(-Constants.CHUTE_LIFT_UP_SPEED);
    }

    public void stopChute() {
        chuteMotor.stopMotor();
    }

    public double getTicks() {
        return chuteEncoder.getPosition();
    }

    public void resetEncoder() {
        chuteEncoder.resetEncoder();
    }

    public int getRotations() {
        return (int) (getRawRotations());
    }

    private double getRawRotations() {
        return getTicks() / Constants.CHUTE_TICKS_PER_BALL;
    }

    public double getDistance() {
        return getRawRotations() * Constants.CHUTE_RADIUS * 2 * Math.PI;
    }


}