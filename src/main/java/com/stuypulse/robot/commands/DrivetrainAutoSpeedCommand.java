/* Copyright (c) 2021 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.robot.commands;

import com.stuypulse.stuylib.control.feedback.PIDCalculator;
import com.stuypulse.stuylib.control.feedback.PIDController;

import com.stuypulse.robot.Constants.Alignment;
import com.stuypulse.robot.subsystems.Drivetrain;

/**
 * This class will move the drivetrain and make measurements so that you can
 * calculate optimal P I
 * and D values for the speed controller.
 *
 * <p>
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
    public DrivetrainAutoSpeedCommand(
            Drivetrain drivetrain, DrivetrainAlignmentCommand.Aligner aligner) {
        super(
                drivetrain,
                aligner,
                new PIDCalculator(Alignment.Speed.BANGBANG_SPEED),
                Alignment.Angle.getPID());
        setNeverFinish();
    }

    // Report value to smart dashboard
    public void execute() {
        super.execute();

        if (speed instanceof PIDCalculator) {
            PIDController calculated = ((PIDCalculator) speed).getPDController();

            Alignment.Speed.P.set(calculated.getP());
            Alignment.Speed.I.set(calculated.getI());
            Alignment.Speed.D.set(calculated.getD());
        }
    }
}
