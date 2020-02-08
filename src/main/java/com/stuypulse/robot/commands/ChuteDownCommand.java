package com.stuypulse.robot.commands;

import com.stuypulse.robot.subsystems.Chute;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ChuteDownCommand extends CommandBase {

    private Chute chute;

    public ChuteDownCommand(Chute chute) {
        this.chute = chute;

        addRequirements(chute);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        chute.liftDown();
    }

    @Override
    public boolean isFinished() {  
        return chute.getLowerChuteValue();
    }

    @Override
    public void end(boolean interrupted) {
        chute.stopChute();
    }

}
