package com.stuypulse.robot.commands;

import com.stuypulse.robot.subsystems.Shooter;

import edu.wpi.first.wpilibj2.command.InstantCommand;

public class ShooterSetRPMCommand extends InstantCommand {
    private final Shooter shooter;
    private final Number targetRPM;

    public ShooterSetRPMCommand(Shooter shooter, Number targetRPM) {
        this.shooter = shooter;
        this.targetRPM = targetRPM;
        addRequirements(shooter);
    }

    @Override
    public void initialize() {
        shooter.setTargetRPM(targetRPM);
    }
}
