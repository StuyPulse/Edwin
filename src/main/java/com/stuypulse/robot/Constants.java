/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.stuypulse.robot;

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
     * Drivetrain Ports
     *********************************************************************************************/
    //Left Side Motors
    int DRIVETRAIN_LEFT_TOP_MOTOR_PORT = -1;
    int DRIVETRAIN_LEFT_MIDDLE_MOTOR_PORT = -1;
    int DRIVETRAIN_LEFT_BOTTOM_MOTOR_PORT = -1;
    //Right Side Motors
    int DRIVETRAIN_RIGHT_TOP_MOTOR_PORT = -1;
    int DRIVETRAIN_RIGHT_MIDDLE_MOTOR_PORT = -1;
    int DRIVETRAIN_RIGHT_BOTTOM_MOTOR_PORT = -1;
    //Sensors
    int DRIVETRAIN_RIGHT_ENCODER_CHANNEL_A = -1;
    int DRIVETRAIN_RIGHT_ENCODER_CHANNEL_B = -1;
    int DRIVETRAIN_LEFT_ENCODER_CHANNEL_A = -1;
    int DRIVETRAIN_LEFT_ENCODER_CHANNEL_B = -1;

    /*********************************************************************************************
     * Funnel Motor Port
     *********************************************************************************************/
    int FUNNEL_MOTOR_PORT = -1;

    /*********************************************************************************************
     * Climber Motor Ports
     *********************************************************************************************/
    int CLIMBER_LIFT_MOTOR_PORT = -1;
    int CLIMBER_YOYO_MOTOR_PORT = -1;

    /*********************************************************************************************
     * Drivetrain Constants
     *********************************************************************************************/
    //Motor Constants
    double WHEEL_DIAMETER = -1;
    double WHEEL_INCHES_PER_REVOLUTION = WHEEL_DIAMETER * Math.PI;

    //Greyhill Constants
    double GREYHILL_PULSES_PER_REVOLUTION = 256 * 4.0;
    double GREYHILL_INCHES_PER_PULSE = WHEEL_INCHES_PER_REVOLUTION / GREYHILL_PULSES_PER_REVOLUTION;

    /*********************************************************************************************
     * Funnel Constants
     *********************************************************************************************/
    //TODO: Test speeds
    double FUNNEL_SPEED = 0.5;
    double UNFUNNEL_SPEED = -FUNNEL_SPEED;

    /*********************************************************************************************
     * Climber Constants
     *********************************************************************************************/
    //TODO: Test speeds
    double CLIMB_UP_SPEED = 0.5;
    double CLIMB_DOWN_SPEED = -CLIMB_UP_SPEED;

     /*********************************************************************************************
     * Intake Motor Ports
     *********************************************************************************************/
    int INTAKE_MOTOR_PORT = -1;
    int INTAKE_SOLENOID_PORT = -1;
}
