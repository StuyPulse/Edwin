package com.stuypulse.robot.commands;

import com.stuypulse.robot.subsystems.Woof;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.CommandBase;
import com.stuypulse.robot.Constants;

public class WoofTurnRotationsCommand extends CommandBase {

    private final Woof controlPanel;
    
    private Color previousColor = null;
    private double colorCount;
    
    /**
     * Creates a new ExampleCommand.
     *
     * @param subsystem The subsystem used by this command.
     */
    public WoofTurnRotationsCommand(Woof controlPanel) {
        this.controlPanel = controlPanel;
        addRequirements(controlPanel);
    }

    @Override
    public void execute() {
        controlPanel.turn(Constants.WOOF_TURN_SPEED);
        if (previousColor != null && previousColor != controlPanel.getColor()) { 
            /**
             * checks if a previous color exists and that it is not the same as the curent color
             * if so, increment colorCount, which keeps track of the number of colors passed so far.
             * 0.125 is equivalent to one color
              */
            colorCount += 0.125;
        }
        previousColor = controlPanel.getColor();
    }

    @Override
    public boolean isFinished() {
        //stop once 3 rotations are met, 3.5 to be safe
            return colorCount >= 3.5;
    }

    @Override
    public void end(boolean interrupted) {
        controlPanel.stop();
    }
}
