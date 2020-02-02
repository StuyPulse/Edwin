package com.stuypulse.robot.commands;

import com.stuypulse.robot.subsystems.Drivetrain;
import com.stuypulse.robot.Constants.Alignment;

import com.stuypulse.stuylib.control.Controller;
import com.stuypulse.stuylib.control.PIDCalculator;
import com.stuypulse.stuylib.control.PIDController;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SpeedPIDCalculatorCommand extends AlignmentCommand {

    private static Controller getNewSpeedController() {
        return new PIDCalculator(Alignment.Speed.kBangBang);
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
    public SpeedPIDCalculatorCommand(Drivetrain drivetrain, double distance) {
        super(drivetrain, distance, getNewSpeedController(), getNewAngleController());
    }

    public void execute() {
        super.execute();
        SmartDashboard.putString("Calculated Speed PID", getSpeedController().toString());
    }
}