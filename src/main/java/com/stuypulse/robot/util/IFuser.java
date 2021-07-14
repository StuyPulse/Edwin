package com.stuypulse.robot.util;

import com.stuypulse.stuylib.streams.IStream;
import com.stuypulse.stuylib.streams.filters.HighPassFilter;
import com.stuypulse.stuylib.streams.filters.IFilter;

import com.stuypulse.stuylib.streams.filters.LowPassFilter;

public class IFuser implements IStream {

    private IFilter lowPass, highPass;
    private IStream lowFreq, highFreq;

    public IFuser(Number rc, IStream lowFreq, IStream highFreq) {
        lowPass = new LowPassFilter(rc);
        highPass = new HighPassFilter(rc);

        this.lowFreq = lowFreq;
        this.highFreq = highFreq;
    }

    public IFuser(Number rc, IStream stream) {
        this(rc, stream, stream);
    }

    private double getLowPass() {
        return lowPass.get(lowFreq.get());
    }

    private double getHighPass() {
        return highPass.get(highFreq.get());
    }

    @Override
    public double get() {
        return (getLowPass() + getHighPass());
    }

}
