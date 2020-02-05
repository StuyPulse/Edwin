package com.stuypulse.robot.commands;

import com.stuypulse.robot.subsystems.Drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;

public abstract class DrivetrainHighGearCommand extends CommandBase {

    private Drivetrain mDrivetrain;

    public DrivetrainHighGearCommand(Drivetrain drivetrain) {
        mDrivetrain = drivetrain;
    }

    public void initialize() {
        mDrivetrain.setHighGear();
    }

    public boolean isFinished() {
        return true;
    }

}