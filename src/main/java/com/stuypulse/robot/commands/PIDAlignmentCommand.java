package com.stuypulse.robot.commands;

import com.stuypulse.robot.subsystems.Drivetrain;
import com.stuypulse.robot.Constants.Alignment;

import com.stuypulse.stuylib.control.Controller;
import com.stuypulse.stuylib.control.PIDController;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PIDAlignmentCommand extends RawAlignmentCommand {

    private static Controller getNewSpeedController() {
        PIDController speed = new PIDController(-1, -1, -1);
        speed.setP(SmartDashboard.getNumber("SpeedP", Alignment.Speed.kP));
        speed.setI(SmartDashboard.getNumber("SpeedI", Alignment.Speed.kI));
        speed.setD(SmartDashboard.getNumber("SpeedD", Alignment.Speed.kD));
        return speed;
    }

    private static Controller getNewAngleController() {
        PIDController angle = new PIDController(-1, -1, -1);
        angle.setP(SmartDashboard.getNumber("AngleP", Alignment.Angle.kP));
        angle.setI(SmartDashboard.getNumber("AngleI", Alignment.Angle.kI));
        angle.setD(SmartDashboard.getNumber("AngleD", Alignment.Angle.kD));
        return angle;
    }

    /**
     * This creates a command that aligns the robot
     * 
     * @param drivetrain Drivetrain used by command to move
     * @param distance   target distance for robot to drive to
     */
    public PIDAlignmentCommand(Drivetrain drivetrain, double distance) {
        super(drivetrain, distance, getNewSpeedController(), getNewAngleController());
    }
}