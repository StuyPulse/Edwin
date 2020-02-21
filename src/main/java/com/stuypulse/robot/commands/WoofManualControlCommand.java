package com.stuypulse.robot.commands;

import com.stuypulse.robot.subsystems.Woof;
import com.stuypulse.stuylib.input.Gamepad;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class WoofManualControlCommand extends CommandBase {

    private final Woof woof;
    private final Gamepad gamepad;

    public WoofManualControlCommand(Woof woof, Gamepad gamepad) {
        this.woof = woof;
        this.gamepad = gamepad;

        addRequirements(woof);
    }

    @Override
    public void execute() {
        woof.turn(gamepad.getRightX());
    }

    @Override
    public void end(boolean interrupted) {
        woof.stop();
    }

}