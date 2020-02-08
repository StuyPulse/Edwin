package com.stuypulse.robot.subsystems;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

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

}