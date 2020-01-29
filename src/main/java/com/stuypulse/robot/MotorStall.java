/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.stuypulse.robot;

import edu.wpi.first.wpilibj.Timer;

public class MotorStall implements Runnable {
    
    public boolean stalled;
    
    private double start_encoder_value;
    private double current_encoder_value;
    private double change_distance;
    private double encoder_approach_stall_threshold;
    private double start_time;
    private double time_now;
    private int counter;

    public MotorStall() {
        encoder_approach_stall_threshold = 3.0;
    }

    @Override
    public void run() {

    }

    public boolean isStalled() {
        while(true) {
            current_encoder_value = Math.abs(Robot.m_robotContainer.funnel.getEncoderVal());
            change_distance = Math.abs(current_encoder_value - start_encoder_value);
            if(change_distance <= encoder_approach_stall_threshold && true) //TODO : replace true with corresponding gamepad button
                counter++;
            else {
                stalled = false;
                counter = 0;
            }
            if(counter >= 5)
                stalled = true; 
            Timer.delay(0.2);
        }

    }
    
}