package com.stuypulse.robot.commands;

import com.stuypulse.robot.subsystems.ControlPanel;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * An example command that uses an example subsystem.
 */
public class ControlPanelTurnRevolutionsCommand extends CommandBase {
    private final ControlPanel m_cPanel;
    private int times;
    private Color previousColor = null;
    private int colorCount;
    private int rotationsAmount;

    /**
     * Creates a new ExampleCommand.
     *
     * @param subsystem The subsystem used by this command.
     */
    public ControlPanelTurnRevolutionsCommand(ControlPanel subsystem, int times) {
        this.times = times;
        m_cPanel = subsystem;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(m_cPanel);
    }

    @Override
    public void execute() {
        m_cPanel.turn(1);
        if (previousColor != null && previousColor != m_cPanel.getColor()) {
            colorCount += 1;
        }

        if (colorCount == 8) {
            rotationsAmount += 1;
            colorCount = 0;
        }
        previousColor = m_cPanel.getColor();
    }

    @Override
    public boolean isFinished() {
            return rotationsAmount == 3;
    }

    @Override
    public void end(boolean interrupted) {
        // code to run wwhen ends
        m_cPanel.stop();
    }
}
