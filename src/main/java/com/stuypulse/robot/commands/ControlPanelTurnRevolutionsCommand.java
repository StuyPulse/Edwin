package com.stuypulse.robot.commands;

import com.stuypulse.robot.subsystems.ControlPanel;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * An example command that uses an example subsystem.
 */
public class ControlPanelTurnRevolutionsCommand extends CommandBase {
    private final ControlPanel cPanel;
    private Color previousColor = null;
    private int colorCount;
    private int rotationsAmount;

    /**
     * Creates a new ExampleCommand.
     *
     * @param subsystem The subsystem used by this command.
     */
    public ControlPanelTurnRevolutionsCommand(ControlPanel cPanel) {
        this.cPanel = cPanel;
        addRequirements(cPanel);
    }

    @Override
    public void execute() {
        cPanel.turn(1);
        if (previousColor != null && previousColor != cPanel.getColor()) {
            colorCount += 1;
        }

        if (colorCount == 8) {
            rotationsAmount += 1;
            colorCount = 0;
        }
        previousColor = cPanel.getColor();
    }

    @Override
    public boolean isFinished() {
            return rotationsAmount == 3;
    }

    @Override
    public void end(boolean interrupted) {
        cPanel.stop();
    }
}
