package com.stuypulse.robot.commands;

import com.stuypulse.robot.subsystems.Drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class DrivetrainStopCommand extends CommandBase {

    private Drivetrain drivetrain;

    public DrivetrainStopCommand(Drivetrain drivetrain) {
        this.drivetrain = drivetrain;
    }

    public void initialize() {
        this.drivetrain.stop();
    }

    public boolean isFinished() {
        return true;
    }

}