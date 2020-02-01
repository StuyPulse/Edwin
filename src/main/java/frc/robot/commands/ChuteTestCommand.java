package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Chute;

/**
 * An example command that uses an example subsystem.
 */
public class ChuteTestCommand extends CommandBase {
    private Chute m_chute;


    public ChuteTestCommand(Chute subsystem) {
        m_chute = subsystem;

        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(m_chute);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {

    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override 
    public void end(boolean interrupted) {
    }

}