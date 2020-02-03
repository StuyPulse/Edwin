  
package com.stuypulse.robot;

public interface MotorStall {
    public boolean isStalling();
    public double getEncoderVal();
    public double getEncoderApproachStallThreshold();
    public void enableStalling();

}