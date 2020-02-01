package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Chute;

public class ChuteTakeBallCommand extends CommandBase {
    private Chute m_chute;
    private final int targetBalls;

    public ChuteTakeBallCommand(Chute subsystem, int balls) {
        m_chute = subsystem;
        targetBalls = balls;

        m_chute.resetEncoder();

        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(m_chute);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        m_chute.liftUp();
    }

    @Override
    public boolean isFinished() {  
        return m_chute.getRotations() == targetBalls;
    }

}