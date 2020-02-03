package com.stuypulse.robot.commands;

import java.util.Set;

import com.stuypulse.robot.subsystems.ControlPanel;
import com.stuypulse.stuylib.input.Gamepad;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class ControlPanelManualControlCommand implements Command {

    ControlPanel cPanel;
    Gamepad gamepad;

    public ControlPanelManualControlCommand(final ControlPanel cPanel, final Gamepad gamepad) {
        this.cPanel = cPanel;
        this.gamepad = gamepad;
    }

    @Override
    public void execute() {
        // TODO Auto-generated method stub
        cPanel.turn(gamepad.getRightY());
        Command.super.execute();
    }

    @Override
    public Set<Subsystem> getRequirements() {
        // TODO Auto-generated method stub
        return null;
    }




}