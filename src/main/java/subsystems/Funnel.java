package subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Funnel extends SubsystemBase {

    private CANSparkMax motor;

    public Funnel() {
        motor = new CANSparkMax(Constants.FUNNEL_MOTOR_PORT, MotorType.kBrushless);
    }

    public void funnel() {
        motor.set(1.0);
    }

    public void unfunnnel() {  
        motor.set(-1.0);
    }

}