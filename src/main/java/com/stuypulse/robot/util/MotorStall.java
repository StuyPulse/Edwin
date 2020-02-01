/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.stuypulse.robot.util;

import com.stuypulse.robot.subsystems.Funnel;
import java.util.ArrayList;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class MotorStall implements Runnable {

    private int counter;
    private ArrayList<Stallable> subsystemArray;

    public MotorStall(int arraySize) {
        subsystemArray = new ArrayList<Stallable>();
    }

    public void addSubsystem(Stallable subsystem) {
        subsystemArray.add(subsystem);
    }

    @Override
    public void run() {
        checkStall();
    }

    public void checkStall() {
        int current_encoder_value;
        int change_distance;
        int start_encoder_value = 0;
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