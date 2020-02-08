package com.stuypulse.robot.commands.auton.routines;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import com.stuypulse.robot.Constants;
import com.stuypulse.robot.commands.DrivetrainMovementCommand;
import com.stuypulse.robot.subsystems.Drivetrain;

public class MobilityAutonCommand extends SequentialCommandGroup {
    public MobilityAutonCommand(Drivetrain drivetrain) {
        //Constructors
        addCommands(new DrivetrainMovementCommand(drivetrain, 0,Constants.DISTANCE_FROM_START_TO_GOAL));
    }


}