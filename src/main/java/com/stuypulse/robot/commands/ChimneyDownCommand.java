package com.stuypulse.robot.commands;

import com.stuypulse.robot.subsystems.Chimney;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ChimneyDownCommand extends CommandBase {

    private final Chimney chimney;

    public ChimneyDownCommand(Chimney chimney) {
        this.chimney = chimney;
        
        addRequirements(chimney);
    }

    @Override
    public void execute() {
        System.out.println("Running ChimneyDownCommand");
        chimney.liftDown();
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("Ending ChimneyDownCommand");
        chimney.stop();
    }
    
}