package com.stuypulse.robot.commands;

import com.stuypulse.robot.subsystems.Chimney;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ChimneyUpCommand extends CommandBase {

    private final Chimney chimney;

    public ChimneyUpCommand(Chimney chimney) {
        this.chimney = chimney;
        
        addRequirements(chimney);
    }

    @Override
    public void execute() {
        chimney.liftUp();
    }

    @Override
    public void end(boolean interrupted) {
        chimney.stop();
    }
    
}