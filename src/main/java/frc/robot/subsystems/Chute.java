package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.utils.NeoEncoder;

public class Chute extends SubsystemBase {

    private static Chute instance;

    public static Chute getChute() {
        if (instance == null) {
            instance = new Chute();
        }
        return instance;
    }

    private CANSparkMax liftMotor;
    private CANSparkMax feederMotor;

    private NeoEncoder liftEncoder;

    private Chute() {
        liftMotor = new CANSparkMax(Constants.CHUTE_LIFT_MOTOR_PORT, MotorType.kBrushless);
        feederMotor = new CANSparkMax(Constants.CHUTE_FEEDER_MOTOR_PORT, MotorType.kBrushless);

        feederMotor.follow(liftMotor);

        liftEncoder = new NeoEncoder(new CANEncoder(liftMotor));
    }

    public void liftUp() {
        liftMotor.set(Constants.CHUTE_LIFT_UP_SPEED);
    }

    public void liftDown() {
        liftMotor.set(-Constants.CHUTE_LIFT_UP_SPEED);
    }

    public double getTicks() {
        return liftEncoder.getPosition();
    }

    public void resetEncoder() {
        liftEncoder.resetEncoder();
    }

    public int getRotations() {
        return (int) (getTicks() / Constants.CHUTE_TICKS_PER_REVOLUTION);
    }

    private double getRawRotations() {
        return getTicks() / Constants.CHUTE_TICKS_PER_REVOLUTION;
    }

    public double getDistance() {
        return getRawRotations() * Constants.CHUTE_RADIUS * 2 * Math.PI;
    }


}