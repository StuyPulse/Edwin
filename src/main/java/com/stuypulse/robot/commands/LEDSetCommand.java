package com.stuypulse.robot.commands;

import com.stuypulse.robot.util.LEDController;
import com.stuypulse.robot.util.LEDController.Color;

import edu.wpi.first.wpilibj2.command.InstantCommand;

public class LEDSetCommand extends InstantCommand {

    private Color color;
    private LEDController controller;

    public LEDSetCommand(Color color, LEDController controller) {
        this.color = color;
        this.controller = controller;
    }

    @Override
    public void initialize() {
        controller.setColor(color);
    }

}