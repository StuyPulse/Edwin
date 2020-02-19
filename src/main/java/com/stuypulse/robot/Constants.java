
  
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

    /**
     * Lets us turn feet and inches into just feet for measurements
     * 
     * @param feet   feet
     * @param inches inches
     * @return value in feet
     */
    public static double toFeet(int feet, double inches) {
        return ((double) feet) + (inches / 12.0);
    }

    /**
     * Lets us turn feet and inches into just feet for measurements
     * 
     * @param inches inches
     * @return value in feet
     */
    public static double toFeet(double inches) {
        return toFeet(0, inches);
    }

    public interface Pneumatics {
        int ANALOG_PRESSURE_SWITCH_PORT = 0;
        double ANALOG_PRESSURE_SWITCH_VOLTAGE_SUPPLY = 5.0;
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

            int LEFT_ENCODER_A = 0;
            int LEFT_ENCODER_B = 1;
            int RIGHT_ENCODER_A = 2;
            int RIGHT_ENCODER_B = 3;

            int GEAR_SHIFT = 0;
        }

        int HOOD_SOLENOID = 1;

        public interface Shooter {
            int LEFT = 12;
            int MIDDLE = 13;
            int RIGHT = 14;
            
            int FEEDER = 11;
        }
    }

    public interface DrivetrainSettings {
        // If speed is below this, use quick turn
        double QUICKTURN_THRESHOLD = 0.05; 

        // How much to slow down quick turn
        double QUICKTURN_SPEED = 0.5; // TODO: Go Over This With Driver

        // Cool Rumble
        boolean COOL_RUMBLE = true;
        double COOL_RUMBLE_MAG = 1;

        // Low Pass Filter and deadband for Driver Controls
        double SPEED_DEADBAND = 0.1;
        double ANGLE_DEADBAND = 0.1;

        double SPEED_POWER = 1.0;
        double ANGLE_POWER = 1.0;

        double SPEED_FILTER = 0.5; 
        double ANGLE_FILTER = 0.1;

        int SPEED_ORDER = 1;
        int ANGLE_ORDER = 1;
        
        // Current Limit for the motors
        int CURRENT_LIMIT = 40; 

        // If the motors are inverted
        boolean IS_INVERTED = true;

        // Encoder Constants
        public interface Encoders {

            double WHEEL_DIAMETER = 0.5;
            double WHEEL_CIRCUMFERENCE = WHEEL_DIAMETER * Math.PI;
            
            // Ratio of the smaller gear to the larger gear
            double OUTER_GEAR_RATIO = 24.0 / 60.0;

            // The difference between theoretical and actual distance
            double REAL_YIELD = 1.0;
            
            double GREYHILL_PULSES_PER_REVOLUTION = 256 * 4.0;
            double GREYHILL_FEET_PER_PULSE = ((WHEEL_CIRCUMFERENCE * OUTER_GEAR_RATIO) / GREYHILL_PULSES_PER_REVOLUTION) * REAL_YIELD;

            double NEO_DISTANCE_PER_ROTATION = WHEEL_CIRCUMFERENCE;
        }
    }

    public interface Alignment {
        double TRENCH_DISTANCE = toFeet(242);
        double INITATION_LINE_DISTANCE = toFeet(91.5);

        double MIN_ALIGNMENT_TIME = 0.5;
        double MAX_ALIGNMENT_TIME = 7.5;
        
        SmartNumber AUTOTUNE_P = new SmartNumber("Auto Tune P", 0.6);
        SmartNumber AUTOTUNE_I = new SmartNumber("Auto Tune I", 1.2);
        SmartNumber AUTOTUNE_D = new SmartNumber("Auto Tune D", 3.0 / 40);

        public interface Speed {
            // Preset PID Values
            SmartNumber P = new SmartNumber("SpeedP", 0); 
            SmartNumber I = new SmartNumber("SpeedI", 0);
            SmartNumber D = new SmartNumber("SpeedD", 0);

            // Get PID Controller
            PIDController SPEED_CONTROLLER = new PIDController();

            public static PIDController getPID() {
                SPEED_CONTROLLER.setP(P.get());
                SPEED_CONTROLLER.setI(I.get());
                SPEED_CONTROLLER.setD(D.get());
                return SPEED_CONTROLLER;
            }

            // Bang Bang speed when measuring PID Values 
            double BANGBANG_SPEED = 0.3;

            // Low Pass Filter Time Constant for controller
            SmartNumber IN_SMOOTH_FILTER = new SmartNumber("Speed In Filter", 0.06);
            SmartNumber OUT_SMOOTH_FILTER = new SmartNumber("Speed Out Filter", 0.2);

            // What is an acceptable error
            double MAX_SPEED_ERROR = toFeet(3.0);
            double MAX_SPEED_VEL = toFeet(3.0);
        }

        public interface Angle {
            // Preset PID Values
            SmartNumber P = new SmartNumber("AngleP", 0);
            SmartNumber I = new SmartNumber("AngleI", 0);
            SmartNumber D = new SmartNumber("AngleD", 0);

            // Get PID Controller
            PIDController ANGLE_CONTROLLER = new PIDController();

            public static PIDController getPID() {
                ANGLE_CONTROLLER.setP(P.get());
                ANGLE_CONTROLLER.setI(I.get());
                ANGLE_CONTROLLER.setD(D.get());
                return ANGLE_CONTROLLER;
            }
            
            // Bang Bang speed when measuring PID Values 
            double BANGBANG_SPEED = 0.35;

            // Low pass Filter Time Constant for controller
            SmartNumber IN_SMOOTH_FILTER = new SmartNumber("Angle In Filter", 0.02);
            SmartNumber OUT_SMOOTH_FILTER = new SmartNumber("Angle Out Filter", 0.1);

            // What is an acceptable error
            double MAX_ANGLE_ERROR = 5.0;
            double MAX_ANGLE_VEL = 3.0;
        }

        public interface Measurements {

            double GOAL_HEIGHT = toFeet(7, 5);

            public interface Limelight {
                double HEIGHT = toFeet(2, 10);
                double DISTANCE = toFeet(0, 0);
                double PITCH = 20;
                double YAW = 0.0;
            }
        }
    }

    public interface Shooting {

        double GEAR = 2.0 / 3.0; 
    
        double INITATION_LINE_RPM = 2075;
        double TRENCH_RPM = 3000;
        double FAR_RPM = 5500.0 * GEAR;

        double TOLERANCE = 100;

        double SECONDS_TO_SPEED = 2;
        double TARGET_VEL_RATE_LIMIT = TRENCH_RPM / (50.0 * SECONDS_TO_SPEED);

        public interface Shooter {
            double MAX_RPM = 5600.0 * GEAR;
        
            SmartNumber P = new SmartNumber("Shooter P", 0.001148);
            SmartNumber I = new SmartNumber("Shooter I", 0.003382);
            SmartNumber D = new SmartNumber("Shooter D", 0.000097);
            SmartNumber FF = new SmartNumber("Shooter FF", 0.00019);
        
            double BANGBANG_SPEED = 0.1;
        }
    
        public interface Feeder {
            double SPEED_MUL = 1.0;

            double MAX_RPM = 5600.0 * GEAR;
        
            SmartNumber P = new SmartNumber("Feeder P", 0.000972);
            SmartNumber I = new SmartNumber("Feeder I", 0.002740);
            SmartNumber D = new SmartNumber("Feeder D", 0.000086);
            SmartNumber FF = new SmartNumber("Feeder FF", 0.000195);
        
            double BANGBANG_SPEED = 0.1;
        }
    }

    /*********************************************************************************************
     * Funnel Motor Port
     *********************************************************************************************/
    int FUNNEL_MOTOR_PORT = 9;

    /*********************************************************************************************
     * Climber Motor Ports
     *********************************************************************************************/
    int CLIMBER_LIFT_MOTOR_PORT = 15;
    int CLIMBER_YOYO_MOTOR_PORT = 17;

    int CLIMBER_LIFT_SOLENOID_CHANNEL = 2;
    int CLIMBER_YOYO_SOLENOID_CHANNEL = 3;

    int CLIMBER_LIMIT_SWITCH_CHANNEL = -1;

    /*********************************************************************************************
     * Climber Motor Constants
     *********************************************************************************************/
    double CLIMBER_MOVE_DEADBAND = 0.25;

    double CLIMBER_EXPONENT = 1/3;

    /*********************************************************************************************
     * Funnel Constants
     *********************************************************************************************/
    //TODO: Test
    double FUNNEL_SPEED = 0.2;
    double UNFUNNEL_SPEED = -FUNNEL_SPEED;

    double FUNNEL_ENCODER_APPROACH_STALL_THRESHOLD = 3.0;

    /*********************************************************************************************
     * Climber Constants
     *********************************************************************************************/
    //TODO: Test speeds
    double MOVE_LIFT_UP_SPEED = 1.0;
    double MOVE_LIFT_DOWN_SPEED = -1.0;
    double CLIMBER_SETUP_WAIT_TIME = 0.1;
    double CLIMBER_SCALE = 0.5;

    /*********************************************************************************************
     * Woof Ports
     *********************************************************************************************/
    int WOOF_MOTOR_PORT = 16;
    int WOOF_SENSOR_PORT = -1;

    /*********************************************************************************************
     * Control Panel Constants
     *********************************************************************************************/
    double WOOF_TURN_SPEED = 1.0;
    
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
    
    Color CYAN_TARGET = ColorMatch.makeColor(Constants.CYAN_RED, Constants.CYAN_GREEN, Constants.CYAN_BLUE);
    Color GREEN_TARGET = ColorMatch.makeColor(Constants.GREEN_RED, Constants.GREEN_GREEN, Constants.GREEN_BLUE);
    Color RED_TARGET = ColorMatch.makeColor(Constants.RED_RED, Constants.RED_GREEN, Constants.RED_BLUE);
    Color YELLOW_TARGET = ColorMatch.makeColor(Constants.YELLOW_RED, Constants.YELLOW_GREEN, Constants.YELLOW_BLUE);

    /*********************************************************************************************
     * Intake Motor Ports
     *********************************************************************************************/
    int INTAKE_MOTOR_PORT = 18;
    int INTAKE_SOLENOID_PORT_A = 6;
    int INTAKE_SOLENOID_PORT_B = 7;

    /*********************************************************************************************
     * Intake Constants
     *********************************************************************************************/    
    double INTAKE_MOTOR_SPEED = 1.0;

    /*********************************************************************************************
     * CHIMNEY Motor & Sensor Ports
     *********************************************************************************************/
  	int CHIMNEY_LIFT_MOTOR_PORT = 8;
	  int CHIMNEY_LOWER_SENSOR_PORT = 5;
    int CHIMNEY_UPPER_SENSOR_PORT = 6;
    
    /*********************************************************************************************
     * CHIMNEY Constants
     *********************************************************************************************/
	double CHIMNEY_LIFT_UP_SPEED = 1;
    double CHIMNEY_ENCODER_RADIUS = -1;
    double CHIMNEY_BALL_PER_ROTATIONS = -1;
    
    // AUTOS 

    //TODO check all values for correctlynessly
    /*********************************************************************************************
     * Movement Auton Command       
     *********************************************************************************************/
    double DISTANCE_TO_MOVE_AT_START = 5; //feet

    /*********************************************************************************************
     * Shoot Three (At Start) Auton Command
     *********************************************************************************************/
    double SHOOT_FROM_START_TO_GOAL = 10;

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
