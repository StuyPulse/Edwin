package com.stuypulse.robot.subsystems;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.stuypulse.robot.Constants.DrivetrainPorts;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Drivetrain extends SubsystemBase {

    private CANSparkMax mLeftTopMotor;
    private CANSparkMax mLeftMiddleMotor;
    private CANSparkMax mLeftBottomMotor;
    
    private CANSparkMax mRightTopMotor;
    private CANSparkMax mRightMiddleMotor;
    private CANSparkMax mRightBottomMotor;

    private SpeedControllerGroup mLeftMotors;
    private SpeedControllerGroup mRightMotors;

    private DifferentialDrive mDrivetrain;

    public Drivetrain() {
        mLeftTopMotor = new CANSparkMax(DrivetrainPorts.kLeftTop, MotorType.kBrushless);
        mLeftMiddleMotor = new CANSparkMax(DrivetrainPorts.kLeftMiddle, MotorType.kBrushless);
        mLeftBottomMotor = new CANSparkMax(DrivetrainPorts.kLeftBottom, MotorType.kBrushless);

        mRightTopMotor = new CANSparkMax(DrivetrainPorts.kRightTop, MotorType.kBrushless);
        mRightMiddleMotor = new CANSparkMax(DrivetrainPorts.kRightMiddle, MotorType.kBrushless);
        mRightBottomMotor = new CANSparkMax(DrivetrainPorts.kRightBottom, MotorType.kBrushless);

        mLeftMotors = new SpeedControllerGroup(mLeftTopMotor, mLeftMiddleMotor, mLeftBottomMotor);
        mRightMotors = new SpeedControllerGroup(mRightTopMotor, mRightMiddleMotor, mRightBottomMotor);

        mDrivetrain = new DifferentialDrive(mLeftMotors, mRightMotors);
    }

    public void tankDrive(double left, double right) {
        mDrivetrain.tankDrive(left, right);
    }

    public void arcadeDrive(double speed, double rotation) {
        mDrivetrain.arcadeDrive(speed, rotation, false);
    }

    public void curvatureDrive(double speed, double rotation, boolean quickturn) {
        mDrivetrain.curvatureDrive(speed, rotation, quickturn);
    }

}