package com.stuypulse.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import com.stuypulse.robot.subsystems.Chute;

public class ChuteUpCommand extends CommandBase {

    private Chute chute;

    public ChuteUpCommand(Chute chute) {
        this.chute = chute;

        addRequirements(chute);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        chute.liftUp();
    }

    @Override
    public boolean isFinished() {  
        return chute.getUpperChuteValue();
    }

    @Override
    public void end(boolean interrupted) {
        chute.stopChute();
    }

}