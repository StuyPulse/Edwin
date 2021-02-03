package com.stuypulse.robot.commands;

import com.stuypulse.robot.subsystems.Shooter;
import com.stuypulse.robot.subsystems.Shooter.ShooterMode;

import edu.wpi.first.wpilibj2.command.InstantCommand;

public class ShooterControlCommand extends InstantCommand {
    private final Shooter shooter;
    private final double targetRPM;
    private final ShooterMode mode;

    public ShooterControlCommand(Shooter shooter, double targetRPM, ShooterMode mode) {
        this.shooter = shooter;
        this.targetRPM = targetRPM;
        this.mode = mode;
    }

    @Override
    public void initialize() {
        shooter.setTargetRPM(targetRPM);
        shooter.setShooterMode(mode);

        if(mode == ShooterMode.SHOOT_FROM_TRENCH || mode == ShooterMode.SHOOT_FROM_FAR) {
            shooter.retractHoodSolenoid();
        }
        if(mode == ShooterMode.SHOOT_FROM_INITIATION_LINE) {
            shooter.extendHoodSolenoid();
        }
    }
}