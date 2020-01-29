/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.util.Set;

import com.stuypulse.robot.MotorStall;
import com.stuypulse.robot.Robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class FunnelMotorStallingCommand implements Command {

    @Override
	public void initialize() {
        new Thread(new MotorStall()).start();
    }

    @Override
    public void execute() {
        if(Robot.m_robotContainer.funnel.stalled)
           //TODO : when it stalls, gamepad will vibrate unil not stalled anymore
        Robot.m_robotContainer.funnel.funnel();
    }

    @Override
	public boolean isFinished() {
      return false;
    }

	@Override
	public Set<Subsystem> getRequirements() {
		return Set.of(Robot.m_robotContainer.funnel);
	}
  }
}