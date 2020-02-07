package com.stuypulse.robot.commands;

import java.util.Set;

import com.stuypulse.robot.subsystems.ControlPanel;
import com.stuypulse.stuylib.input.Gamepad;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ControlPanelManualControlCommand extends CommandBase {

    ControlPanel cPanel;
    Gamepad gamepad;

    public ControlPanelManualControlCommand(ControlPanel cPanel, Gamepad gamepad) {
        this.cPanel = cPanel;
        this.gamepad = gamepad;

        addRequirements(cPanel);
    }

    @Override
    public void execute() {
        cPanel.turn(gamepad.getRightX());
    }

}