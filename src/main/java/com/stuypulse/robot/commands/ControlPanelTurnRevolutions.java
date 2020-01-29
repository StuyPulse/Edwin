package com.stuypulse.robot.commands;

import com.stuypulse.robot.subsystems.ControlPanel;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * An example command that uses an example subsystem.
 */
public class ControlPanelTurnRevolutions extends CommandBase {
    private final ControlPanel m_cPanel;
    private int times;

    /**
     * Creates a new ExampleCommand.
     *
     * @param subsystem The subsystem used by this command.
     */
    public ControlPanelTurnRevolutions(ControlPanel subsystem, int times) {
        this.times = times;
        m_cPanel = subsystem;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(m_cPanel);
    }

    @Override
    public void initialize() {
        m_cPanel.turnNumberOfTimes(times);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
