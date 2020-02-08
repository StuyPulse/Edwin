package com.stuypulse.frc.robot.commands;

import com.stuypulse.frc.robot.Constants;
import com.stuypulse.frc.robot.subsystems.Shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ShootFromInitiationLineCommand extends CommandBase {
    public Shooter m_shooter;
    public double targetVelocity;

    public ShootFromInitiationLineCommand(Shooter shooter) {
        m_shooter = shooter;
        targetVelocity = Constants.SHOOT_FROM_INITATION_LINE_RPM;
    }

    @Override
    public void initialize() {
        m_shooter.retractHoodSolenoid();
    }

    @Override
    public void execute() {
        m_shooter.startShooter(targetVelocity);

        if (m_shooter.getCurrentShooterVelocity() > targetVelocity) {
            m_shooter.startFeeder();
        } 
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
