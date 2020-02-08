package com.stuypulse.robot.commands;

import com.stuypulse.robot.subsystems.Chute;

import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * Constantly checks if the chute's lower sensor is touching a ball.
 * If it is touching a ball, it will move it up as long as there is enough space. 
 * It is toggleable by a gamepad button.
 */
public class ChuteIntakeCommand extends CommandBase {

    private Chute chute;

    public ChuteIntakeCommand(Chute chute) {
        this.chute = chute;
        addRequirements(chute);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() { 
        // Make go all the way to funnel if Shooter is shooting
        if (!chute.getUpperChuteValue() && chute.getLowerChuteValue()) {
            chute.liftUp();
        } else {
            chute.stopChute();
        }
        
    }

    @Override
    public boolean isFinished() {  
        return false;
    }

    // Just in case command shuts off
    @Override
    public void end(boolean interrupted) {
        chute.stopChute();
    }

}