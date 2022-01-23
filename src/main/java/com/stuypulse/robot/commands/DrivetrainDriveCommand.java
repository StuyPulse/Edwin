/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.stuypulse.robot.commands;

import com.stuypulse.stuylib.input.Gamepad;
import com.stuypulse.stuylib.math.SLMath;
import com.stuypulse.stuylib.network.SmartBoolean;
import com.stuypulse.stuylib.network.SmartNumber;
import com.stuypulse.stuylib.streams.FilteredIStream;
import com.stuypulse.stuylib.streams.IStream;
import com.stuypulse.stuylib.streams.filters.*;

import edu.wpi.first.wpilibj2.command.CommandBase;

import com.stuypulse.robot.Constants.DrivetrainSettings;
import com.stuypulse.robot.subsystems.Drivetrain;

/**
 * ----- PLEASE READ -----
 * This is an example implementation of the drivetrain drive command.
 * 
 * This is pretty much what we use for competitions. 
 * 
 * This version includes the use of Filters.
 * 
 * Filters are complicated, but can be used to make driving much smooter and more reliable.
 */
public class DrivetrainDriveCommand extends CommandBase {

    private Drivetrain drivetrain;
    private Gamepad driver;

    private SmartBoolean tankMode = new SmartBoolean("Driver Settings/Tank Mode", false);
    private SmartNumber tankFilter = new SmartNumber("Driver Settings/Tank Filter", 0.04);

    // These filters help smooth out driving
    // But they are also optional
    private IFilter speedFilter = new LowPassFilter(DrivetrainSettings.SPEED_FILTER);
    private IFilter turnFilter = new LowPassFilter(DrivetrainSettings.ANGLE_FILTER);

    private IFilter leftFilter = new LowPassFilter(tankFilter);
    private IFilter righFilter = new LowPassFilter(tankFilter);

    public DrivetrainDriveCommand(Drivetrain subsystem, Gamepad gamepad) {
        drivetrain = subsystem;
        driver = gamepad;
        
        // This makes sure that two commands that need the same subsystem dont mess eachother up. 
        // Example, if a command activated by a button needs to take control away from a default command.
        addRequirements(subsystem);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
    }

    // Called 50 times a second if the robot is running
    @Override
    public void execute() {
        if(driver.getRawRightButton()) {
            drivetrain.setLowGear();
        } else {
            drivetrain.setHighGear();
        }

        if(tankMode.get()) {
            speedFilter.get(0);
            turnFilter.get(0);

            double left = leftFilter.get(-driver.getLeftY());
            double right = righFilter.get(-driver.getRightY());

            drivetrain.tankDrive(left, right);
        } else {
            leftFilter.get(0);
            righFilter.get(0);

            double speed = driver.getRightTrigger() - driver.getLeftTrigger();
            double turn = driver.getLeftX();
    
            speed = speedFilter.get(speed);
            turn = turnFilter.get(turn);
    
            drivetrain.curvatureDrive(speed, turn);
        }
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
