package com.stuypulse.robot.commands;

import com.stuypulse.robot.Constants.ClimberSettings;
import com.stuypulse.robot.subsystems.Climber;
import com.stuypulse.stuylib.input.Gamepad;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ClimberMoveYoyoCommand extends CommandBase {

    private final Climber climber;
    private final Gamepad gamepad;

    public ClimberMoveYoyoCommand(Climber climber, Gamepad gamepad) {
        this.climber = climber;
        this.gamepad = gamepad;

        addRequirements(climber);
    }

    @Override
    public void execute() {
        climber.moveYoyo(Math.pow(gamepad.getLeftX() * ClimberSettings.SCALE, ClimberSettings.EXPONENT));
    }

    @Override
    public void end(boolean interrupted) {
        climber.stopYoyo();
    }

}