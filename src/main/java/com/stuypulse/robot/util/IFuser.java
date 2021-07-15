package com.stuypulse.robot.util;

import com.stuypulse.stuylib.streams.IStream;
import com.stuypulse.stuylib.streams.filters.HighPassFilter;
import com.stuypulse.stuylib.streams.filters.IFilter;

import com.stuypulse.stuylib.streams.filters.LowPassFilter;

/**
 * A "fuser" combins a low frequency input stream and a 
 * high frequency input stream using a low pass and high pass filter.
 * 
 * This fuser is implemented as a stream itself, and takes in two others.
 * 
 * TOOD: move to stuylib?
 * 
 * @author Myles Pasetsky (@selym3)
 */
public class IFuser implements IStream {

    // Two input streams: one being low frequency updates and the other 
    // being high frequency updates
    private IStream lowFreq, highFreq;

    // The low pass and high pass filters
    private IFilter lowPass, highPass;

    /**
     * Creates an input fuser stream
     * 
     * @param rc window size value given to both the high and low pass filter
     * @param lowFreq an input stream of low frequency data 
     * @param highFreq an input stream of high frequency data
     */
    public IFuser(Number rc, IStream lowFreq, IStream highFreq) {
        lowPass = new LowPassFilter(rc);
        highPass = new HighPassFilter(rc);

        this.lowFreq = lowFreq;
        this.highFreq = highFreq;
    }

    /**
     * @return data from the low frequency stream through the low pass filter
     */
    private double getLowPass() {
        return lowPass.get(lowFreq.get());
    }

    /**
     * @return data from the high frequency stream through the high pass filter
     */
    private double getHighPass() {
        return highPass.get(highFreq.get());
    }

    /**
     * Returns the combined low pass and high pass data.
     * 
     * Provides the IStream interface for an IFuser.
     * 
     * @return the combined low pass and high pass data
     */
    public double get() {
        return (getLowPass() + getHighPass());
    }

}
