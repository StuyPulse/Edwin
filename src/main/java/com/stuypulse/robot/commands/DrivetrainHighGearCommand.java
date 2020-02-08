package com.stuypulse.robot.commands;

import com.stuypulse.robot.subsystems.Drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class DrivetrainHighGearCommand extends CommandBase {

    private Drivetrain drivetrain;

    public DrivetrainHighGearCommand(Drivetrain drivetrain) {
        this.drivetrain = drivetrain;
    }

    public void initialize() {
        this.drivetrain.setHighGear();
    }

    public boolean isFinished() {
        return true;
    }

}