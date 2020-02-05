package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Chute;

public class ChuteDownCommand extends CommandBase {

    private Chute chute;

    public ChuteDownCommand(Chute chute) {
        this.chute = chute;

        // Use addRequirements() here to declare subsystem dependencies.
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

    /*@Override
    public void end(boolean interrupted) {
        chute.stopChute();
    }*/

}
