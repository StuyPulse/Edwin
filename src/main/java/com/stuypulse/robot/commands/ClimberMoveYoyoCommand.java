package com.stuypulse.robot.commands;

import com.stuypulse.robot.subsystems.Climber;
import com.stuypulse.stuylib.input.Gamepad;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ClimberMoveYoyoCommand extends CommandBase{

    Climber climber;
    Gamepad gamepad;

    public ClimberMoveYoyoCommand(Climber climber, Gamepad gamepad) {
        this.climber = climber;
        this.gamepad = gamepad;
    }

    @Override
    public void execute() {
        climber.moveYoyo(gamepad.getLeftX());
    }

    @Override
    public void end(boolean interrupted) {
        climber.stopYoyo();
    }

}