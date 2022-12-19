package com.stuypulse.robot.commands.shooter;

import com.stuypulse.robot.subsystems.Shooter;
import com.stuypulse.robot.subsystems.Shooter.ShooterMode;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class ShooterLaunchPadShot extends SequentialCommandGroup {
    public ShooterLaunchPadShot(Shooter shooter) {
        addCommands(
                new ShooterSetRPMCommand(shooter, ShooterMode.LAUNCH_PAD_SHOT));
    }
}
