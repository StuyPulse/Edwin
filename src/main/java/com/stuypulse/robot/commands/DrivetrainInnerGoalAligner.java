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

    private LowPassFilter filter = new LowPassFilter(0.5);
    public DrivetrainInnerGoalAligner() {
        // Turn on LEDs for CV
        Limelight.setLEDMode(Limelight.LEDMode.FORCE_ON);
    }

    public double getSpeedError() {
        return 0;
    }

    public double getAngleError() {
        // TODO: have CV replace this command
        return Limelight.getTargetXAngle() + Alignment.Measurements.Limelight.YAW + filter.get(CVFuncs.txOffset());
    }
}