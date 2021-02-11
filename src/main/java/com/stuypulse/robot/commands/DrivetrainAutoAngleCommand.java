package com.stuypulse.robot.commands;

import com.stuypulse.robot.subsystems.Drivetrain;
import com.stuypulse.robot.Constants.Alignment;

import com.stuypulse.stuylib.control.PIDCalculator;
import com.stuypulse.stuylib.control.PIDController;

/**
 * This class will move the drivetrain and make measurements so that you can
 * calculate optimal P I and D values for the angle controller.
 */
public class DrivetrainAutoAngleCommand extends DrivetrainAlignmentCommand {

    /**
     * This creates a command that aligns the robot
     * 
     * @param drivetrain Drivetrain used by command to move
     * @param distance   target distance for robot to drive to
     */
    public DrivetrainAutoAngleCommand(Drivetrain drivetrain, DrivetrainAlignmentCommand.Aligner aligner) {
        super(drivetrain, aligner, new PIDController(), new PIDCalculator(Alignment.Angle.BANGBANG_SPEED));
        setNeverFinish();
    }

    // Report value to smart dashboard
    public void execute() {
        super.execute();

        if (angle instanceof PIDCalculator) {
            PIDController calculated = ((PIDCalculator) angle).getPDController();

            Alignment.Angle.P.set(calculated.getP());
            Alignment.Angle.I.set(calculated.getI());
            Alignment.Angle.D.set(calculated.getD());
        }
    }
}