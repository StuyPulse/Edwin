package com.stuypulse.robot.commands;

import com.stuypulse.robot.subsystems.Drivetrain;

import edu.wpi.first.wpilibj2.command.InstantCommand;

public class DrivetrainLowGearCommand extends InstantCommand {

    private Drivetrain drivetrain;

    public DrivetrainLowGearCommand(Drivetrain drivetrain) {
        this.drivetrain = drivetrain;
    }

    public void initialize() {
        drivetrain.setLowGear();
    }
}