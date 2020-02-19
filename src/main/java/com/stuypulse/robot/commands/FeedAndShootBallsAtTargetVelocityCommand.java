package com.stuypulse.robot.commands;

import com.stuypulse.robot.subsystems.Chimney;
import com.stuypulse.robot.subsystems.Funnel;
import com.stuypulse.robot.subsystems.Shooter;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandGroupBase;

public class FeedAndShootBallsAtTargetVelocityCommand extends CommandGroupBase {

    public FeedAndShootBallsAtTargetVelocityCommand(int balls, Funnel funnel, Chimney chimney, Shooter shooter) {
        for (int i = 0; i < balls; i++) {
            addCommands(
                new FeedAndShootOneBallAtTargetVelocityCommand(funnel, chimney, shooter)
            );
        }
    }

    @Override
    public void addCommands(Command... commands) {
        // TODO Auto-generated method stub

    }



}