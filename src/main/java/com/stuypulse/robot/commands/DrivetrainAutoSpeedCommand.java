package com.stuypulse.robot.commands;

import com.stuypulse.robot.subsystems.Drivetrain;
import com.stuypulse.robot.Constants.Alignment;

import com.stuypulse.stuylib.control.PIDCalculator;
import com.stuypulse.stuylib.control.PIDController;

/**
 * This class will move the drivetrain and make measurements so that you can
 * calculate optimal P I and D values for the speed controller.
 * 
 * While doing this it will need to have the angle aligned, so make sure that
 * you tune that first
 */
public class DrivetrainAutoSpeedCommand extends DrivetrainAlignmentCommand {

    /**
     * This creates a command that aligns the robot
     * 
     * @param drivetrain Drivetrain used by command to move
     * @param distance   target distance for robot to drive to
     */
    public DrivetrainAutoSpeedCommand(Drivetrain drivetrain, DrivetrainAlignmentCommand.Aligner aligner) {
        super(drivetrain, aligner, new PIDCalculator(Alignment.Speed.BANGBANG_SPEED), Alignment.Angle.getPID());
    }

    // This command never really finishes
    public boolean isFinished() {
        return false;
    }

    // Report value to smart dashboard
    public void execute() {
        super.execute();

        if (getSpeedController() instanceof PIDCalculator) {
            PIDController calculated = ((PIDCalculator) getSpeedController()).getPIDController(
                    Alignment.AUTOTUNE_P.doubleValue(), 
                    Alignment.AUTOTUNE_I.doubleValue(),
                    Alignment.AUTOTUNE_D.doubleValue()
                );
            
            Alignment.Speed.P.set(calculated.getP());
            Alignment.Speed.I.set(calculated.getI());
            Alignment.Speed.D.set(calculated.getD());
        }
    }
}