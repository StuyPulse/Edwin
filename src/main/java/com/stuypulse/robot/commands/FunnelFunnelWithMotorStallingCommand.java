/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.stuypulse.robot.commands;

import com.stuypulse.robot.subsystems.Funnel;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class FunnelFunnelWithMotorStallingCommand extends CommandBase {
    
    private final Funnel funnel;

    public FunnelFunnelWithMotorStallingCommand(Funnel funnel) {
        this.funnel = funnel;
        addRequirements(funnel);
    }

    @Override
    public void initialize() {
        funnel.setRunning(true);
    }

    @Override
    public void execute() {
        if(funnel.isStalling())
           //TODO : when it stalls, gamepad will vibrate unil not stalled anymore
        funnel.funnel();
    }

    @Override
	public boolean isFinished() {
      return false;
    }

    @Override
    public void end(boolean interrupted) {
        funnel.stop();
        funnel.setRunning(false);
    }
}