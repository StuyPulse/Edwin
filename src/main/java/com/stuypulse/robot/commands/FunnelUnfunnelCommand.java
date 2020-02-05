/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.stuypulse.robot.commands;

import com.stuypulse.robot.subsystems.Funnel;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class FunnelUnfunnelCommand extends CommandBase {
    
    private final Funnel m_Funnel;

    public FunnelUnfunnelCommand(Funnel funnel) {
        m_Funnel = funnel;
        addRequirements(m_Funnel);
    }

    @Override
    public void initialize() {
        new Thread().start();
    }

    @Override
    public void execute() {
        m_Funnel.unfunnel();
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