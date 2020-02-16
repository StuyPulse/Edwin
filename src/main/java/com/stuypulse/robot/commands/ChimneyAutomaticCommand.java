package com.stuypulse.robot.commands;

import com.stuypulse.robot.subsystems.Chimney;
import com.stuypulse.stuylib.input.Gamepad;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ChimneyAutomaticCommand extends CommandBase {

    private final Chimney chimney;
    private final Gamepad gamepad;

    public ChimneyAutomaticCommand(Chimney chimney, Gamepad gamepad) {
        this.chimney = chimney;
        this.gamepad = gamepad;
        
        addRequirements(chimney);
    }

    @Override
    public void execute() {
        if (gamepad.getRawDPadDown()) {
            chimney.liftUp();
        } else {
            if (!chimney.getUpperChimneyValue() && chimney.getLowerChimneyValue()) {
                chimney.liftUp();
            } else {
                chimney.stop();
            }
        }
    }

}