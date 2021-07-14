package com.stuypulse.robot.commands;

import com.stuypulse.robot.subsystems.Drivetrain;
import com.stuypulse.robot.subsystems.Shooter;

public class DrivetrainAutomaticAlign extends DrivetrainGoalCommand {

    private static class ShooterAligner extends DrivetrainGoalAligner {

        // holds the shooter because the shooter mode / distance changes
        private final Shooter shooter;

        public ShooterAligner(Shooter shooter) {
            super(0); // default distance of zero
            this.shooter = shooter;
        }

        private Number getUpdatedDistance() {
            return this.shooter.getMode().distance; 
        }

        public void init() {
            super.init(); // turns on the limelight
            this.distance = getUpdatedDistance();
        }

    }

    // This class is no longer necessary, as one could technically make a GoalAlignment 
    // command with just a shooter (if the simple code changes are made). However this 
    // class was already used so for now it's easier
    public DrivetrainAutomaticAlign(Drivetrain drivetrain, Shooter shooter) {
        super(drivetrain, new ShooterAligner(shooter));
    }

}
