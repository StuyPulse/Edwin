package com.stuypulse.frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import com.stuypulse.frc.robot.subsystems.Shooter;

public class ShooterStopCommand extends CommandBase {
    public Shooter m_shooter;

    public ShooterStopCommand(Shooter shooter) {
        m_shooter = shooter;
        addRequirements(m_shooter);
    }

    @Override
    public void execute() {
        m_shooter.stopShooterMotors();
    }
}