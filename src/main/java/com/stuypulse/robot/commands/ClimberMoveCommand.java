package com.stuypulse.robot.commands;

import com.stuypulse.robot.subsystems.Climber;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ClimberMoveCommand extends CommandBase {
    Climber m_climber;
    Gamepad gamepad;

    public ClimberMoveCommand(Climber m_climber, Gamepad gamepad) {
        this.m_climber = m_climber;
        this.gamepad = gamepad;
        addRequirements(m_climber);
    }

    @Override
    public void initialize() {
        m_climber.climbUp();
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        m_climber.stopClimber();
    }

}