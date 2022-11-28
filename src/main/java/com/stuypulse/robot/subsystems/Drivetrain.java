package com.stuypulse.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import static com.stuypulse.robot.constants.Ports.Drivetrain.*;
import static com.stuypulse.robot.constants.Settings.Drivetrain.*;
import static com.stuypulse.robot.constants.Motors.Drivetrain.*;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drivetrain extends SubsystemBase {
    
    private final CANSparkMax[] left;
    private final CANSparkMax[] right;
    
    private final RelativeEncoder leftEncoder;
    private final RelativeEncoder rightEncoder;

    // private final Controller leftController, rightController;
    // private SmartNumber leftTargetSpeed, rightTargetSpeed;

    private final DifferentialDrive drivetrain;

    private final Solenoid gearShift;
    
    private final AHRS navx;

    private final DifferentialDriveOdometry odometry;
    private final DifferentialDriveKinematics kinematics;

    private final Field2d field;

    public Drivetrain() {
        left = new CANSparkMax[] {
            new CANSparkMax(LEFT_TOP, MotorType.kBrushless), 
            new CANSparkMax(LEFT_BOTTOM, MotorType.kBrushless)
        };

        right = new CANSparkMax[] {
            new CANSparkMax(RIGHT_TOP, MotorType.kBrushless), 
            new CANSparkMax(RIGHT_BOTTOM, MotorType.kBrushless)
        };

        leftEncoder = left[0].getEncoder();
        rightEncoder = right[0].getEncoder();

        configMotors();
        
        // leftController = new Feedforward.Drivetrain(Motion.Feedforward.kS, Motion.Feedforward.kV, Motion.Feedforward.kA).velocity()
        //     .add(new PIDController(Motion.PID.kP, Motion.PID.kI, Motion.PID.kD));
        
        // rightController = new Feedforward.Drivetrain(Motion.Feedforward.kS, Motion.Feedforward.kV, Motion.Feedforward.kA).velocity()
        //     .add(new PIDController(Motion.PID.kP, Motion.PID.kI, Motion.PID.kD));

        // leftTargetSpeed = new SmartNumber("Edwin/Left Target Speed", 0);
        // rightTargetSpeed = new SmartNumber("Edwin/Right TargetSpeed", 0);

        drivetrain = new DifferentialDrive(new MotorControllerGroup(left), new MotorControllerGroup(right));

        gearShift = new Solenoid(PneumaticsModuleType.CTREPCM, GEAR_SHIFT);
        gearShift.set(true);

        navx = new AHRS(SPI.Port.kMXP);

        odometry = new DifferentialDriveOdometry(getRotation2d(), 0, 0);
        kinematics = new DifferentialDriveKinematics(TRACK_WIDTH);

        setPose(new Pose2d());

        field = new Field2d();
        SmartDashboard.putData("Edwin/Field", field);
    }

    private void configMotors() {

        leftEncoder.setVelocityConversionFactor(Encoders.HIGH_GEAR_DISTANCE_PER_ROTATION / 60);
        leftEncoder.setPositionConversionFactor(Encoders.HIGH_GEAR_DISTANCE_PER_ROTATION);
        rightEncoder.setVelocityConversionFactor(Encoders.HIGH_GEAR_DISTANCE_PER_ROTATION / 60);
        rightEncoder.setPositionConversionFactor(Encoders.HIGH_GEAR_DISTANCE_PER_ROTATION);

        for (CANSparkMax motor : left) {
            LEFT.configure(motor);
        }

        for (CANSparkMax motor : right) {
            RIGHT.configure(motor);
        }
    }

    /*********************
     * ENCODER FUNCTIONS *
     *********************/

    // Distance
    private double getLeftDistance() {
        return leftEncoder.getPosition();
    }

    private double getRightDistance() {
        return rightEncoder.getPosition();
    }

    // Velocity
    private double getLeftVelocity() {
        return leftEncoder.getVelocity();
    }

    private double getRightVelocity() {
        return rightEncoder.getVelocity();
    }

    /***************
     * ROBOT ANGLE *
     ***************/

    // Gets current Angle of the Robot as a Rotation2d (contiuous / not +-180)
    public Rotation2d getRotation2d() {
        return navx.getRotation2d();
    }

    /**********************
     * ODOMETRY FUNCTIONS *
     **********************/

    public Pose2d getPose() {
        return odometry.getPoseMeters();
    }

    public void setPose(Pose2d pose) {
        leftEncoder.setPosition(0.);
        rightEncoder.setPosition(0.);

        odometry.resetPosition(getRotation2d(), 0, 0, pose);
    }

    public DifferentialDriveKinematics getKinematics() {
        return kinematics;
    }

    public TrajectoryConfig getTrajectoryConfig() {
        return new TrajectoryConfig(
            Motion.MAX_VELOCITY, 
            Motion.MAX_ACCELERATION
        ).setKinematics(kinematics);
    }

    /********************
     * DRIVING COMMANDS *
     ********************/

    public void stop() {
        drivetrain.stopMotor();
        driveVolts(0, 0);
    }

    public void tankDrive(double leftMetersPerSecond, double rightMetersPerSecond) {
        drivetrain.tankDrive(leftMetersPerSecond, rightMetersPerSecond);
    }

    public void arcadeDrive(double speed, double angle) {
        drivetrain.arcadeDrive(speed, angle);
    }

    private void driveVolts(double leftVolts, double rightVolts) {
        for (CANSparkMax motor : left) {
            motor.setVoltage(leftVolts);
        }

        for (CANSparkMax motor : right) {
            motor.setVoltage(rightVolts);
        }
    }

    @Override
    public void periodic() {
        odometry.update(getRotation2d(), getLeftDistance(), getRightDistance());
        field.setRobotPose(getPose());

        // LOGGING

        SmartDashboard.putNumber("Edwin/Left Distance", getLeftDistance());
        SmartDashboard.putNumber("Edwin/Right Distance", getRightDistance());

        SmartDashboard.putNumber("Edwin/Left Vel", getLeftVelocity());
        SmartDashboard.putNumber("Edwin/Right Vel", getRightVelocity());

        SmartDashboard.putNumber("Edwin/Pose X", getPose().getX());
        SmartDashboard.putNumber("Edwin/Pose Y", getPose().getY());
        SmartDashboard.putNumber("Edwin/Gyro Angle", getRotation2d().getDegrees());
    }

    public Field2d getField2d() {
        return field;
    }
}