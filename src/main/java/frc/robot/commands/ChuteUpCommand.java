package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Chute;

public class ChuteUpCommand extends CommandBase {

    private Chute chute;

    public ChuteUpCommand(Chute chute) {
        this.chute = chute;

        // Use addRequirements() here to declare subsystem dependencies.
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

}