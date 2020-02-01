package com.stuypulse.robot.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.SPI;

import com.kauailabs.navx.frc.AHRS;
import com.stuypulse.robot.Constants;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Drivetrain extends SubsystemBase {

    //LEFT SIDE MOTORS
    private CANSparkMax leftTopMotor;
    private CANSparkMax leftMiddleMotor;
    private CANSparkMax leftBottomMotor;
    
    //RIGHT SIDE MOTORS
    private CANSparkMax rightTopMotor;
    private CANSparkMax rightMiddleMotor;
    private CANSparkMax rightBottomMotor;

    //Speed Controller Groups
    private SpeedControllerGroup leftMotors;
    private SpeedControllerGroup rightMotors;

    private DifferentialDrive drivetrain;

    //Sensors
    private Encoder leftEncoder;
    private Encoder rightEncoder;

    private AHRS gyro;

    public Drivetrain() {
        //LEFT SIDE MOTORS
        leftTopMotor = new CANSparkMax(Constants.DRIVETRAIN_LEFT_TOP_MOTOR_PORT, MotorType.kBrushless);
        leftMiddleMotor = new CANSparkMax(Constants.DRIVETRAIN_LEFT_MIDDLE_MOTOR_PORT, MotorType.kBrushless);
        leftBottomMotor = new CANSparkMax(Constants.DRIVETRAIN_LEFT_BOTTOM_MOTOR_PORT, MotorType.kBrushless);

        //RIGHT SIDE MOTORS
        rightTopMotor = new CANSparkMax(Constants.DRIVETRAIN_RIGHT_TOP_MOTOR_PORT, MotorType.kBrushless);
        rightMiddleMotor = new CANSparkMax(Constants.DRIVETRAIN_RIGHT_MIDDLE_MOTOR_PORT, MotorType.kBrushless);
        rightBottomMotor = new CANSparkMax(Constants.DRIVETRAIN_RIGHT_BOTTOM_MOTOR_PORT, MotorType.kBrushless);

        //Speed Controller Groups
        leftMotors = new SpeedControllerGroup(leftTopMotor, leftMiddleMotor, leftBottomMotor);
        rightMotors = new SpeedControllerGroup(rightTopMotor, rightMiddleMotor, rightBottomMotor);

        //Differential Drive Object
        drivetrain = new DifferentialDrive(leftMotors, rightMotors);

        //Encoders
        leftEncoder = new Encoder(Constants.DRIVETRAIN_LEFT_ENCODER_CHANNEL_A, Constants.DRIVETRAIN_LEFT_ENCODER_CHANNEL_B);
        rightEncoder = new Encoder(Constants.DRIVETRAIN_RIGHT_ENCODER_CHANNEL_A, Constants.DRIVETRAIN_RIGHT_ENCODER_CHANNEL_B);

        leftEncoder.setDistancePerPulse(Constants.GREYHILL_INCHES_PER_PULSE);
        rightEncoder.setDistancePerPulse(Constants.GREYHILL_INCHES_PER_PULSE);

        //Gyro
        gyro = new AHRS(SPI.Port.kMXP);
    }

    public void tankDrive(double left, double right) {
        drivetrain.tankDrive(left, right);
    }

    public void arcadeDrive(double speed, double rotation) {
        drivetrain.arcadeDrive(speed, rotation);
    }

    public void curvatureDrive(double speed, double rotation, boolean quickturn) {
        drivetrain.curvatureDrive(speed, rotation, quickturn);
    }

    public double getLeftEncoderTicks() {
        return leftEncoder.get();
    }

    public double getRighttEncoderTicks() {
        return rightEncoder.get();
    }

    public double getLeftEncoderDistance() {
        return leftEncoder.getDistance();
    }

    public double getRighttEncoderDistance() {
        return rightEncoder.getDistance();
    }

    public double getEncoderDistance() {
        return Math.max(getLeftEncoderDistance(), getRighttEncoderDistance());
    }

    public void resetEncoders() {
        leftEncoder.reset();
        rightEncoder.reset();
    }

    public double getGyroAngle() {
        return gyro.getAngle();
    }

    public void resetGyro() {
        gyro.reset();
    }

}