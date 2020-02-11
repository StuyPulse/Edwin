/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.stuypulse.robot;

import edu.wpi.first.wpilibj.util.Color;
import com.revrobotics.ColorMatch;

import com.stuypulse.stuylib.control.PIDController;
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

<<<<<<< HEAD
      /*********************************************************************************************
     * Gamepad Ports
     *********************************************************************************************/
    int DRIVER_GAMEPAD_PORT = 0;
    int OPERATOR_GAMEPAD_PORT = 1;
=======
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
            int DRIVER = 0;
            int OPERATOR = 1;
            int DEBUGGER = 2;
        }

        public interface Drivetrain {
            int LEFT_TOP = 7;
            int LEFT_BOTTOM = 6;

            int RIGHT_TOP = 4;
            int RIGHT_BOTTOM = 3;

            int LEFT_ENCODER_A = -1; // TODO: find value
            int LEFT_ENCODER_B = -1; // TODO: find value
            int RIGHT_ENCODER_A = -1; // TODO: find value
            int RIGHT_ENCODER_B = -1; // TODO: find value

            int GEAR_SHIFT = 0;
        }
    }

    public interface DrivetrainSettings {
        // If speed is below this, use quick turn
        double QUICKTURN_THRESHOLD = 0.04; 

        // How much to slow down quick turn
        double QUICKTURN_SPEED = 0.5; // TODO: Go Over This With Driver

        // Low Pass Filter and deadband for Driver Controls
        double SPEED_DEADBAND = 0.1;
        double ANGLE_DEADBAND = 0.1;

        double SPEED_POWER = 1.0;
        double ANGLE_POWER = 1.0;

        double SPEED_FILTER = 0.5;  // TODO: Go Over This With Driver
        double ANGLE_FILTER = 0.15; // TODO: Go Over This With Driver

        int SPEED_ORDER = 1; // TODO: Go Over This With Driver
        int ANGLE_ORDER = 1; // TODO: Go Over This With Driver
        
        // Current Limit for the motors
        int CURRENT_LIMIT = 40; // TODO: ask about this

        // Encoder Constants
        public interface Encoders {

            double WHEEL_DIAMETER = 0.5;
            double WHEEL_CIRCUMFERENCE = WHEEL_DIAMETER * Math.PI;
            
            // Ratio of the smaller gear to the larger gear
            double OUTER_GEAR_RATIO = 24.0 / 60.0;

            // The difference between theoretical and actual distance
            SmartNumber REAL_YIELD = new SmartNumber("Greyhill Yeild", 1.3);
            
            double GREYHILL_PULSES_PER_REVOLUTION = 256 * 4.0;
            double GREYHILL_FEET_PER_PULSE = ((WHEEL_CIRCUMFERENCE * OUTER_GEAR_RATIO) / GREYHILL_PULSES_PER_REVOLUTION) * REAL_YIELD.doubleValue();

            double NEO_DISTANCE_PER_ROTATION = WHEEL_CIRCUMFERENCE; // TODO: Calculate if Important
        }
    }

    public interface Alignment {

        // TODO: find better values for this
        double MIN_ALIGNMENT_TIME = 0.25;
        double MAX_ALIGNMENT_TIME = 7.5;
        
        // TODO: test (0.6, 1.2, 3.0/40.0) for PID, or (0.8, 0.0, 0.1) for PD if it works better
        SmartNumber AUTOTUNE_P = new SmartNumber("Auto Tune P", 0.6);
        SmartNumber AUTOTUNE_I = new SmartNumber("Auto Tune I", 1.2);
        SmartNumber AUTOTUNE_D = new SmartNumber("Auto Tune D", 3.0 / 40.0);

        public interface Speed {
            // Preset PID Values
            SmartNumber P = new SmartNumber("SpeedP", 0.1);   // TODO: find value 
            SmartNumber I = new SmartNumber("SpeedI", 0.01);  // TODO: find value 
            SmartNumber D = new SmartNumber("SpeedD", 0.025); // TODO: find value 

            // Get PID Controller
            public static PIDController getPID() {
                return new PIDController(P.get(), I.get(), D.get());
            }

            // Bang Bang speed when measuring PID Values 
            // [whatever you want, but 0.7 is nice]
            double BANGBANG_SPEED = 0.7;

            // Low Pass Filter Time Constant for controller
            SmartNumber IN_SMOOTH_FILTER = new SmartNumber("Speed In Filter", 0.05);
            SmartNumber OUT_SMOOTH_FILTER = new SmartNumber("Speed Out Filter", 0.1);

            // What is an acceptable error
            double MAX_SPEED_ERROR = toFeet(0, 4.0); // TODO: discuss value
            double MAX_SPEED_VEL = toFeet(0, 2.0); // TODO: discuss value
        }

        public interface Angle {
            // Preset PID Values
            SmartNumber P = new SmartNumber("AngleP", 0.055); // TODO: find value 
            SmartNumber I = new SmartNumber("AngleI", 0.01);  // TODO: find value 
            SmartNumber D = new SmartNumber("AngleD", 0.005); // TODO: find value 

            // Get PID Controller
            public static PIDController getPID() {
                return new PIDController(P.get(), I.get(), D.get());
            }
            
            // Bang Bang speed when measuring PID Values 
            // [whatever you want, but 0.7 is nice]
            double BANGBANG_SPEED = 0.7;

            // Low pass Filter Time Constant for controller
            SmartNumber IN_SMOOTH_FILTER = new SmartNumber("Angle In Filter", 0.05);
            SmartNumber OUT_SMOOTH_FILTER = new SmartNumber("Angle Out Filter", 0.1);

            // What is an acceptable error
            double MAX_ANGLE_ERROR = 5.0; // TODO: discuss value
            double MAX_ANGLE_VEL = 2.5; // TODO: discuss value
        }

        public interface Measurements {

            double GOAL_HEIGHT = toFeet(7, 6); // TODO: measure on feild

            public interface Limelight {
                double HEIGHT = toFeet(2, 7); // TODO: calculate on robot
                double DISTANCE = toFeet(0, 0);
                double PITCH = 17.3; // TODO: calculate on robot
                double YAW = 0.0;
            }
        }
    }
