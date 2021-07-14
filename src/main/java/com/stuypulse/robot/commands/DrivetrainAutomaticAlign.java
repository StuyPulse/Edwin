package com.stuypulse.robot.commands;

import com.stuypulse.robot.subsystems.Drivetrain;
import com.stuypulse.robot.subsystems.Shooter;

public class DrivetrainAutomaticAlign extends DrivetrainGoalCommand {

    public DrivetrainAutomaticAlign(Drivetrain drivetrain, Shooter shooter) {
        super(
            drivetrain,
            shooter.getMode().distance
        );
    }

}
