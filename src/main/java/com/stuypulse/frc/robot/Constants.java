/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.stuypulse.frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public interface Constants {

    /*********************************************************************************************
     * Shooter Motor Ports
     *********************************************************************************************/
    int LEFT_SHOOTER_MOTOR_PORT = -1;
    int RIGHT_SHOOTER_MOTOR_PORT = -1;
    int MIDDLE_SHOOTER_MOTOR_PORT = -1;
    
    /*********************************************************************************************
     * Feeder Motor Port
     *********************************************************************************************/
    int FEEDER_MOTOR_PORT = -1;

    /*********************************************************************************************
     * Hood Solenoid Port
     *********************************************************************************************/
    int HOOD_SOLENOID_PORT = -1;

    /*********************************************************************************************
     * PID values
     *********************************************************************************************/
    double kP = -1;
    double kI = -1;
    double kD = -1;
    
}