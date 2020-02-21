package com.stuypulse.robot.commands;

import com.stuypulse.robot.Constants;
import com.stuypulse.robot.subsystems.Woof;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class WoofTurnRotationsWithEncoderCommand extends CommandBase {

    private final Woof woof;

    
    /**
     * Creates a new ExampleCommand.
     *
     * @param subsystem The subsystem used by this command.
     */
    public WoofTurnRotationsWithEncoderCommand(Woof woof) {
        this.woof = woof;
        addRequirements(woof);
    }

    @Override
    public void initialize() {
        woof.resetEncoderValue();
    }

    @Override
    public void execute() {
        SmartDashboard.putNumber("Woof Encoder Value", woof.getEncoderValue());
        woof.turn(Constants.WOOF_TURN_SPEED);
    }

    @Override
    public boolean isFinished() {
            return woof.getEncoderValue() >= Constants.WOOF_TARGET_ENCODER_VALUE;
    }

    @Override
    public void end(boolean interrupted) {
        woof.stop();
    }
}
