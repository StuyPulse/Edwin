package com.stuypulse.robot.commands;

import com.stuypulse.robot.subsystems.ControlPanel;
import com.stuypulse.stuylib.input.Gamepad;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ControlPanelManualControlCommand extends CommandBase {

    private final ControlPanel controlPanel;
    private final Gamepad gamepad;

    public ControlPanelManualControlCommand(ControlPanel controlPanel, Gamepad gamepad) {
        this.controlPanel = controlPanel;
        this.gamepad = gamepad;

        addRequirements(controlPanel);
    }

    @Override
    public void execute() {
        controlPanel.turn(gamepad.getRightX());
    }

    @Override
    public void end(boolean interrupted) {
        controlPanel.stop();
    }

}