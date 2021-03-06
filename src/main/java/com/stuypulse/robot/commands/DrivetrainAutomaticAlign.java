package com.stuypulse.robot.commands;

import com.stuypulse.robot.subsystems.Drivetrain;
import com.stuypulse.robot.subsystems.Shooter;

public class DrivetrainAutomaticAlign extends DrivetrainGoalCommand {

    public DrivetrainAutomaticAlign(Drivetrain drivetrain, Shooter shooter) {
        super(
            drivetrain,
            new Number() {
                public int intValue() {
                    return shooter.getMode().distance.intValue();
                }
                public long longValue() {
                    return shooter.getMode().distance.longValue();
                }
                public float floatValue() {
                    return shooter.getMode().distance.floatValue();
                }
                public double doubleValue() {
                    return shooter.getMode().distance.doubleValue();
                }
            }
        );
    }

}
