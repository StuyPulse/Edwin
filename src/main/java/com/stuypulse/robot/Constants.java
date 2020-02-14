/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.stuypulse.robot;

import com.stuypulse.stuylib.network.SmartNumber;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean constants. This class should not be used for any other
 * purpose. All constants should be declared globally (i.e. public static). Do
 * not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the constants are needed, to reduce verbosity.
 */
public interface Constants {

    /**
     * Lets us turn feet and inches into just feet for measurements
     * 
     * @param feet   feet
     * @param inches inches
     * @return value in feet
     */
    private static double toFeet(int feet, double inches) {
        return ((double) feet) + (inches / 12.0);
    }

    public interface Ports {

        public interface Gamepad {
            int OPERATOR = 0;
            int DRIVER = 1;
            int DEBUGGER = 2;
        }

        public interface Drivetrain {
            int LEFT_TOP = 3;
            int LEFT_MIDDLE = 4;
            int LEFT_BOTTOM = 5;

            int RIGHT_TOP = 6;
            int RIGHT_MIDDLE = 7;
            int RIGHT_BOTTOM = 8;

            int LEFT_ENCODER_A = -1;
            int LEFT_ENCODER_B = -1;
            int RIGHT_ENCODER_A = -1;
            int RIGHT_ENCODER_B = -1;

            int GEAR_SHIFT = 0;
        }
    }

    public interface DrivetrainSettings {
        // If speed is below this, use quick turn
        double QUICKTURN_THRESHOLD = 0.04;

        // How much to slow down quick turn
        double QUICKTURN_SPEED = 0.5;

        // Low Pass Filter and deadband for Driver Controls
        double SPEED_DEADBAND = 0.1;
        double ANGLE_DEADBAND = 0.1;

        double SPEED_FILTER = 0.5;
        double ANGLE_FILTER = 0.15;

        // Current Limit for the motors
        int CURRENT_LIMIT = 65;

        // Encoder Constants
        public interface Encoders {

            double WHEEL_DIAMETER = 0.5;
            double WHEEL_CIRCUMFERENCE = WHEEL_DIAMETER * Math.PI;
            
            // Ratio of the smaller gear to the larger gear
            double OUTER_GEAR_RATIO = 24.0 / 60.0;

            // The difference between theoretical and actual distance
            double REAL_YIELD = 1.3;
            
            double GREYHILL_PULSES_PER_REVOLUTION = 256 * 4.0;
            double GREYHILL_FEET_PER_PULSE = ((WHEEL_CIRCUMFERENCE * OUTER_GEAR_RATIO) / GREYHILL_PULSES_PER_REVOLUTION) * REAL_YIELD;

            double NEO_DISTANCE_PER_ROTATION = 1.0; // Not Correct
        }
    }

    public interface Alignment {

        double MIN_ALIGNMENT_TIME = 0.25;
        double MAX_ALIGNMENT_TIME = 7.5;
        
        public interface Speed {
            // Preset PID Values
            SmartNumber P = new SmartNumber("SpeedP", 0.1);
            SmartNumber I = new SmartNumber("SpeedI", 0.01);
            SmartNumber D = new SmartNumber("SpeedD", 0.025);

            // Bang Bang speed when measuring PID Values 
            // [whatever you want, but 0.75 is nice]
            double BANGBANG_SPEED = 0.75;

            // Low Pass Filter Time Constant for controller
            double IN_SMOOTH_FILTER = 0.0;
            double OUT_SMOOTH_FILTER = 0.1;

            // What is an acceptable error
            double MAX_SPEED_ERROR = toFeet(0, 2);
            double MAX_SPEED_VEL = toFeet(0, 3);
        }

        public interface Angle {
            // Preset PID Values
            SmartNumber P = new SmartNumber("AngleP", 0.055);
            SmartNumber I = new SmartNumber("AngleI", 0.01);
            SmartNumber D = new SmartNumber("AngleD", 0.005);

            // Bang Bang speed when measuring PID Values 
            // [whatever you want, but 0.75 is nice]
            double BANGBANG_SPEED = 0.75;

            // Low pass Filter Time Constant for controller
            double IN_SMOOTH_FILTER = 0.00;
            double OUT_SMOOTH_FILTER = 0.05;

            // What is an acceptable error
            double MAX_ANGLE_ERROR = 2.0;
            double MAX_ANGLE_VEL = 0.5;
        }

        public interface Measurements {

            double GOAL_HEIGHT = toFeet(7, 6);

            public interface Limelight {
                double HEIGHT = toFeet(2, 7);
                double DISTANCE = toFeet(0, 0);
                double PITCH = 17.3;
                double YAW = 0.0;
            }
        }
    }

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
    // TODO: Test speeds
    double FUNNEL_SPEED = 0.5;
    double UNFUNNEL_SPEED = -FUNNEL_SPEED;

    /*********************************************************************************************
     * Climber Constants
     *********************************************************************************************/
    // TODO: Test speeds
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
    

    //TODO check all values for correctlynessly
    /*********************************************************************************************
     * Movement Auton Command       
     *********************************************************************************************/
    int DISTANCE_TO_MOVE_AT_START = 5;

    /*********************************************************************************************
     * Shoot Three (At Start) Auton Command
     *********************************************************************************************/
    int SHOOT_FROM_START_TO_GOAL = 10;

    /*********************************************************************************************
     * Shoot at start and take 3 balls from trench
     *********************************************************************************************/
    double ANGLE_FROM_START_TO_TRENCH = 37.7;
    double DISTANCE_FROM_START_TO_TRENCH = 109.4;
    double DISTANCE_FROM_BALL_TO_BALL = 36;
    double DISTANCE_FROM_TRENCH_TO_GOAL = 20;

    /*********************************************************************************************
     * Shoot three at start, get 3 balls from trench, and then take 2 balls from rdvs
     *********************************************************************************************/
    double ANGLE_FROM_TRENCH_TO_RDVS = 125.88;
    double DISTANCE_FROM_TRENCH_TO_RDVS = 109.85;
    double ANGLE_FROM_RDVS_TO_TWO_BALL = 25; //estimation between 0 - 54.12
    double DISTANCE_BETWEEN_TWO_BALL = 16.57;
    double DISTANCE_FROM_RDVS_TO_INTERSECTION_BEWTWEEN_TWO_BALL_AND_GOAL = 40; //estimation according to field markings


    /*********************************************************************************************
     * Shoot three at start and get 3 balls from rdvs
     *********************************************************************************************/

    double DISTANCE_FROM_START_TO_RDVS = 107.83;
    double ANGLE_FROM_START_POINT_TO_THREE_BALL = 247.5; //estimation from common knowledge
    double DISTANCE_FOR_THREE_BALLS_IN_RDVS = 36; //estimate. Probably higher


    /*********************************************************************************************
     * Shoot three at start, and then get 5 balls from rdvs
     *********************************************************************************************/
    
    double ANGLE_FROM_THREE_BALL_TO_TWO_BALL = 90;
    double DISTANCE_FROM_THREE_BALL_TO_TWO_BALL = 25.42;

}
