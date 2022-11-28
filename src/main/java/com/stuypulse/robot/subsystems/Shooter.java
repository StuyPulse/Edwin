package com.stuypulse.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import static com.stuypulse.robot.constants.Ports.Shooter.*;
import static com.stuypulse.robot.constants.Settings.Shooter.*;
import com.stuypulse.stuylib.control.Controller;
import com.stuypulse.stuylib.control.feedback.PIDController;
import com.stuypulse.stuylib.control.feedforward.Feedforward;
import com.stuypulse.stuylib.network.SmartNumber;
import com.stuypulse.stuylib.streams.filters.IFilter;
import com.stuypulse.stuylib.streams.filters.LowPassFilter;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * @author Ivan Chen (@anivanchen)
 */
public class Shooter extends SubsystemBase {

    private final CANSparkMax shooterA, shooterB, shooterC, feeder;
    private final RelativeEncoder shooterAEncoder, shooterBEncoder, shooterCEncoder, feederEncoder;

    private final Solenoid hood;

    private final Controller shooterController, feederController;

    private final SmartNumber targetRPM;
    private final IFilter targetFilter;

    public Shooter() {
        shooterA = new CANSparkMax(SHOOTER_A, MotorType.kBrushless);
        shooterB = new CANSparkMax(SHOOTER_B, MotorType.kBrushless);
        shooterC = new CANSparkMax(SHOOTER_C, MotorType.kBrushless);
        feeder = new CANSparkMax(FEEDER, MotorType.kBrushless);

        shooterAEncoder = shooterA.getEncoder();
        shooterBEncoder = shooterB.getEncoder();
        shooterCEncoder = shooterC.getEncoder();
        feederEncoder = feeder.getEncoder();

        hood = new Solenoid(PneumaticsModuleType.CTREPCM, HOOD);

        shooterController = new Feedforward.Flywheel(ShooterFF.S, ShooterFF.V, ShooterFF.A).velocity()
                .add(new PIDController(ShooterFB.P, ShooterFB.I, ShooterFB.D));
        feederController = new Feedforward.Flywheel(FeederFF.S, FeederFF.V, FeederFF.A).velocity()
                .add(new PIDController(FeederFB.P, FeederFB.I, FeederFB.D));

        targetRPM = new SmartNumber("Shooter/Target RPM", 0);
        targetFilter = new LowPassFilter(CHANGE_RC);

        hood.set(false);
    }

    public double getRawTargetRPM() {
        return targetRPM.get();
    }

    public double getTargetRPM() {
        return targetFilter.get(getRawTargetRPM());
    }

    public double getShooterRPM() {
        return (Math.abs(shooterAEncoder.getVelocity()) + shooterBEncoder.getVelocity() + shooterCEncoder.getVelocity())
                / 3;
    }

    public double getFeederRPM() {
        return feederEncoder.getVelocity();
    }

    public boolean isReady() {
        return shooterController.isDone(TOLERANCE.get()) && feederController.isDone(TOLERANCE.get());
    }

    public void setTargetRPM(Number target) {
        targetRPM.set(target);
    }

    public void runShooter(double voltage) {
        shooterA.setVoltage(voltage);
        shooterB.setVoltage(voltage);
        shooterC.setVoltage(voltage);
    }

    public void runFeeder(double voltage) {
        feeder.setVoltage(voltage * FEEDER_MULTIPLIER);
    }

    public void stop() {
        shooterA.stopMotor();
        shooterB.stopMotor();
        shooterC.stopMotor();
        feeder.stopMotor();
    }

    public void extendHood() {
        hood.set(true);
    }

    public void retractHood() {
        hood.set(false);
    }

    @Override
    public void periodic() {
        if (getTargetRPM() > MIN_RPM.get()) {
            runShooter(shooterController.update(getTargetRPM(), getShooterRPM()));
            runFeeder(feederController.update(getTargetRPM(), getFeederRPM()));
        } else {
            stop();
        }

        SmartDashboard.putNumber("Shooter/Shooter RPM", getShooterRPM());
        SmartDashboard.putNumber("Shooter/Feeder RPM", getFeederRPM());
    }
}
