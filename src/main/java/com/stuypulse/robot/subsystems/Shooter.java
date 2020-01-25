package main.java.com.stuypulse.robot.subsystems;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.stuypulse.robot.Constants;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Shooter extends SubsystemBase {

    // CANSPARKMAX //
    private final CANSparkMax shooterMotor, altShooterMotor;
    private final CANSparkMax feederMotor;
 
    // SPEED CONTROLLER GROUP //
    private final SpeedControllerGroup shooterMotors; // use differential drive??

    public Shooter() {
        shooterMotor = new CANSparkMax(Constants.SHOOTER_SHOOTER_MOTOR, MotorType.kBrushless);
        altShooterMotor = new CANSparkMax(Constants.SHOOTER_ALT_SHOOTER_MOTOR, MotorType.kBrushless);
        feederMotor = new CANSparkMax(Constants.SHOOTER_FEEDER_MOTOR, MotorType.kBrushless);

        shooterMotor.setInverted(true);
        altShooterMotor.setInverted(true);
        feederMotor.setInverted(true);

        shooterMotor.enableBreakMode(false);
        altShooterMotor.enableBreakMode(false);
        feederMotor.enableBreakMode(false);

        shooterMotors = new SpeedControllerGroup(shooterMotor,altShooterMotor);
    }

    public void runShooter(double speed) {
        shooterMotors.set(speed);
    }

    public void stopShooter() {
        shooterMotors.stopMotor();
    }

    public void runFeeder(double speed) {
        feederMotor.set(speed);
    }
    
    public void stopFeeder() {
        feederMotor.stopMotor();
    }

    public double getShooterSpeed() {
        return shooterMotors.get();
    }

    public double getFeederSpeed() {
        return feederMotor.get();
    }

}