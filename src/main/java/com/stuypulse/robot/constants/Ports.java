/************************ PROJECT PHIL ************************/
/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved.*/
/* This work is licensed under the terms of the MIT license.  */
/**************************************************************/

package com.stuypulse.robot.constants;

/** This file contains the different ports of motors, solenoids and sensors */
public interface Ports {
    public interface Gamepad {
        int DRIVER = 0;
        int OPERATOR = 1;
        int DEBUGGER = 2;
    }

    public interface Shooter {
        int SHOOTER_A = 12;
        int SHOOTER_B = 13;
        int SHOOTER_C = 14;
        int FEEDER = 11;
        int HOOD = 1;
    }

    public interface Drivetrain {
        int LEFT_TOP = 7;
        int LEFT_BOTTOM = 6;

        int RIGHT_TOP = 4;
        int RIGHT_BOTTOM = 3;

        int GEAR_SHIFT = 0;
    }

    public interface Intake {
        int MOTOR_PORT = 18;
        int SOLENOID_PORT_A = 4;
        int SOLENOID_PORT_B = 5;
        int SENSOR_PORT = 7;
    }

    public interface Conveyor {
        int LIFT_MOTOR_PORT = 8;
        int FUNNEL = 9;

        int LOWER_SENSOR_PORT = 5;
        int UPPER_SENSOR_PORT = 6;
    }
}
