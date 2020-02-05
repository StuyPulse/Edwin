package com.stuypulse.robot.commands;

import com.stuypulse.robot.subsystems.Drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;

public abstract class DrivetrainStopCommand extends CommandBase {

    private Drivetrain mDrivetrain;

    public DrivetrainStopCommand(Drivetrain drivetrain) {
        mDrivetrain = drivetrain;
    }

    public void initialize() {
        mDrivetrain.stop();
    }

    public boolean isFinished() {
        return true;
    }

}