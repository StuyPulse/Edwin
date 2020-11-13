package com.stuypulse.robot.commands;

import com.stuypulse.robot.util.CVFuncs;
import com.stuypulse.stuylib.math.Angle;
import com.stuypulse.stuylib.network.limelight.Limelight;
import com.stuypulse.stuylib.streams.filters.LowPassFilter;

/**
 * The drivetrain goal aligner is an aligner that uses the limelight to send
 * instructions to the drivetrain on how it should move to align with the
 * target.
 */
public class DrivetrainInnerGoalAligner extends DrivetrainGoalAligner {

    private LowPassFilter filter = new LowPassFilter(0.2);

    public DrivetrainInnerGoalAligner(double distance) {
        super(distance);
    }

    public Angle getAngleError() {
        if(Limelight.hasValidTarget()) {
            return super.getAngleError().add(Angle.fromDegrees(filter.get(CVFuncs.txOffset())));
        } else {
            return Angle.fromDegrees(0);
        }
    }
}