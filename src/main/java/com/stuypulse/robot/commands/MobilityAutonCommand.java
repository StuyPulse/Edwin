package com.stuypulse.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;

import com.revrobotics.CANSparkMax;
import com.stuypulse.robot.subsystems.Drivetrain;

public class MobilityAutonCommand extends CommandBase {
    Subsystem drivetrain;
    public MobilityAutonCommand(Subsystem subsystem) {
        drivetrain = subsystem;
    }

    
}