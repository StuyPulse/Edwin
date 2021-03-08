package com.stuypulse.robot.commands.auton.routines;

import com.stuypulse.robot.subsystems.Drivetrain;
import com.stuypulse.robot.commands.DrivetrainRamseteCommand;

public class SlalomPathAutonCommand extends DrivetrainRamseteCommand {
    public SlalomPathAutonCommand(Drivetrain drivetrain) {
        super(
            drivetrain, "SlalomPath/Slalom.wpilib.json"
        ); 

        robotRelative(); 
    } 
    
}
