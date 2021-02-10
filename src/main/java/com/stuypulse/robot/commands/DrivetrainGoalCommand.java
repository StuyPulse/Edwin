package com.stuypulse.robot.commands;

import com.stuypulse.robot.Constants;
import com.stuypulse.robot.subsystems.Drivetrain;
/**
 * Command that aligns with the target
 */
public class DrivetrainGoalCommand extends DrivetrainAlignmentCommand {

    /**
     * @param drivetrain drivetrain to do movements on
     * @param distance distance to move from the target
     * @param innerGoal if we should aim for the inner goal
     */
    public DrivetrainGoalCommand(Drivetrain drivetrain, double distance) {
        super(drivetrain, new DrivetrainGoalAligner(distance));
        setMaxSpeed(Constants.Alignment.Speed.LIMELIGHT_MAX_SPEED);
        useInterpolation();
        useMinTime();
    }

}