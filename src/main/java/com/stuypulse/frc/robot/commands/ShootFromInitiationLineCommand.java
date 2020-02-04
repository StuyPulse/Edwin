package com.stuypulse.frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import com.stuypulse.frc.robot.subsystems.Shooter;

public class ShootFromInitiationLineCommand extends CommandBase {
    public Shooter m_shooter;
    
    public ShootFromInitiationLineCommand(Shooter shooter) {
        m_shooter = shooter;
        addRequirements(m_shooter);
    }

    @Override
    public void initialize() {
        m_shooter.extendHoodSolenoid();
    }

    @Override
    public void execute() {
        m_shooter.runShooterMotors();
        if (m_shooter.getMaxShooterVelocity() > 3900) {
            m_shooter.feed();
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        m_shooter.stopFeeder();
        m_shooter.stopShooterMotors();
        m_shooter.setDefaultSolenoidPosition();
    }
}