>>>>>>> master

    /*********************************************************************************************
     * Funnel Motor Port
     *********************************************************************************************/
    int FUNNEL_MOTOR_PORT = -1;

    /*********************************************************************************************
     * Climber Motor Ports
     *********************************************************************************************/
    int CLIMBER_LIFT_MOTOR_PORT = -1;
    int CLIMBER_YOYO_MOTOR_PORT = -1;

    int CLIMBER_LIFT_SOLENOID_CHANNEL = -1;

    int CLIMBER_LIMIT_SWITCH_CHANNEL = -1;

    /*********************************************************************************************
     * Climber Motor Constants
     *********************************************************************************************/
    double CLIMBER_MOVE_DEADBAND = 0.25;

    Color CYAN_TARGET = ColorMatch.makeColor(Constants.CYAN_RED, Constants.CYAN_GREEN, Constants.CYAN_BLUE);
    Color GREEN_TARGET = ColorMatch.makeColor(Constants.GREEN_RED, Constants.GREEN_GREEN, Constants.GREEN_BLUE);
    Color RED_TARGET = ColorMatch.makeColor(Constants.RED_RED, Constants.RED_GREEN, Constants.RED_BLUE);
    Color YELLOW_TARGET = ColorMatch.makeColor(Constants.YELLOW_RED, Constants.YELLOW_GREEN, Constants.YELLOW_BLUE);

    double COLOR_SENSOR_SPEED = 1;

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
<<<<<<< HEAD
    //TODO: Test speeds
    double MOVE_LIFT_UP_SPEED = 0.5;
    double MOVE_LIFT_DOWN_SPEED = -0.5;
    double CLIMBER_SETUP_WAIT_TIME = 0.1;
    double CLIMBER_SCALE = 0.5;

=======
    // TODO: Test speeds
    double CLIMB_UP_SPEED = 0.5;
    double CLIMB_DOWN_SPEED = -CLIMB_UP_SPEED;
    
>>>>>>> master
     /*********************************************************************************************
     * Intake Motor Ports
     *********************************************************************************************/
    int INTAKE_MOTOR_PORT = -1;
    int INTAKE_SOLENOID_PORT = -1;

    /*********************************************************************************************
     * Co1or Wheel Ports
     *********************************************************************************************/
    int CONTROL_PANEL_MOTOR_PORT = -1;
    int CONTROL_SENSOR_PORT = -1;

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

<<<<<<< HEAD
}
=======


    /*********************************************************************************************
     * Intake Motor Ports
     *********************************************************************************************/
    int INTAKE_MOTOR_PORT = -1;
    int INTAKE_SOLENOID_PORT = -1;
    }
>>>>>>> master
