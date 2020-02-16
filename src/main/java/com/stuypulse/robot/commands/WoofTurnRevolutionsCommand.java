package com.stuypulse.robot.commands;

import com.stuypulse.robot.subsystems.Woof;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.CommandBase;
import com.stuypulse.robot.Constants;

public class WoofTurnRevolutionsCommand extends CommandBase {

    private final Woof woof;
    
    private Color previousColor = null;
    private double colorCount;
    private double rotationsAmount;

    /**
     * Creates a new ExampleCommand.
     *
     * @param subsystem The subsystem used by this command.
     */
    public WoofTurnRevolutionsCommand(Woof woof) {
        this.woof = woof;
        addRequirements(woof);
    }

    @Override
    public void execute() {
        woof.turn(Constants.WOOF_TURN_SPEED);
        if (previousColor != null && previousColor != woof.getColor()) {
            colorCount += 0.125;
        }

        if (Math.abs(colorCount - 1) < 0.01) {
            rotationsAmount += 1;
            colorCount = 0;
        }
        previousColor = woof.getColor();
    }

    @Override
    public boolean isFinished() {
        //stop at 3 1/2 or 3 5/8
            return rotationsAmount >= 3.5;
    }

    @Override
    public void end(boolean interrupted) {
        woof.stop();
    }
}
