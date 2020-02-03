/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.stuypulse.robot;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.Timer;

public class MotorStalling implements Runnable {

    private int counter;   
    private ArrayList<MotorStall> subsystemArray;

    public MotorStalling(int arraySize) { 
        subsystemArray = new ArrayList<MotorStall>();
    }

    public void addSubsystem(MotorStall subsystem) {
        subsystemArray.add(subsystem);
    }

    public boolean checkStall() {
        double current_encoder_value;
        double change_distance;
        double start_encoder_value = 0;
        while(true) {
            for(int i = 0; i < subsystemArray.size(); i++) {
                current_encoder_value = Math.abs(subsystemArray.get(i).getEncoderVal());
                change_distance = Math.abs(current_encoder_value - start_encoder_value);
                if(change_distance <= subsystemArray.get(i).getEncoderApproachStallThreshold() && true) //TODO : replace true with corresponding gamepad button
                    counter++;
                else {
                    counter = 0;
                    return false;
                }
                if(counter >= 5)
                    return true;
                start_encoder_value = current_encoder_value;
                Timer.delay(0.2);
            }
        }

    }
    
    @Override
    public void run() {
        checkStall();
    }
    
}