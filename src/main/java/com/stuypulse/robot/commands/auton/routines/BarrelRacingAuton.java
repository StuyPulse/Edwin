package com.stuypulse.robot.commands.auton.routines;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import com.stuypulse.robot.commands.DrivetrainRamseteCommand;
import com.stuypulse.robot.subsystems.Drivetrain;

/**
 * 
 * robot becomes sentient
 */
public class BarrelRacingAuton extends SequentialCommandGroup {

    private static final String BARREL_PATH = "BarrelRacingPath/BarrelRacing.wpilib.json";

    public BarrelRacingAuton(Drivetrain drivetrain) {
        addCommands(
            new DrivetrainRamseteCommand(drivetrain, BARREL_PATH).robotRelative()
        );
    }

} 
