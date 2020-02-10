/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.stuypulse.robot.util;

import edu.wpi.first.wpilibj.Timer;

public class MotorStalling implements Runnable {

    private MotorStall[] subsystemArray;

    public MotorStalling(MotorStall... subsystems) { 
        subsystemArray = subsystems;
    }

    public void checkStall() {
        while(true) {
            Timer.delay(0.2);
            for(MotorStall subsystem : subsystemArray) {
                double start_encoder_value = Math.abs(subsystem.getStartEncoderVal());
                double current_encoder_value = Math.abs(subsystem.getCurrentEncoderVal());
                double change_distance = Math.abs(current_encoder_value - start_encoder_value);
                if(subsystem.getStallCounter() >= 5)
                    subsystem.setStalled(true);
                if (change_distance <= subsystem.getEncoderApproachStallThreshold() && subsystem.isRunning())
                    subsystem.incrementStallCounter();
                else {
                    subsystem.resetStallCounter();
                    subsystem.setStalled(false);
                }
                subsystem.setStartEncoderVal(current_encoder_value);
            }
        }

    }
    
    @Override
    public void run() {
        checkStall();
    }
    
}