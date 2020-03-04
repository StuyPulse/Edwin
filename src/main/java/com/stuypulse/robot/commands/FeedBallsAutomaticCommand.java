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
        System.out.println("RUNNING");
        if(gamepad.getRawBottomButton()) {
            chimney.liftUp();
            funnel.funnel();
        } else {
            if(!chimney.getUpperChimneyValue()) { // && chimney.getLowerChimneyValue()) {
                System.out.println("FEEDING");
                chimney.liftUp();
                funnel.funnel();
            } else {
                System.out.println("STOPPING");
                chimney.stop();
                funnel.stop();
            }
        }
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("DONE");
        chimney.stop();
        funnel.stop();
    }

}