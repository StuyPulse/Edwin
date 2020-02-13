package com.stuypulse.robot.util;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Timer;

public class BrownoutProtector implements Runnable {
    private BrownoutProtection[] priorityList;
    private PowerDistributionPanel pdp;
    private int counterLow;
    private int counterHigh;
    private int index;

    public BrownoutProtector(PowerDistributionPanel pdp, BrownoutProtection... brownoutProtectees) {
        priorityList = brownoutProtectees;
        this.pdp = pdp;
        counterLow = 0;
        counterHigh = 0;
        index = 0;
    }

    public boolean voltageLow() {
        if(pdp.getVoltage() < 10) { //10 corresponds to RobotMap.java constant LOW_VOLTAGE
            counterLow = 0;
            return false;
        }
        counterLow++;
        if(counterLow >= 3)
            return true;
        return false;
    }

    public boolean voltageSafe() {
        if(pdp.getVoltage() > 11) { //11 corresponds to RobotMap.java constant SAFE_VOLTAGE
            counterHigh = 0;
            return false;
        }
        counterHigh++;
        if(counterHigh >= 3)
            return true;
        return false;
    }

    public void priorityLimit() {
        while(true) {
            while(voltageLow() && (index < priorityList.length)) {
                priorityList[index].enableBrownout();
                index++;
                Timer.delay(.5);
            }
            while(voltageSafe() && (index > 0)) {
                index--;
                priorityList[index].disableBrownout();
                Timer.delay(.5);
            }
            Timer.delay(.5);
        }
    }

    @Override
    public void run() {
        priorityLimit();
    }
    
}