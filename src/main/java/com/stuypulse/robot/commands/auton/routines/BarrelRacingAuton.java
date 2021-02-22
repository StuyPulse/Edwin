package com.stuypulse.robot.commands.auton.routines;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
      
public class BarrelRacingAuton extends SequentialCommandGroup {

    private static final String BARREL_PATH = "BarrelRacingPath/output/BarrelRacing.wpilib.json";

    public BarrelRacingAuton(Drivetrain drivetrain) {
        addCommands(
            // new DrivetrainRamseteCommand(drivetrain, BARREL_PATH),
            // new DrivetrainRamseteCommand(drivetrain, BARREL_PATH),
            // new DrivetrainRamseteCommand(drivetrain, BARREL_PATH),
            new DrivetrainRamseteCommand(drivetrain, BARREL_PATH)
        );
    }

} 
