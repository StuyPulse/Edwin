package com.stuypulse.robot.commands;

import com.stuypulse.robot.RobotContainer;
import com.stuypulse.robot.subsystems.Chute;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ChuteIntakeCommand extends CommandBase {

    private Chute chute;
    private static boolean isIntake;

    public ChuteIntakeCommand(Chute chute) {
        this.chute = chute;
        isIntake = false;

        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(chute);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        isIntake = RobotContainer.gamepad.getRawDPadDown(); 

        if (!isIntake) {
            if (chute.getLowerChuteValue()) {
                chute.liftUp();
            } else {
                chute.stopChute();
            }
        } else {
            chute.stopChute();
        }

    }

    @Override
    public boolean isFinished() {  
        return false;
    }

    /*@Override
    public void end(boolean interrupted) {
        chute.stopChute();
    }*/

}