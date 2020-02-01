package frc.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Chute;

/**
 * An example command that uses an example subsystem.
 */
public class ChuteTakeBallCommand extends CommandBase {
    private Chute m_chute;
    private final int targetBalls;

    public ChuteTakeBallCommand(Chute subsystem, int balls /* lol xd */) {
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