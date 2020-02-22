package com.stuypulse.robot.commands;

import com.stuypulse.robot.subsystems.Chimney;
import com.stuypulse.robot.subsystems.Funnel;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class FeedBallsInAutoCommand extends CommandBase{

    private final Funnel funnel;
    private final Chimney chimney;

    public FeedBallsInAutoCommand(Funnel funnel, Chimney chimney) {
        this.funnel = funnel;
        this.chimney = chimney;
    }

    @Override
    public void execute() {
        funnel.funnel();
        chimney.liftUp();
    }

    @Override
    public boolean isFinished() {
        return chimney.getUpperChimneyValue();
    }

}