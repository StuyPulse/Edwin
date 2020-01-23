package frc.robot.subsystems;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import frc.robot.subsystems.Chute;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Chute extends SubsystemBase {
    private CANSparkMax liftMotor;
    private CANSparkMax speedMotor;

    public Chute() {
        liftMotor = new CANSparkMax(Constants.CHUTE_LIFT_MOTOR_PORT, MotorType.kBrushless);
        speedMotor = new CANSparkMax(Constants.CHUTE_SPEED_MOTOR_PORT, MotorType.kBrushless);
    }

    public void liftUp() {
        liftMotor.set(Constants.CHUTE_LIFT_UP_SPEED);
    }

    public void liftDown() {
        liftMotor.set(-Constants.CHUTE_LIFT_UP_SPEED);
    }

    public void speedUp() {
        speedMotor.set(Constants.CHUTE_ACCEL_UP_SPEED);
    }

    public void speedDown() {
        speedMotor.set(-Constants.CHUTE_ACCEL_UP_SPEED);
    }
}