package com.stuypulse.robot.commands;

import com.stuypulse.robot.subsystems.Drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;

public abstract class DrivetrainLowGearCommand extends CommandBase {

    private Drivetrain drivetrain;

    public DrivetrainLowGearCommand(Drivetrain drivetrain) {
        this.drivetrain = drivetrain;
    }

    public void initialize() {
        drivetrain.setLowGear();
    }

    public boolean isFinished() {
        return true;
    }

}