package com.stuypulse.robot.commands;

import com.stuypulse.robot.subsystems.Chimney;
import com.stuypulse.stuylib.input.Gamepad;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ChimneyStopCommand extends CommandBase {

    Chimney chimney;
    Gamepad gamepad;

    public ChimneyStopCommand(Chimney chimney) {
        this.chimney = chimney;
        
        addRequirements(chimney);
    }

    @Override
    public void initialize() {
        chimney.stop();
    }

    @Override
    public void end(boolean interrupted) {
        chimney.stop();
    }

}