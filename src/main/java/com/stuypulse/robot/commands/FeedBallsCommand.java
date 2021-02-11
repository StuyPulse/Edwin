package com.stuypulse.robot.commands;

import com.stuypulse.robot.subsystems.Chimney;
import com.stuypulse.robot.subsystems.Funnel;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

public class FeedBallsCommand extends ParallelCommandGroup {

    public FeedBallsCommand(Funnel funnel, Chimney chimney) {
        addCommands(
            new FunnelFunnelCommand(funnel), 
            new ChimneyUpCommand(chimney));
    }
    
}
