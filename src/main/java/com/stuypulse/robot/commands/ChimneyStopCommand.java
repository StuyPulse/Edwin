package com.stuypulse.robot.commands;

import com.stuypulse.robot.subsystems.Chimney;
import com.stuypulse.stuylib.input.Gamepad;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ChimneyStopCommand extends CommandBase {

    private final Chimney chimney;
    private final Gamepad gamepad;

    public ChimneyStopCommand(Chimney chimney) {
        this.chimney = chimney;
        
        addRequirements(chimney);
    }

    @Override
    public void initialize() {
        System.out.println("Running ChimneyStopCommand");
        chimney.stop();
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("Ending ChimneyStopCommand");
        chimney.stop();
    }

}