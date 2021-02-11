package com.stuypulse.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.stuypulse.robot.Constants.Ports;
import com.stuypulse.robot.Constants.ShooterSettings;
import com.stuypulse.stuylib.control.Controller;
import com.stuypulse.stuylib.control.PIDController;
import com.stuypulse.stuylib.streams.filters.IFilter;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.stuypulse.stuylib.math.SLMath;

public class Shooter extends SubsystemBase {

    public static final IFilter INTEGRAL_FILTER = (x) -> SLMath.limit(x, ShooterSettings.I_LIMIT.doubleValue());

    public enum ShooterMode {
        NONE, SHOOT_FROM_INITIATION_LINE, SHOOT_FROM_TRENCH, SHOOT_FROM_FAR
    };

    // Motors
    private final CANSparkMax leftShooterMotor;
    private final CANSparkMax rightShooterMotor;
    private final CANSparkMax middleShooterMotor;
    private final CANSparkMax feederMotor;

    // Encoders
    private final CANEncoder leftShooterEncoder;
    private final CANEncoder rightShooterEncoder;
    private final CANEncoder middleShooterEncoder;
    private CANEncoder feederEncoder;

    // Hood Solenoid
    private final Solenoid hoodSolenoid;

    // SpeedControllerGroup
    private final SpeedControllerGroup shooterMotors;

    // Target RPM
    private double targetRPM;

    private Controller shooterController;
    private Controller feederController;

    private ShooterMode mode = ShooterMode.NONE;

    public Shooter() {
        // Shooter Stuff
        leftShooterMotor = new CANSparkMax(Ports.Shooter.LEFT, MotorType.kBrushless);
        rightShooterMotor = new CANSparkMax(Ports.Shooter.RIGHT, MotorType.kBrushless);
        middleShooterMotor = new CANSparkMax(Ports.Shooter.MIDDLE, MotorType.kBrushless);

        leftShooterMotor.setInverted(true);

        leftShooterEncoder = leftShooterMotor.getEncoder();
        rightShooterEncoder = rightShooterMotor.getEncoder();
        middleShooterEncoder = middleShooterMotor.getEncoder();

        shooterMotors = new SpeedControllerGroup(leftShooterMotor, rightShooterMotor, middleShooterMotor);

        // Feeder Stuff
        feederMotor = new CANSparkMax(Ports.Shooter.FEEDER, MotorType.kBrushless);

        feederMotor.setInverted(true);
        feederEncoder = feederMotor.getEncoder();

        // PID Stuff
        shooterController = new PIDController(ShooterSettings.Shooter.P, ShooterSettings.Shooter.I,
                ShooterSettings.Shooter.D).setIntegratorFilter(INTEGRAL_FILTER);

        feederController = new PIDController(ShooterSettings.Feeder.P, ShooterSettings.Feeder.I,
                ShooterSettings.Feeder.D).setIntegratorFilter(INTEGRAL_FILTER);

        // Hood Stuff
        hoodSolenoid = new Solenoid(Ports.Shooter.HOOD_SOLENOID);

        // Setting Modes Stuff
        rightShooterMotor.setIdleMode(IdleMode.kCoast);
        leftShooterMotor.setIdleMode(IdleMode.kCoast);
        middleShooterMotor.setIdleMode(IdleMode.kCoast);

        feederMotor.setIdleMode(IdleMode.kCoast);

        rightShooterMotor.setSmartCurrentLimit(ShooterSettings.CURRENT_LIMIT);
        leftShooterMotor.setSmartCurrentLimit(ShooterSettings.CURRENT_LIMIT);
        middleShooterMotor.setSmartCurrentLimit(ShooterSettings.CURRENT_LIMIT);

        feederMotor.setSmartCurrentLimit(ShooterSettings.CURRENT_LIMIT);
    }

    /************
     * SHOOTING *
     ************/

    public double getShooterRPM() {
        return (leftShooterEncoder.getVelocity() + middleShooterEncoder.getVelocity()
                + rightShooterEncoder.getVelocity()) / 3.0;
    }

    public double getFeederRPM() {
        return feederEncoder.getVelocity();
    }

    public void setTargetRPM(double target) {
        this.targetRPM = target;
    }

    public boolean isReady() {
        return shooterController.isDone(ShooterSettings.TOLERANCE)
                && feederController.isDone(ShooterSettings.TOLERANCE);
    }

    public void stop() {
        shooterMotors.stopMotor();
        feederMotor.stopMotor();
    }

    @Override
    public void periodic() {
        if (targetRPM > 100) {
            double shootSpeed = shooterController.update(targetRPM, getShooterRPM());
            shootSpeed += targetRPM * ShooterSettings.Shooter.FF.get();

            double feederSpeed = feederController.update(targetRPM, getFeederRPM());
            feederSpeed += targetRPM * ShooterSettings.Feeder.FF.get();

            shooterMotors.set(shootSpeed);
            feederMotor.set(feederSpeed);
        } else {
            stop();
        }

    }

    /********
     * HOOD *
     ********/

    public void extendHoodSolenoid() {
        hoodSolenoid.set(true);
    }

    public void retractHoodSolenoid() {
        hoodSolenoid.set(false);
    }

    public void setDefaultSolenoidPosition() {
        retractHoodSolenoid();
    }

    /****************
     * SHOOTER MODE *
     ****************/

    public void setShooterMode(ShooterMode mode) {
        this.mode = mode;
    }

    public ShooterMode getShooterMode() {
        return mode;
    }

    /************************
     * SENDABLE INFORMATION *
     ************************/

    public void initSendable(SendableBuilder builder) {
        super.initSendable(builder);

        builder.addDoubleProperty(
            "Target RPM", 
            () -> targetRPM, 
            (x) -> setTargetRPM(x));

        builder.addDoubleProperty(
            "Shooter RPM", 
            () -> getShooterRPM(), 
            (x) -> {});

        builder.addDoubleProperty(
            "Feeder RPM", 
            () -> getFeederRPM(), 
            (x) -> {});

        builder.addBooleanProperty(
            "Hood Extended", 
            () -> hoodSolenoid.get(), 
            (x) -> hoodSolenoid.set(x));
    }
}