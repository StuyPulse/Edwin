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

            int kGearShift = -1;
        }
    }

    public interface DrivetrainSettings {
        double kQuickTurnThreshold = 0.04;
        double kQuickTurnSpeed = 0.5;

        int kCurrentLimit = 0;

        double kDistancePerRotation = 0;
    }
    
    public interface Alignment {
        public interface Speed {
            int kP = -1;
            int kI = -1;
            int kD = -1;

            double kBangBang = 0.75;

            double kInSmoothTime = 0.1;
            double kOutSmoothTime = 0.1;

            double kMaxAngleErr = 5;
            double kMaxAngleVel = 5;
        }

        public interface Angle {
            int kP = -1;
            int kI = -1;
            int kD = -1;

            double kBangBang = 0.75;

            double kInSmoothTime = 0.00;
            double kOutSmoothTime = 0.05;
        }

        public interface Measurements {

            private static double toFeet(int feet, double inches) {
                return ((double) feet) + (inches / 12.0);
            }

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
