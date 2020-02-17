package com.stuypulse.robot.commands;

import com.stuypulse.robot.Constants;
import com.stuypulse.robot.subsystems.Chimney;
import com.stuypulse.robot.subsystems.Funnel;
import com.stuypulse.robot.subsystems.Shooter;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class FeedBallsCommand extends ParallelCommandGroup {

    // private final Shooter shooter;

    public FeedBallsCommand(Shooter shooter, Funnel funnel, Chimney chimney) {

        addCommands(
            new FunnelFunnelCommand(funnel),
            new ChimneyUpCommand(chimney)
        );
    }
}
