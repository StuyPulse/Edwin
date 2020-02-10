package com.stuypulse.robot.commands;

import com.stuypulse.robot.subsystems.ControlPanel;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.CommandBase;
import com.stuypulse.robot.util.Constants;

public class ControlPanelTurnRevolutionsCommand extends CommandBase {
    private final ControlPanel controlPanel;
    private Color previousColor = null;
    private double colorCount;
    private double rotationsAmount;

    /**
     * Creates a new ExampleCommand.
     *
     * @param subsystem The subsystem used by this command.
     */
    public ControlPanelTurnRevolutionsCommand(ControlPanel controlPanel) {
        this.controlPanel = controlPanel;
        addRequirements(controlPanel);
    }

    @Override
    public void execute() {
        controlPanel.turn(Constants.COLOR_SENSOR_SPEED);
        if (previousColor != null && previousColor != controlPanel.getColor()) {
            colorCount += 0.125;
        }

        if (colorCount == 1) {
            rotationsAmount += 1;
            colorCount = 0;
        }
        previousColor = controlPanel.getColor();
    }

    @Override
    public boolean isFinished() {
            return rotationsAmount == 3.5;
    }

    @Override
    public void end(boolean interrupted) {
        controlPanel.stop();
    }
}
