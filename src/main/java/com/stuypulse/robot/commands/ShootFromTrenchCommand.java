package com.stuypulse.robot.commands;

import com.stuypulse.robot.Constants;
import com.stuypulse.robot.subsystems.Shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ShootFromTrenchCommand extends CommandBase {
    public Shooter m_shooter;
    public double targetVelocity;

    public ShootFromTrenchCommand(Shooter shooter) {
        m_shooter = shooter;
        addRequirements(m_shooter);

        targetVelocity = Constants.SHOOT_FROM_INITATION_LINE_RPM; // targetVelocity is 4900 RPM
    }

    @Override
    public void initialize() {
        m_shooter.extendHoodSolenoid(); // solenoid is fired for this mode
    }

    @Override
    public void execute() {
        m_shooter.runShooter(targetVelocity); 

        if (m_shooter.getCurrentShooterVelocityInRPM() > targetVelocity) {
            m_shooter.runFeeder();
        } 
        // if the shooter RPM reaches the target velocity, the feeder motor runs
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}