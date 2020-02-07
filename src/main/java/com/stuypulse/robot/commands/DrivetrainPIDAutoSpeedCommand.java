package com.stuypulse.robot.commands;

import com.stuypulse.robot.subsystems.Drivetrain;
import com.stuypulse.robot.Constants.Alignment;

import com.stuypulse.stuylib.control.Controller;
import com.stuypulse.stuylib.control.PIDCalculator;
import com.stuypulse.stuylib.control.PIDController;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class will move the drivetrain and make measurements so that you can
 * calculate optimal P I and D values for the speed controller.
 * 
 * While doing this it will need to have the angle aligned, so make sure that
 * you tune that first
 */
public class DrivetrainPIDAutoSpeedCommand extends DrivetrainAlignmentCommand {

    private static Controller getNewSpeedController() {
        return new PIDCalculator(Alignment.Speed.BANGBANG_SPEED);
    }

    private static Controller getNewAngleController() {
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
    public DrivetrainPIDAutoSpeedCommand(Drivetrain drivetrain, DrivetrainAlignmentCommand.Aligner aligner) {
        super(drivetrain, aligner, getNewSpeedController(), getNewAngleController());
    }

    public void execute() {
        super.execute();
        SmartDashboard.putString("Calculated Speed PID", getSpeedController().toString());
    }
}