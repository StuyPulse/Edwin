package com.stuypulse.robot.commands.auton.routines;

import com.stuypulse.robot.subsystems.Drivetrain;

import edu.wpi.first.wpilibj2.Filesystem.getDeployDirectory;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class SlalomPathAutonCommand extends DrivetrainRamseteCommand {

    private Drivetrain drivetrain;
    
    public SlalomPathAutonCommand(Drivetrain drivetrain) {
        // idk
        // anthony said something like Filesystem.getDeployDirectory
        // ohhh
        //I feel useless- Eric
        /* 
        WHAT THIS IS:
        If you look on the side there's a folder called "deploy"
        I believe Filesystem.getDeployDirectory just gets inserts the path to the deploy folder before whatever
        string you input.
        Then all you have to do is 
        wrt
        
        */
        // apparently you have to put the super statement in the beginning of the constructor
        // or else it will throw an error
        // henglo
        super(
            drivetrain, Filesystem.getDeployDirectory("SlalomPath/Slalom.wpilib.json").robotRelative()
        );
    } 
}
