package com.stuypulse.robot.commands;

import com.stuypulse.robot.subsystems.Drivetrain;
import com.stuypulse.robot.Constants.Alignment;

import com.stuypulse.stuylib.control.Controller;
import com.stuypulse.stuylib.control.PIDController;

/**
 * This command takes in a drivetrain and aligner and moves the drivetrain until
 * the errors reach zero using PID. The kP kI kD values are from the constants.
 */
public class DrivetrainPIDAlignmentCommand extends DrivetrainAlignmentCommand {

    public static Controller getNewSpeedController() {
        PIDController speed = new PIDController(-1, -1, -1);
        speed.setP(Alignment.Speed.P.doubleValue());
        speed.setI(Alignment.Speed.I.doubleValue());
        speed.setD(Alignment.Speed.D.doubleValue());
        return speed;
    }

    public static Controller getNewAngleController() {
        PIDController angle = new PIDController(-1, -1, -1);
        angle.setP(Alignment.Angle.P.doubleValue());
        angle.setI(Alignment.Angle.I.doubleValue());
        angle.setD(Alignment.Angle.D.doubleValue());
        return angle;
    }

    /**
     * This creates a command that aligns the robot
     * 
     * @param drivetrain Drivetrain used by command to move
     * @param distance   target distance for robot to drive to
     */
    public DrivetrainPIDAlignmentCommand(Drivetrain drivetrain, DrivetrainAlignmentCommand.Aligner aligner) {
        super(drivetrain, aligner, getNewSpeedController(), getNewAngleController());
    }
}