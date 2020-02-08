  
package com.stuypulse.robot.util;

public interface MotorStall {
    public boolean isStalling();
    public void setStalled(boolean value);

    public void setStartEncoderVal(double val);
    public double getStartEncoderVal();
    public double getCurrentEncoderVal();
    public double getEncoderApproachStallThreshold();
   
    public int getStallCounter();
    public void incrementStallCounter();
    public void resetStallCounter();
    
    public boolean isRunning();
    public void setRunning(boolean val);
}