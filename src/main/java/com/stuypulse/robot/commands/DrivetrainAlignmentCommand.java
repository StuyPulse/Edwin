package com.stuypulse.robot.commands;

import com.stuypulse.robot.subsystems.Drivetrain;
import com.stuypulse.robot.commands.DrivetrainCommand;
import com.stuypulse.robot.Constants.Alignment;

import com.stuypulse.stuylib.control.Controller;
import com.stuypulse.stuylib.math.SLMath;
import com.stuypulse.stuylib.network.limelight.Limelight;
import com.stuypulse.stuylib.streams.filters.LowPassFilter;
import com.stuypulse.stuylib.util.StopWatch;

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
        // Called when command initialize is called,
        // Useful for relative encoder commands
        public default void init() { }

        // The amount of positional error
        public default double getSpeedError() { return 0.0; };

        // The amount of angular error
        public default double getAngleError() { return 0.0; };
    }

    // Controllers for Alignment
    private Controller speed;
    private Controller angle;

    // Distance that the command will try to align with
    private Aligner aligner;

    // Used to check timeout of alignment
    private StopWatch timer;

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
        this.speed = speed;
        this.speed.setErrorFilter(new LowPassFilter(Alignment.Speed.IN_SMOOTH_FILTER.doubleValue()));
        this.speed.setOutputFilter(new LowPassFilter(Alignment.Speed.OUT_SMOOTH_FILTER.doubleValue()));

        // Initialize PID Controller for Angle
        this.angle = angle;
        this.angle.setErrorFilter(new LowPassFilter(Alignment.Angle.IN_SMOOTH_FILTER.doubleValue()));
        this.angle.setOutputFilter(new LowPassFilter(Alignment.Angle.OUT_SMOOTH_FILTER.doubleValue()));

        // Target distance for the Alignment Command
        this.aligner = aligner;

        // Used to check the alignment time.
        this.timer = new StopWatch();
    }

    /**
     * This creates a command that aligns the robot
     * 
     * @param drivetrain Drivetrain used by command to move
     * @param distance   target distance for robot to drive to
     */
    public DrivetrainAlignmentCommand(Drivetrain drivetrain, Aligner aligner) {
        this(drivetrain, aligner, Alignment.Speed.getPID(), Alignment.Angle.getPID());
    }

    // Get the Speed Controller
    // Used by sub classes to get information
    protected Controller getSpeedController() {
        return speed;
    }

    // Get the Angle controller
    // Used by sub classes to get information
    protected Controller getAngleController() {
        return angle;
    }

    // Update the speed if the angle is aligned
    public double getSpeed() {
        double angleError = Math.abs(aligner.getAngleError());
        double speedError = aligner.getSpeedError();
        double out = 0;

        if(angleError < Alignment.Angle.MAX_ANGLE_ERROR) {
            out = SLMath.limit(speed.update(speedError), -1, 1);
        } else {
            angleError -= Alignment.Angle.MAX_ANGLE_ERROR;
            angleError = Alignment.Angle.MAX_ANGLE_ERROR - angleError;
            angleError = Math.max(angleError, 0.0);

            out = SLMath.limit(speed.update(speedError), -1, 1);
            out *= (Alignment.Angle.MAX_ANGLE_ERROR - angleError);
            out /= Alignment.Angle.MAX_ANGLE_ERROR;
        }

        return out;
    }

    // Update angle based on angle error
    public double getAngle() {
        double error = aligner.getAngleError();
        error = Math.copySign(Math.abs(error) % 360, error);

        if(error > 180) { 
            error -= 360;
        }

        if(error < -180) { 
            error += 360;
        }
    
        return SLMath.limit(angle.update(error), -1, 1);
    }

    // Alignment must use low gear
    public Drivetrain.Gear getGear() {
        return Drivetrain.Gear.LOW;
    }

    public boolean useCurvatureDrive() {
        // Aligning doesn't need to use curvature drive
        // Arcade drive is better for non humans
        return false;
    }

    // Set the gear and other things when initializing
    public void initialize() {
        aligner.init();
        timer.reset();
    }

    // Turn limelight off when no longer aligning due to rules
    public void end(boolean interrupted) {
        Limelight.setLEDMode(Limelight.LEDMode.FORCE_OFF);
    }

    // Command is finished if all of the errors are small enough
    public boolean isFinished() {
        // Check if the aligner hasn't run for long enough
        if(timer.getTime() < Alignment.MIN_ALIGNMENT_TIME) {
            return false;
        }

        // Time out for aligning
        if(timer.getTime() > Alignment.MAX_ALIGNMENT_TIME) {
            return false;
        }

        return (speed.getError() < Alignment.Speed.MAX_SPEED_ERROR 
                && speed.getVelocity() < Alignment.Speed.MAX_SPEED_VEL
                && angle.getError() < Alignment.Angle.MAX_ANGLE_ERROR
                && angle.getVelocity() < Alignment.Angle.MAX_ANGLE_VEL);
    }
}