package com.stuypulse.robot.commands;

import com.stuypulse.robot.subsystems.Drivetrain;
import com.stuypulse.robot.Constants.Alignment;

import com.stuypulse.stuylib.control.Controller;
import com.stuypulse.stuylib.control.PIDCalculator;
import com.stuypulse.stuylib.control.PIDController;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AnglePIDCalculatorCommand extends AlignmentCommand {

    private static Controller getNewSpeedController() {
        PIDController speed = new PIDController(-1, -1, -1);
        return speed;
    }

    private static Controller getNewAngleController() {
        return new PIDCalculator(Alignment.Angle.kBangBang);
    }

    /**
     * This creates a command that aligns the robot
     * 
     * @param drivetrain Drivetrain used by command to move
     * @param distance   target distance for robot to drive to
     */
    public AnglePIDCalculatorCommand(Drivetrain drivetrain, double distance) {
        super(drivetrain, distance, getNewSpeedController(), getNewAngleController());
    }

    public void execute() {
        super.execute();
        SmartDashboard.putString("Calculated Angle PID", getAngleController().toString());
    }
}