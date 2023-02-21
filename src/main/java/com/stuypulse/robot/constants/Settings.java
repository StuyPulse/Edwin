/************************ PROJECT PHIL ************************/
/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved.*/
/* This work is licensed under the terms of the MIT license.  */
/**************************************************************/

package com.stuypulse.robot.constants;

import com.stuypulse.stuylib.control.Controller;
import com.stuypulse.stuylib.control.angle.AngleController;
import com.stuypulse.stuylib.control.angle.feedback.AnglePIDController;
import com.stuypulse.stuylib.control.feedback.PIDController;
import com.stuypulse.stuylib.math.SLMath;
import com.stuypulse.stuylib.network.SmartBoolean;
import com.stuypulse.stuylib.network.SmartNumber;
import com.stuypulse.stuylib.streams.IStream;
import com.stuypulse.stuylib.streams.filters.IFilterGroup;
import com.stuypulse.stuylib.streams.filters.LowPassFilter;

import edu.wpi.first.math.util.Units;

/*-
 * File containing tunable settings for every subsystem on the robot.
 *
 * We use StuyLib's SmartNumber / SmartBoolean in order to have tunable
 * values that we can edit on Shuffleboard.
 */
public interface Settings {

    SmartBoolean DEBUG_MODE = new SmartBoolean("Debug Mode", true);

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

    public interface ShooterSettings {

        int CURRENT_LIMIT = 40;

        double TOLERANCE = 100;

        SmartBoolean AUTOTUNE = new SmartBoolean("Shooter/Auto Tune", false);

        SmartNumber I_LIMIT = new SmartNumber("Shooter/I Limit", 1.0);
        SmartNumber I_RANGE = new SmartNumber("Shooter/I Range", 400);

        public interface Shooter {
            SmartNumber P = new SmartNumber("Shooter/Shooter/P", 0.011); // 0.0162989522473
            SmartNumber I = new SmartNumber("Shooter/Shooter/I", 0.04); // 0.0820395194249
            SmartNumber D = new SmartNumber("Shooter/Shooter/D", 0.0013); // 0.000809536203473
            SmartNumber FF = new SmartNumber("Shooter/Shooter/FF", 0.0023);

            double BANGBANG_SPEED = 1;
        }

        public interface Feeder {
            SmartNumber P = new SmartNumber("Shooter/Feeder/P", 0.01); // 0.0157904851743
            SmartNumber I = new SmartNumber("Shooter/Feeder/I", 0.03); // 0.0652808665387
            SmartNumber D = new SmartNumber("Shooter/Feeder/D", 0.001); // 0.000954871753622
            SmartNumber FF = new SmartNumber("Shooter/Feeder/FF", 0.00235);

            double BANGBANG_SPEED = 1;
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
                double LOW_GEAR_NEO_TO_WHEEL = (1.0 / 16.67);
                double HIGH_GEAR_NEO_TO_WHEEL = (1.0 / 7.71);
            }

