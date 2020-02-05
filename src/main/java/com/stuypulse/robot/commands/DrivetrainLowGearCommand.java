package com.stuypulse.robot.commands;

import com.stuypulse.robot.subsystems.Drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;

public abstract class DrivetrainLowGearCommand extends CommandBase {

    private Drivetrain mDrivetrain;

    public DrivetrainLowGearCommand(Drivetrain drivetrain) {
        mDrivetrain = drivetrain;
    }

    public void initialize() {
        mDrivetrain.setLowGear();
    }

    public boolean isFinished() {
        return true;
    }

}