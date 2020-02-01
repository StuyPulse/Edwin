/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.stuypulse.robot;

import com.stuypulse.robot.subsystems.Funnel;

import edu.wpi.first.wpilibj.Timer;

public class MotorStall implements Runnable {
        
    private final Funnel m_Funnel;

    private double start_encoder_value;
    private double current_encoder_value;
    private double change_distance;
    private double encoder_approach_stall_threshold;
    private int counter;

    public MotorStall(Funnel funnel) {
        encoder_approach_stall_threshold = 3.0;
        m_Funnel = funnel;
    }

    @Override
    public void run() {
        checkStall();
    }

    public void checkStall() {
        while(true) {
            current_encoder_value = Math.abs(m_Funnel.getEncoderVal());
            change_distance = Math.abs(current_encoder_value - start_encoder_value);
            if(change_distance <= encoder_approach_stall_threshold && true) //TODO : replace true with corresponding gamepad button
                counter++;
            else {
                m_Funnel.setStalled(false);
                counter = 0;
            }
            if(counter >= 5)
                m_Funnel.setStalled(true);
            start_encoder_value = current_encoder_value;
            Timer.delay(0.2);
        }

    }
    
}