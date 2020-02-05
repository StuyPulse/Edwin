package com.stuypulse.robot.commands;

import com.stuypulse.robot.subsystems.Drivetrain;
import com.stuypulse.robot.commands.DrivetrainCommand;
import com.stuypulse.robot.Constants.Alignment;

import com.stuypulse.stuylib.control.Controller;
import com.stuypulse.stuylib.network.limelight.Limelight;
import com.stuypulse.stuylib.streams.filters.LowPassFilter;

/**
 * Drivetrain Alignment Command takes in a drivetrain, an aligner, and two
 * controllers. This lets you align the robot with whatever controllers you
 * want. Most commonly, a DrivetrainPIDAlignmentCommand is used instead as it
 * automatically provides the controllers for you.
 */
public class DrivetrainAlignmentCommand extends DrivetrainCommand {

    /**
     * This interface allows you to create classes that instruct the drivetrain to
     * move based off of error for speed and angle. If you use an aligner to define
     * your class, you can do things like auto tune.
     */
    public interface Aligner {
        public double getSpeedError();

        public double getAngleError();
    }

    // Controllers for Alignment
    private Controller mSpeed;
    private Controller mAngle;

    // Distance that the command will try to align with
    private Aligner mAligner;

    /**
     * This creates a command that aligns the robot
     * 
     * @param drivetrain Drivetrain used by command to move
     * @param distance   target distance for robot to drive to
     * @param speed      controller used to align distance
     * @param angle      controller used to align the angle
     */
    public DrivetrainAlignmentCommand(Drivetrain drivetrain, Aligner aligner, Controller speed, Controller angle) {
        // Pass Drivetrain to the super class
        super(drivetrain);

        // Initialize PID Controller for Speed
        mSpeed = speed;
        mSpeed.setErrorFilter(new LowPassFilter(Alignment.Speed.kInSmoothTime));
        mSpeed.setOutputFilter(new LowPassFilter(Alignment.Speed.kOutSmoothTime));

        // Initialize PID Controller for Angle
        mAngle = angle;
        mAngle.setErrorFilter(new LowPassFilter(Alignment.Angle.kInSmoothTime));
        mAngle.setOutputFilter(new LowPassFilter(Alignment.Angle.kOutSmoothTime));

        // Target distance for the Alignment Command
        mAligner = aligner;
    }

    // Get the Speed Controller
    // Used by sub classes to get information
    protected Controller getSpeedController() {
        return mSpeed;
    }

    // Get the Angle controller
    // Used by sub classes to get information
    protected Controller getAngleController() {
        return mAngle;
    }

    // Update the speed if the angle is aligned
    public double getSpeed() {
        if ( // Check if the angle is aligned before moving forward
        mAngle.getError() < Alignment.Angle.kMaxAngleErr && mAngle.getVelocity() < Alignment.Angle.kMaxAngleVel) {
            return mSpeed.update(mAligner.getSpeedError());
        } else {
            return 0.0;
        }
    }

    // Update angle based on angle error
    public double getAngle() {
        return mAngle.update(mAligner.getAngleError());
    }

    // The Limelight camera shall stay on when aligning
    // Works as a good indicator that things are working
    public void initialize() {
        // Moved to DrivetrainGoalAligner() to reduce LED usage
        // Limelight.setLEDMode(Limelight.LEDMode.FORCE_ON);
    }

    // Turn limelight off when no longer aligning due to rules
    public void end(boolean interrupted) {
        Limelight.setLEDMode(Limelight.LEDMode.FORCE_OFF);
    }

    // Command is finished if all of the errors are small enough
    public boolean isFinished() {
        return (mSpeed.getError() < Alignment.Speed.kMaxSpeedErr 
                && mSpeed.getVelocity() < Alignment.Speed.kMaxSpeedVel
                && mAngle.getError() < Alignment.Angle.kMaxAngleErr
                && mAngle.getVelocity() < Alignment.Angle.kMaxAngleVel);
    }
}