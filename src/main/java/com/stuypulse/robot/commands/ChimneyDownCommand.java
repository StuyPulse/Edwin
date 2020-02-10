package com.stuypulse.robot.commands;

import com.stuypulse.robot.subsystems.Chimney;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ChimneyDownCommand extends CommandBase {

    Chimney chimney;

    public ChimneyDownCommand(Chimney chimney) {
        this.chimney = chimney;
        
        addRequirements(chimney);
    }

    @Override
    public void execute() {
        chimney.liftDown();
    }

    @Override
    public void end(boolean interrupted) {
        chimney.stop();
    }
    
}