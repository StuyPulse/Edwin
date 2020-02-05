package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Chute;

public class ChuteIntakeCommand extends CommandBase {

    private Chute chute;
    private int targetBalls;

    public ChuteIntakeCommand(Chute chute) {
        this.chute = chute;
        targetBalls = 1;

        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(chute);
    }
    public ChuteIntakeCommand(Chute chute, int balls) {
        this.chute = chute;
        targetBalls = balls;

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
        return chute.getRotations() != targetBalls;
    }

    /*@Override
    public void end(boolean interrupted) {
        chute.stopChute();
    }*/

}