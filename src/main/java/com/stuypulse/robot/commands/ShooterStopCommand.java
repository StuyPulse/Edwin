package com.stuypulse.robot.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import com.stuypulse.robot.subsystems.Shooter;

public class ShooterStopCommand extends InstantCommand {
    public Shooter m_shooter;

    public ShooterStopCommand(Shooter shooter) {
        m_shooter = shooter;
    }

    @Override
    public void initialize() {
        m_shooter.stopShooter();
    }
}