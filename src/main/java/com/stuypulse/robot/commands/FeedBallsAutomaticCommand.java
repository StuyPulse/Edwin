package com.stuypulse.robot.commands;

import com.stuypulse.robot.subsystems.Chimney;
import com.stuypulse.robot.subsystems.Funnel;
import com.stuypulse.stuylib.input.Gamepad;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class FeedBallsAutomaticCommand extends CommandBase {

    private final Chimney chimney;
    private final Funnel funnel;
    private final Gamepad gamepad;

    public FeedBallsAutomaticCommand(Chimney chimney, Funnel funnel, Gamepad gamepad) {
        this.chimney = chimney;
        this.funnel = funnel;
        this.gamepad = gamepad;

        addRequirements(chimney, funnel);
    }

    @Override
    public void execute() {
        if ((!chimney.getUpperChimneyValue() && chimney.getLowerChimneyValue()) // can bring a ball up the chimmey or 
            || gamepad.getRawDPadUp() || gamepad.getRawDPadDown() || gamepad.getRawDPadLeft()) { // shooter being run
            chimney.liftUp();
            funnel.funnel();
        } else {
            chimney.stop();
            funnel.stop();
        }
    }

}