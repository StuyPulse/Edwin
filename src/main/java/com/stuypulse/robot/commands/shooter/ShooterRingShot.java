package com.stuypulse.robot.commands.shooter;

import com.stuypulse.robot.subsystems.Shooter;
import com.stuypulse.robot.subsystems.Shooter.ShooterMode;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class ShooterRingShot extends SequentialCommandGroup {
    public ShooterRingShot(Shooter shooter) {
        addCommands(
                new ShooterSetRPMCommand(shooter, ShooterMode.RING_SHOT));
    }
}