            double LOW_GEAR_DISTANCE_PER_ROTATION = WHEEL_CIRCUMFERENCE * GearRatio.LOW_GEAR_NEO_TO_WHEEL;
            double HIGH_GEAR_DISTANCE_PER_ROTATION = WHEEL_CIRCUMFERENCE * GearRatio.HIGH_GEAR_NEO_TO_WHEEL;
        }
    }

    public interface Intake {
        double MOTOR_SPEED = 0.8;
    }

    public interface Conveyor {
        double FUNNEL_SPEED = 0.8;
        double UNFUNNEL_SPEED = -FUNNEL_SPEED;

        double ENCODER_APPROACH_STALL_THRESHOLD = 3.0;

        double LIFT_UP_SPEED = 10;
        double ENCODER_RADIUS = -1;
        double BALL_PER_ROTATIONS = -0.5;
    }

    public interface Alignment {
        IStream RING_DISTANCE = new SmartNumber("Limelight/Ring Distance",
                50).filtered(Units::inchesToMeters);
        IStream PAD_DISTANCE = new SmartNumber("Limelight/Pad Distance", 217).filtered(Units::inchesToMeters);

        IStream APRIL_TAG_DISTANCE = new SmartNumber("Limelight/April Tag Distance", 100)
                .filtered(Units::inchesToMeters);

        double MIN_DISTANCE = Units.feetToMeters(1);
        double MAX_DISTANCE = Units.feetToMeters(54);

        double MIN_ALIGNMENT_TIME = 1.0;

        SmartNumber SPEED_ADJ_FILTER = new SmartNumber("Drivetrain/Alignment/Speed Adj RC", 0.1);
        SmartNumber FUSION_FILTER = new SmartNumber("Drivetrain/Alignment/Fusion RC", 0.3);
        SmartNumber SENSOR_FUSION_RC = new SmartNumber("Drivetrain/Alignment/Sensor Fusion RC", 0.4);

        SmartNumber AUTOTUNE_P = new SmartNumber("Drivetrain/Alignment/Auto Tune/P", 0.8);
        SmartNumber AUTOTUNE_I = new SmartNumber("Drivetrain/Alignment/Auto Tune/I", 0.0);
        SmartNumber AUTOTUNE_D = new SmartNumber("Drivetrain/Alignment/Auto Tune/D", 0.1);

        public interface Speed {

            // Preset PID Values
            SmartNumber P = new SmartNumber("Drivetrain/Alignment/Speed/P", 0.984252);
            SmartNumber I = new SmartNumber("Drivetrain/Alignment/Speed/I", 0);
            SmartNumber D = new SmartNumber("Drivetrain/Alignment/Speed/D", 0.0656168);

            // Get PID Controller
            public static Controller getPID() {
                return new PIDController(P, I, D)
                        .setOutputFilter(new IFilterGroup(SLMath::clamp, new LowPassFilter(OUT_SMOOTH_FILTER)));
            }

            // Bang Bang speed when measuring PID Values
            double BANGBANG_SPEED = 0.5;

            // Low Pass Filter Time Constant for controller
            SmartNumber IN_SMOOTH_FILTER = new SmartNumber("Drivetrain/Alignment/Speed/In Filter", 0);
            SmartNumber OUT_SMOOTH_FILTER = new SmartNumber("Drivetrain/Alignment/Speed/Out Filter", 0.2);

            // Max speed for limelight to move
            double LIMELIGHT_MAX_SPEED = 0.8;

            // What is an acceptable error
            double MAX_SPEED_ERROR = Units.inchesToMeters(3.0);
            double MAX_SPEED_VEL = Units.inchesToMeters(9.0);
        }

        public interface Angle {
            // Preset PID Values
            SmartNumber P = new SmartNumber("Drivetrain/Alignment/Angle/P", 0.022);
            SmartNumber I = new SmartNumber("Drivetrain/Alignment/Angle/I", 0);
            SmartNumber D = new SmartNumber("Drivetrain/Alignment/Angle/D", 0.0023);

            // Get PID Controller
            public static AngleController getPID() {
                return new AnglePIDController(P, I, D)
                        .setOutputFilter(new IFilterGroup(SLMath::clamp, new LowPassFilter(OUT_SMOOTH_FILTER)));
            }

            // Bang Bang speed when measuring PID Values
            double BANGBANG_SPEED = 0.4;

            // Low pass Filter Time Constant for controller
            SmartNumber IN_SMOOTH_FILTER = new SmartNumber("Drivetrain/Alignment/Angle/In Filter", 0.00);
            SmartNumber OUT_SMOOTH_FILTER = new SmartNumber("Drivetrain/Alignment/Angle/Out Filter", 0.06);

            // What is an acceptable error
            double MAX_ANGLE_ERROR = 3;
            double MAX_ANGLE_VEL = 20.0;
        }

        public interface Measurements {

            SmartNumber APRIL_TAG_16H52_2 = new SmartNumber("April Tag 2 Height", Units.inchesToMeters(88));
            SmartNumber APRIL_TAG_16H52_1 = new SmartNumber("April Tag 1 Height", Units.inchesToMeters(85.75));

            public interface Limelight {
                double HEIGHT = Units.feetToMeters(2) + Units.inchesToMeters(10);
                double DISTANCE = Units.feetToMeters(0);
                double PITCH = Units.degreesToRadians(23);
                double YAW = 0.0;

                // What angle error should make us start distance alignment
                SmartNumber MAX_ANGLE_FOR_MOVEMENT = new SmartNumber("Limelight/Max Angle For Distance", 3.0);

                SmartNumber MAX_ANGLE_ERROR = new SmartNumber("Limelight/Max Angle Error", 2);
                SmartNumber MAX_DISTANCE_ERROR = new SmartNumber("Limelight/Max Distance Error",
                        Units.inchesToMeters(6));

                SmartNumber MAX_VELOCITY = // THERE WAS AN ERROR WHERE THIS WOULD'NT CHECK WHEN MOVING BACKWARDS
                        new SmartNumber("Limelight/Max Velocity Error", Units.inchesToMeters(3));

                // How long it takes to stop aligning
                double DEBOUNCE_TIME = 0.2;
            }
        }
    }
}
