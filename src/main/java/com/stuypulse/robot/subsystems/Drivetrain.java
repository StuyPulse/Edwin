package com.stuypulse.robot.subsystems;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.stuypulse.robot.Constants;
import com.stuypulse.stuylib.file.FRCLogger.Loggable;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Drivetrain extends SubsystemBase implements Loggable {

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

    public String getLogData() {

        return 
        "Left side speed: " + 
        Double.toString(
            (
                leftTopMotor.getEncoder().getVelocity() + 
                leftMiddleMotor.getEncoder().getVelocity() + 
                leftBottomMotor.getEncoder().getVelocity()
            ) / 3
        ) + 
        "\n" +
        "Right side speed: " + 
        Double.toString(
            (
                rightTopMotor.getEncoder().getVelocity() + 
                rightMiddleMotor.getEncoder().getVelocity() + 
                rightBottomMotor.getEncoder().getVelocity()
            ) / 3
        ) + 
        "\n" + 
        "Left side currents, top to bottom:\n" + 
        Double.toString(leftTopMotor.getOutputCurrent()) + "\n" +
        Double.toString(leftMiddleMotor.getOutputCurrent()) + "\n" +
        Double.toString(leftBottomMotor.getOutputCurrent()) + "\n" +
        "Right side currents, top to bottom:\n" + 
        Double.toString(rightTopMotor.getOutputCurrent()) + "\n" +
        Double.toString(rightMiddleMotor.getOutputCurrent()) + "\n" +
        Double.toString(rightBottomMotor.getOutputCurrent()); 

    }

}