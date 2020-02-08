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
     * Gamepad Ports
     *********************************************************************************************/
    int DRIVER_GAMEPAD_PORT = 0;
    int OPERATOR_GAMEPAD_PORT = 1;

    /*********************************************************************************************
     * Drivetrain Motor Ports
     *********************************************************************************************/
    //Left Side Motors
    int DRIVETRAIN_LEFT_TOP_MOTOR_PORT = -1;
    int DRIVETRAIN_LEFT_MIDDLE_MOTOR_PORT = -1;
    int DRIVETRAIN_LEFT_BOTTOM_MOTOR_PORT = -1;
    //Right Side Motors
    int DRIVETRAIN_RIGHT_TOP_MOTOR_PORT = -1;
    int DRIVETRAIN_RIGHT_MIDDLE_MOTOR_PORT = -1;
    int DRIVETRAIN_RIGHT_BOTTOM_MOTOR_PORT = -1;
    
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
     * Co1or Wheel Ports
     *********************************************************************************************/
    int CONTROL_PANEL_MOTOR_PORT = -1;
    int CONTROL_SENSOR_PORT = -1;

    /*********************************************************************************************
     * Funnel Constants
     *********************************************************************************************/
    //TODO: Test
    double FUNNEL_SPEED = 0.5;
    double UNFUNNEL_SPEED = -FUNNEL_SPEED;

    double FUNNEL_ENCODER_APPROACH_STALL_THRESHOLD = 3.0;

    /*********************************************************************************************
     * Climber Constants
     *********************************************************************************************/
    //TODO: Test speeds
    double CLIMB_UP_SPEED = 0.5;
    double CLIMB_DOWN_SPEED = -CLIMB_UP_SPEED;
    
     /*********************************************************************************************
     * Co1or Wheel Constants
     *********************************************************************************************/
    double CONTROL_PANEL_TURN_SPEED = 1.0;
    
    double CYAN_RED = 0.2;
    double CYAN_GREEN = 0.56;
    double CYAN_BLUE = 0.3;
    
    double GREEN_RED = 0.25;
    double GREEN_GREEN = 0.65;
    double GREEN_BLUE = 0.17;

    double RED_RED = 0.60;
    double RED_GREEN = 0.35;
    double RED_BLUE = 0.1;

    double YELLOW_RED = 0.30;
    double YELLOW_GREEN = 0.50;
    double YELLOW_BLUE = 0.1;



     /*********************************************************************************************
     * Intake Motor Ports
     *********************************************************************************************/
    int INTAKE_MOTOR_PORT = -1;
    int INTAKE_SOLENOID_PORT = -1;
    }
