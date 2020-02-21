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
        if(gamepad.getRawBottomButton()) {
            chimney.liftUp();
            funnel.funnel();
        } else {
            if(chimney.getLowerChimneyValue() && !chimney.getUpperChimneyValue()) {
                chimney.liftUp();
                funnel.funnel();
            } else if(chimney.getLowerChimneyValue() && chimney.getUpperChimneyValue()) {
                chimney.liftDown();
                funnel.stop();
            } else if(!chimney.getLowerChimneyValue() && chimney.getUpperChimneyValue()) {
                chimney.stop();
                funnel.stop();
            } else if(!chimney.getLowerChimneyValue() && chimney.getUpperChimneyValue()) {
                chimney.stop();
                funnel.stop();
            }
        }
    }

}