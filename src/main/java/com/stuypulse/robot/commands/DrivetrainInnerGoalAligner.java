package com.stuypulse.robot.commands;

import com.stuypulse.robot.Constants.Alignment;
import com.stuypulse.robot.util.CVFuncs;
import com.stuypulse.stuylib.network.limelight.Limelight;
import com.stuypulse.stuylib.streams.filters.LowPassFilter;

/**
 * The drivetrain goal aligner is an aligner that uses the limelight to send
 * instructions to the drivetrain on how it should move to align with the
 * target.
 */
public class DrivetrainInnerGoalAligner implements DrivetrainAlignmentCommand.Aligner {

    private LowPassFilter filter = new LowPassFilter(0.2);

    public DrivetrainInnerGoalAligner() {
    }

    public void init() {
        // Turn on LEDs for CV
        Limelight.setLEDMode(Limelight.LEDMode.FORCE_ON);
    }

    public double getAngleError() {
        if(Limelight.hasValidTarget()) {
            return 0 - (Limelight.getTargetXAngle() + Alignment.Measurements.Limelight.YAW + filter.get(CVFuncs.txOffset()));
        } else {
            return 0;
        }
    }
}