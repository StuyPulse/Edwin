/************************ PROJECT PHIL ************************/
/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved.*/
/* This work is licensed under the terms of the MIT license.  */
/**************************************************************/

package com.stuypulse.robot.constants;

import com.stuypulse.stuylib.network.SmartBoolean;
import com.stuypulse.stuylib.network.SmartNumber;

import edu.wpi.first.math.util.Units;

/*-
 * File containing tunable settings for every subsystem on the robot.
 *
 * We use StuyLib's SmartNumber / SmartBoolean in order to have tunable
 * values that we can edit on Shuffleboard.
 */
public interface Settings {

    public interface Driver {
        // If speed is below this, use quick turn
       SmartNumber BASE_TURNING_SPEED = new SmartNumber("Driver Settings/Base Turn Speed", 0.45);

        // Low Pass Filter and deadband for Driver Controls
        SmartNumber SPEED_DEADBAND = new SmartNumber("Driver Settings/Speed Deadband", 0.00);
        SmartNumber ANGLE_DEADBAND = new SmartNumber("Driver Settings/Turn Deadband", 0.00);
       
        SmartNumber SPEED_POWER = new SmartNumber("Driver Settings/Speed Power", 2.0);
        SmartNumber ANGLE_POWER = new SmartNumber("Driver Settings/Turn Power", 1.0);
       
        SmartNumber SPEED_FILTER = new SmartNumber("DriPver Settings/Speed Filtering", 0.125);
        SmartNumber ANGLE_FILTER = new SmartNumber("Driver Settings/Turn Filtering", 0.005);
       
    }
    public interface Shooter {
        
        SmartNumber CHANGE_RC = new SmartNumber("Shooter/RC", 0.1);
        SmartNumber TOLERANCE = new SmartNumber("Shooter/Speed Error", 100);
        SmartNumber MIN_RPM = new SmartNumber("Shooter/Min RPM", 250);

        double FEEDER_MULTIPLIER = 0.9;

        public interface ShooterFF {
            SmartNumber S = new SmartNumber("Shooter/Shooter/S", 0.0);
            SmartNumber V = new SmartNumber("Shooter/Shooter/V", 0.0);
            SmartNumber A = new SmartNumber("Shooter/Shooter/A", 0.0);
        }

        public interface ShooterFB {
            SmartNumber P = new SmartNumber("Shooter/Shooter/P", 0.0);
            SmartNumber I = new SmartNumber("Shooter/Shooter/I", 0.0);
            SmartNumber D = new SmartNumber("Shooter/Shooter/D", 0.0);
        }

        public interface FeederFF {
            SmartNumber S = new SmartNumber("Shooter/Feeder/S", 0.0);
            SmartNumber V = new SmartNumber("Shooter/Feeder/V", 0.0);
            SmartNumber A = new SmartNumber("Shooter/Feeder/A", 0.0);
        }

        public interface FeederFB {
            SmartNumber P = new SmartNumber("Shooter/Feeder/P", 0.0);
            SmartNumber I = new SmartNumber("Shooter/Feeder/I", 0.0);
            SmartNumber D = new SmartNumber("Shooter/Feeder/D", 0.0);
        }
    }

    public interface Drivetrain {

        // Width of the robot
        double TRACK_WIDTH = Units.inchesToMeters(30.0); // SEAN PROMISED !

        public interface Motion {

            double MAX_VELOCITY = 1.0;
            double MAX_ACCELERATION = 1.0;

            double MAX_ANGULAR_VELOCITY = (MAX_VELOCITY * 2) / TRACK_WIDTH;
            double MAX_ANGULAR_ACCELERATION = (Math.pow(MAX_ACCELERATION, 2)) / (TRACK_WIDTH / 2);

            public interface Feedforward {
                double kS = 0.367; 
                double kV = 2.07; 
                double kA = 0.47; 
            }

            public interface PID {
                double kP = 0.00337; 
                double kI = 0; 
                double kD = 0; 
            }
        }

        public interface Encoders {
            
            double WHEEL_DIAMETER = Units.inchesToMeters(6);
            double WHEEL_CIRCUMFERENCE = WHEEL_DIAMETER * Math.PI;

            public interface GearRatio {
                // double LOW_GEAR_NEO_TO_WHEEL =  (1.0 / 16.67); 
                double HIGH_GEAR_NEO_TO_WHEEL = (1.0 / 7.71);
            }

            // double LOW_GEAR_DISTANCE_PER_ROTATION =
            //         WHEEL_CIRCUMFERENCE * GearRatio.LOW_GEAR_NEO_TO_WHEEL;
            double HIGH_GEAR_DISTANCE_PER_ROTATION =
                    WHEEL_CIRCUMFERENCE * GearRatio.HIGH_GEAR_NEO_TO_WHEEL;
        }
    }
}
