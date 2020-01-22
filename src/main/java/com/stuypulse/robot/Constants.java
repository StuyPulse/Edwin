/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.stuypulse.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public interface Constants {

    /**
     * Lets us turn feet and inches into just feet for measurements
     * @param feet feet
     * @param inches inches
     * @return value in feet
     */
    private static double toFeet(int feet, double inches) {
        return ((double) feet) + (inches / 12.0);
    }

    /**
     * Allow us to initialize a value on smart dashboard while putting it in robot map
     * @param key name of value on smart dashboard
     * @param value value you want it to be set to
     * @return the double you passed to it
     */
    private static double onSmartDashboard(String key, double value) {
        SmartDashboard.putNumber(key, value);
        return value;
    }

    public interface Ports {

        public interface Gamepad {
            int kOperator = 0;
            int kDriver = 1;
        }

        public interface Drivetrain {
            int kLeftTop = -1;
            int kLeftMiddle = -1;
            int kLeftBottom = -1;
            
            int kRightTop = -1;
            int kRightMiddle = -1;
            int kRightBottom = -1;

            int kLeftEncoderA = -1;
            int kLeftEncoderB = -1;
            int kRightEncoderA = -1;
            int kRightEncoderB = -1;

            int kGearShift = -1;
        }
    }

    public interface DrivetrainSettings {
        double kQuickTurnThreshold = 0.04;
        double kQuickTurnSpeed = 0.5;

        double kRCSpeed = 0.5;
        double kRCAngle = 0.25;

        int kCurrentLimit = 0;

        public interface Encoders {
            double kNEODistancePerRotation = 1.0;
            double kGreyhillDistancePerPulse = 1.0;
        }
    }
    
    public interface Alignment {
        public interface Speed {
            double kP = onSmartDashboard("SpeedP", -1);
            double kI = onSmartDashboard("SpeedI", -1);
            double kD = onSmartDashboard("SpeedD", -1);

            double kBangBang = 0.75;

            double kInSmoothTime = 0.0;
            double kOutSmoothTime = 0.1;

            double kMaxSpeedErr = 1;
            double kMaxSpeedVel = 0.5;
        }

        public interface Angle {
            double kP = onSmartDashboard("AngleP", -1);
            double kI = onSmartDashboard("AngleI", -1);
            double kD = onSmartDashboard("AngleD", -1);

            double kBangBang = 0.75;

            double kInSmoothTime = 0.00;
            double kOutSmoothTime = 0.05;

            double kMaxAngleErr = 2;
            double kMaxAngleVel = 1;
        }

        public interface Measurements {

            double kGoalHeight = toFeet(7, 6);

            public interface Limelight {
                double kHeight = toFeet(2, 7);
                double kDistance = toFeet(0, 0);
                double kPitch = 17.3;
                double kYaw = 0.0;
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
