package com.stuypulse.robot.commands;

import com.stuypulse.robot.subsystems.Drivetrain;
import com.stuypulse.robot.Constants.Alignment;

import com.stuypulse.stuylib.control.Controller;
import com.stuypulse.stuylib.control.PIDCalculator;
import com.stuypulse.stuylib.control.PIDController;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class will move the drivetrain and make measurements so that you can
 * calculate optimal P I and D values for the angle controller.
 */
public class DrivetrainPIDAutoAngleCommand extends DrivetrainAlignmentCommand {

    private static Controller getNewSpeedController() {
        PIDController speed = new PIDController(-1, -1, -1);
        return speed;
    }

    private static Controller getNewAngleController() {
        return new PIDCalculator(Alignment.Angle.BANGBANG_SPEED);
    }

    /**
     * This creates a command that aligns the robot
     * 
     * @param drivetrain Drivetrain used by command to move
     * @param distance   target distance for robot to drive to
     */
    public DrivetrainPIDAutoAngleCommand(Drivetrain drivetrain, DrivetrainAlignmentCommand.Aligner aligner) {
        super(drivetrain, aligner, getNewSpeedController(), getNewAngleController());
    }

    public void execute() {
        super.execute();
        SmartDashboard.putString("Calculated Angle PID", getAngleController().toString());
    }
}