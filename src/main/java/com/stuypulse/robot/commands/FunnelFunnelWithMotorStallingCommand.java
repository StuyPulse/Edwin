/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.stuypulse.robot.commands;

import com.stuypulse.robot.MotorStall;
import com.stuypulse.robot.subsystems.Funnel;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class FunnelFunnelWithMotorStallingCommand extends CommandBase {
    
    private final Funnel m_Funnel;

    public FunnelFunnelWithMotorStallingCommand(Funnel funnel) {
        m_Funnel = funnel;
        addRequirements(m_Funnel);
    }

    @Override
    public void initialize() {
        new Thread(new MotorStall(m_Funnel)).start();
    }

    @Override
    public void execute() {
        if(m_Funnel.isStalled())
           //TODO : when it stalls, gamepad will vibrate unil not stalled anymore
        m_Funnel.funnel();
    }

    @Override
	public boolean isFinished() {
      return false;
    }

    @Override
    public void end(boolean interrupted) {
        m_Funnel.stop();
    }
}