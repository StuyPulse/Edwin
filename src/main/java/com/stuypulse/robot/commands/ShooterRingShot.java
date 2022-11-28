package com.stuypulse.robot.commands;

import com.stuypulse.robot.constants.Settings;
import com.stuypulse.robot.subsystems.Shooter;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class ShooterRingShot extends SequentialCommandGroup {
    public ShooterRingShot(Shooter shooter) {
        addCommands(
                new ShooterExtendHoodCommand(shooter),
                new ShooterSetRPMCommand(shooter, Settings.Shooter.RING_RPM.get()));
    }
}
