package com.stuypulse.robot.commands;

import com.stuypulse.robot.subsystems.Chimney;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ChimneyStopCommand extends CommandBase {

    private final Chimney chimney;

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