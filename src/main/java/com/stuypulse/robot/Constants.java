
/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.stuypulse.robot;

import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.util.Color;

import edu.wpi.first.wpilibj.util.Units;

import java.nio.file.Path;

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

    Path DEPLOY_DIRECTORY = Filesystem.getDeployDirectory().toPath();

    public interface Ports {

        public interface Gamepad {
            int TESTER = 0;
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

        public interface Shooter {
            int HOOD_SOLENOID = 1;

            int LEFT = 12;
            int MIDDLE = 13;
            int RIGHT = 14;

            int FEEDER = 11;
        }

        public interface Climber {
            int LIFT_MOTOR_PORT = 15;
            int YOYO_MOTOR_PORT = 17;

            int LIFT_SOLENOID_CHANNEL = 2;
            int YOYO_SOLENOID_CHANNEL = 3;

            int LIMIT_SWITCH_CHANNEL = -1;
        }

        public interface Woof {
            int MOTOR_PORT = 16;
            int SENSOR_PORT = -1;
        }

        public interface Intake {
            int MOTOR_PORT = 18;
            // int SOLENOID_PORT_A = 6;
            int SOLENOID_PORT_A = 4;
            int SOLENOID_PORT_B = 5;
            int SENSOR_PORT = 4;
            // int SOLENOID_PORT_B = 7;
        }

        public interface Chimney {
            int LIFT_MOTOR_PORT = 8;
            int LOWER_SENSOR_PORT = 5;
            int UPPER_SENSOR_PORT = 6;
        }

        public interface Pneumatics {
            int ANALOG_PRESSURE_SWITCH_PORT = 0;
            double ANALOG_PRESSURE_SWITCH_VOLTAGE_SUPPLY = 5.0;
        }

        int FUNNEL = 9;
    }

    public interface DrivetrainSettings {
        // If speed is below this, use quick turn
        double QUICKTURN_THRESHOLD = 0.05;

        // How much to slow down quick turn
        double QUICKTURN_SPEED = 0.5;

        // Low Pass Filter and deadband for Driver Controls
        double SPEED_DEADBAND = 0.1;
        double ANGLE_DEADBAND = 0.1;

        double SPEED_POWER = 1.0;
        double ANGLE_POWER = 1.0;

        double SPEED_FILTER = 0.5;
        double ANGLE_FILTER = 0.05;

        // Current Limit for the motors
        int CURRENT_LIMIT = 40;

        // If the motors are inverted
        boolean IS_INVERTED = true;

        // The voltage multipliers for each side
        double RIGHT_VOLTAGE_MUL = -1.0; 
        double LEFT_VOLTAGE_MUL = 1.0;
        
        //TODO: fill in 
        double TRACK_WIDTH = Units.inchesToMeters(30);
 
        interface Motion {

            DifferentialDriveKinematics KINEMATICS = new DifferentialDriveKinematics(TRACK_WIDTH);

            SimpleMotorFeedforward MOTOR_FEED_FORWARD = new SimpleMotorFeedforward(
                FeedForward.S,
                FeedForward.V, 
                FeedForward.A
            );
            
            // TODO: fill in
            interface FeedForward {
                double S = 0;
                double V = 0;
                double A = 0;
            }

            // TODO: fill in
            interface PID {
                double P = 0;
                double I = 0;
                double D = 0;
            }
        }
        
        public interface Odometry {
            Translation2d STARTING_TRANSLATION = new Translation2d();
            Rotation2d STARTING_ANGLE = new Rotation2d();

            Pose2d STARTING_POSITION = new Pose2d(STARTING_TRANSLATION, STARTING_ANGLE);
        }

        // Encoder Constants
        public interface Encoders {

            double WHEEL_DIAMETER = Units.inchesToMeters(6);
            double WHEEL_CIRCUMFERENCE = WHEEL_DIAMETER * Math.PI;

            double LOW_GEAR_DISTANCE_PER_ROTATION = WHEEL_CIRCUMFERENCE * (1.0 / 16.71);
            double HIGH_GEAR_DISTANCE_PER_ROTATION = WHEEL_CIRCUMFERENCE * (1.0 / 7.6199); // TODO: Find this value out
            double LEFT_YEILD = 1.0;
            double RIGHT_YEILD = -1.0;
            
        }
    }

    public interface Alignment {
        double TRENCH_DISTANCE = Units.inchesToMeters(199);
        double INITATION_LINE_DISTANCE = Units.inchesToMeters(88);

        double MIN_DISTANCE = Units.feetToMeters(3);
        double MAX_DISTANCE = Units.feetToMeters(54);

        double MIN_ALIGNMENT_TIME = 1.0;
        double INTERPOLATION_PERIOD = 0.25;

        SmartNumber AUTOTUNE_P = new SmartNumber("Auto Tune P", 0.8);
        SmartNumber AUTOTUNE_I = new SmartNumber("Auto Tune I", 0.0);
        SmartNumber AUTOTUNE_D = new SmartNumber("Auto Tune D", 0.1);

        public interface Speed {

            // Preset PID Values
            SmartNumber P = new SmartNumber("SpeedP", 0.984252);
            SmartNumber I = new SmartNumber("SpeedI", 0);
            SmartNumber D = new SmartNumber("SpeedD", 0.0656168);

            // Get PID Controller
            public static PIDController getPID() {
                return new PIDController(P, I, D);
            }

            // Bang Bang speed when measuring PID Values
            double BANGBANG_SPEED = 0.5;

            // Low Pass Filter Time Constant for controller
            SmartNumber IN_SMOOTH_FILTER = new SmartNumber("Speed In Filter", 0);
            SmartNumber OUT_SMOOTH_FILTER = new SmartNumber("Speed Out Filter", 0.2);

            // Max speed for limelight to move
            double LIMELIGHT_MAX_SPEED = 0.9;

            // What is an acceptable error
            double MAX_SPEED_ERROR = Units.inchesToMeters(3.0);
            double MAX_SPEED_VEL = Units.inchesToMeters(9.0);
        }

        public interface Angle {
            // Preset PID Values
            SmartNumber P = new SmartNumber("AngleP", 0.072178478);
            SmartNumber I = new SmartNumber("AngleI", 0);
            SmartNumber D = new SmartNumber("AngleD", 0.00754593176);

            // Get PID Controller
            public static PIDController getPID() {
                return new PIDController(P, I, D);
            }

            // Bang Bang speed when measuring PID Values
            double BANGBANG_SPEED = 0.4;

            // Low pass Filter Time Constant for controller
            SmartNumber IN_SMOOTH_FILTER = new SmartNumber("Angle In Filter", 0.00);
            SmartNumber OUT_SMOOTH_FILTER = new SmartNumber("Angle Out Filter", 0.06);

            // What is an acceptable error
            double MAX_ANGLE_ERROR = 3;
            double MAX_ANGLE_VEL = 20.0;
        }

        public interface Measurements {

            double GOAL_HEIGHT = Units.feetToMeters(7) + Units.inchesToMeters(6);

            public interface Limelight {
                double HEIGHT = Units.feetToMeters(2) + Units.inchesToMeters(10);
                double DISTANCE = Units.feetToMeters(0);
                double PITCH = 25;
                SmartNumber YAW = new SmartNumber("Limelight Yaw", 2.0);
            }
        }  
    }

    public interface ShooterSettings {

        int CURRENT_LIMIT = 45;

        double GEAR = 2.0 / 3.0;

        double INITATION_LINE_RPM = 2075;
        double TRENCH_RPM = 3000;
        double FAR_RPM = 5300.0;

        double TOLERANCE = 100;

        SmartNumber I_LIMIT = new SmartNumber("Shooter / Feeder I Limit", 400);
        SmartNumber I_RANGE = new SmartNumber("Shooter / Feeder I Range", 400);

        public interface Shooter {
            double MAX_RPM = 5600.0 * GEAR;

            SmartNumber P = new SmartNumber("Shooter P", 0.001234);
            SmartNumber I = new SmartNumber("Shooter I", 0.003559);
            SmartNumber D = new SmartNumber("Shooter D", 0.000107);
            SmartNumber FF = new SmartNumber("Shooter FF", 0.000195);

            double BANGBANG_SPEED = 0.2;

            double REVERSE_SPEED = -0.25;
        }

        public interface Feeder {
            double SPEED_MUL = 1.0;

            double MAX_RPM = 5600.0 * GEAR;

            SmartNumber P = new SmartNumber("Feeder P", 0.000944);
            SmartNumber I = new SmartNumber("Feeder I", 0.002550);
            SmartNumber D = new SmartNumber("Feeder D", 0.000087);
            SmartNumber FF = new SmartNumber("Feeder FF", 0.000205);

            double BANGBANG_SPEED = 0.2;
        }
    }

    public interface ClimberSettings {
        double MOVE_DEADBAND = 0.25;

        double EXPONENT = 1 / 3;

        double MOVE_SLOW_SPEED = 0.1;

        double MOVE_LIFT_UP_SPEED = 1.0;
        double MOVE_LIFT_DOWN_SPEED = -1.0;
        double SETUP_WAIT_TIME = 0.2;
        double SCALE = 0.5;
    }

    public interface FunnelSettings {
        double FUNNEL_SPEED = 0.8;
        double UNFUNNEL_SPEED = -FUNNEL_SPEED;

        double ENCODER_APPROACH_STALL_THRESHOLD = 3.0;
    }

    public interface WoofSettings {
        double TURN_SPEED = 1.0;
        double TARGET_ENCODER_VALUE = 600;
    }

    public interface IntakeSettings {
        double MOTOR_SPEED = 0.8;
    }

    public interface ChimneySettings {
        double LIFT_UP_SPEED = 1.0;
        double ENCODER_RADIUS = -1;
        double BALL_PER_ROTATIONS = -0.5;
    }

    public interface Colors {
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

        Color CYAN_TARGET = ColorMatch.makeColor(Colors.CYAN_RED, Colors.CYAN_GREEN, Colors.CYAN_BLUE);
        Color GREEN_TARGET = ColorMatch.makeColor(Colors.GREEN_RED, Colors.GREEN_GREEN, Colors.GREEN_BLUE);
        Color RED_TARGET = ColorMatch.makeColor(Colors.RED_RED, Colors.RED_GREEN, Colors.RED_BLUE);
        Color YELLOW_TARGET = ColorMatch.makeColor(Colors.YELLOW_RED, Colors.YELLOW_GREEN, Colors.YELLOW_BLUE);
    }

    // TODO check all values for correctlynessly
    public interface AutoSettings {
        /*********************************************************************************************
         * Movement Auton Command
         *********************************************************************************************/
        double DISTANCE_TO_MOVE_AT_START = Units.feetToMeters(3.25); // feet

        /*********************************************************************************************
         * Shoot Three (At Start) Auton Command
         *********************************************************************************************/
        double SHOOT_FROM_START_TO_GOAL = Units.feetToMeters(10);

        /*********************************************************************************************
         * Shoot at start and take 3 balls from trench
         *********************************************************************************************/
        double ANGLE_FROM_START_TO_TRENCH = Units.feetToMeters(37.7);
        double DISTANCE_FROM_START_TO_TRENCH_IN_FEET = Units.feetToMeters(5);
        double DISTANCE_FROM_BALL_TO_BALL = Units.feetToMeters(36);
        double DISTANCE_FROM_TRENCH_TO_GOAL = Units.feetToMeters(20);

        /*********************************************************************************************
         * Shoot three at start, get 3 balls from trench, and then take 2 balls from
         * rdvs
         *********************************************************************************************/
        double ANGLE_FROM_TRENCH_TO_RDVS = 125.88;
        double DISTANCE_FROM_TRENCH_TO_RDVS = Units.feetToMeters(109.85);
        double ANGLE_FROM_RDVS_TO_TWO_BALL = 25; // estimation between 0 - 54.12
        double DISTANCE_BETWEEN_TWO_BALL = Units.feetToMeters(16.57);
        double DISTANCE_FROM_RDVS_TO_INTERSECTION_BEWTWEEN_TWO_BALL_AND_GOAL = Units.feetToMeters(40); // estimation according to field
                                                                                                       // markings

        /*********************************************************************************************
         * Shoot three at start and get 3 balls from rdvs
         *********************************************************************************************/

        double DISTANCE_FROM_START_TO_RDVS = Units.feetToMeters(107.83);
        double ANGLE_FROM_START_POINT_TO_THREE_BALL = 247.5; // estimation from common knowledge
        double DISTANCE_FOR_THREE_BALLS_IN_RDVS = Units.feetToMeters(36); // estimate. Probably higher

        /*********************************************************************************************
         * Shoot three at start, and then get 5 balls from rdvs
         *********************************************************************************************/

        double ANGLE_FROM_THREE_BALL_TO_TWO_BALL = 90;
        double DISTANCE_FROM_THREE_BALL_TO_TWO_BALL = Units.feetToMeters(25.42);
    }
}
