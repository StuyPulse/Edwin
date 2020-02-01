package com.stuypulse.robot.util;

public interface Stallable {

    boolean isStalling();

    double getCurrentEncoderValue();

    double getEncoderApproachStallThreshhold();

    void setStalled();
}