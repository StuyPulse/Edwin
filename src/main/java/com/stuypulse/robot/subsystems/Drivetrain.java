package com.stuypulse.robot.subsystems;

import java.util.Arrays;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.stuypulse.robot.Constants.DrivetrainSettings;
import com.stuypulse.robot.Constants.Ports;
import com.stuypulse.stuylib.math.Angle;

import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drivetrain extends SubsystemBase {

    // Enum used to store the state of the gear
    public static enum Gear {
        HIGH, LOW
    };

    // Turn a list of speed controllers into a speed controller group
    private static SpeedControllerGroup makeControllerGroup(SpeedController... controllers) {
        return new SpeedControllerGroup(controllers[0], Arrays.copyOfRange(controllers, 1, controllers.length));
    }

    // An array of motors on the left and right side of the drive train
    private CANSparkMax[] leftMotors;
    private CANSparkMax[] rightMotors;

    // An encoder for each side of the drive train
    private CANEncoder leftNEO;
    private CANEncoder rightNEO;

    // DifferentialDrive and Gear Information
    private Gear gear;
    private Solenoid gearShift;
    private DifferentialDrive drivetrain;

    // NAVX for Gyro
    private AHRS navx;

    private boolean isAligned;

    public Drivetrain() {
        // Add Motors to list
        leftMotors = new CANSparkMax[] { 
            new CANSparkMax(Ports.Drivetrain.LEFT_TOP, MotorType.kBrushless),
            new CANSparkMax(Ports.Drivetrain.LEFT_BOTTOM, MotorType.kBrushless) 
        };

        rightMotors = new CANSparkMax[] { 
            new CANSparkMax(Ports.Drivetrain.RIGHT_TOP, MotorType.kBrushless),
            new CANSparkMax(Ports.Drivetrain.RIGHT_BOTTOM, MotorType.kBrushless) 
        };

        // Create list of encoders based on motors
        leftNEO = leftMotors[1].getEncoder();
        rightNEO = rightMotors[1].getEncoder();

        leftNEO.setPosition(0);
        rightNEO.setPosition(0);

        // Make differential drive object
        drivetrain = new DifferentialDrive(
            makeControllerGroup(leftMotors),
            makeControllerGroup(rightMotors)
        );

        gearShift = new Solenoid(Ports.Drivetrain.GEAR_SHIFT);

        // Initialize NAVX
        navx = new AHRS(SPI.Port.kMXP);

        // Configure Motors and Other Things
        setInverted(DrivetrainSettings.IS_INVERTED);
        setSmartCurrentLimit(DrivetrainSettings.CURRENT_LIMIT);
        leftMotors[0].setIdleMode(IdleMode.kBrake);
        leftMotors[1].setIdleMode(IdleMode.kCoast);
        rightMotors[0].setIdleMode(IdleMode.kBrake);
        rightMotors[1].setIdleMode(IdleMode.kCoast);
        setLowGear();
    }

    /***********************
     * MOTOR CONFIGURATION *
     ***********************/

    // Set the distance traveled in one rotation of the motor
    public void setNEODistancePerRotation(double distance) {
        leftNEO.setPositionConversionFactor(distance);
        rightNEO.setPositionConversionFactor(distance);
    }

    // Set the smart current limit of all the motors
    public void setSmartCurrentLimit(int limit) {
        for (CANSparkMax motor : leftMotors) {
            motor.setSmartCurrentLimit(limit);
        }

        for (CANSparkMax motor : rightMotors) {
            motor.setSmartCurrentLimit(limit);
        }
    }

    // Set the idle mode of the all the motors
    public void setIdleMode(IdleMode mode) {
        for (CANSparkMax motor : leftMotors) {
            motor.setIdleMode(mode);
        }

        for (CANSparkMax motor : rightMotors) {
            motor.setIdleMode(mode);
        }
    }

    // Set isInverted of all the motors
    public void setInverted(boolean inverted) {
        for (CANSparkMax motor : leftMotors) {
            motor.setInverted(inverted);
        }

        for (CANSparkMax motor : rightMotors) {
            motor.setInverted(inverted);
        }
    }

    /*****************
     * Gear Shifting *
     *****************/

    // Gets the current gear the robot is in
    public Gear getGear() {
        return gear;
    }

    // Sets the current gear the robot is in
    public void setGear(Gear gear) {
        this.gear = gear;
        if(this.gear == Gear.HIGH) {
            gearShift.set(true);
            setNEODistancePerRotation(DrivetrainSettings.Encoders.HIGH_GEAR_DISTANCE_PER_ROTATION);
            reset();
        } else {
            gearShift.set(false);
            setNEODistancePerRotation(DrivetrainSettings.Encoders.LOW_GEAR_DISTANCE_PER_ROTATION);
            reset();
        }
    }

    // Sets robot into low gear
    public void setLowGear() {
        setGear(Gear.LOW);
    }

    // Sets robot into high gear
    public void setHighGear() {
        setGear(Gear.HIGH);
    }

    
    /********
     * NAVX *
     ********/

    // Gets NavX object
    public AHRS getNavX() {
        return navx;
    }

    // Gets current Angle of the Robot
    public Angle getAngle() {
        return Angle.fromDegrees(navx.getAngle());
    }

    private void resetNavX() {
        navx.reset();
    }

    
    /*********************
     * ENCODER FUNCTIONS *
     *********************/

    // Distance
    public double getLeftDistance() {
        return leftNEO.getPosition() * DrivetrainSettings.Encoders.LEFT_YEILD;
    }

    public double getRightDistance() {
        return rightNEO.getPosition() * DrivetrainSettings.Encoders.RIGHT_YEILD;
    }

    public double getDistance() {
        return (getLeftDistance() + getRightDistance()) / 2.0;
    }

    // Velocity
    public double getLeftVelocity() {
        return leftNEO.getVelocity() * DrivetrainSettings.Encoders.LEFT_YEILD;
    }

    public double getRightVelocity() {
        return rightNEO.getVelocity() * DrivetrainSettings.Encoders.RIGHT_YEILD;
    }

    public double getVelocity() {
        return (getLeftVelocity() + getRightVelocity()) / 2.0;
    }

    public void reset() {
        resetNavX();
        leftNEO.setPosition(0);
        rightNEO.setPosition(0);
    }

    /*********************
     * VOLTAGE FUNCTIONS *
     *********************/

    public double getBatteryVoltage() {
        return RobotController.getBatteryVoltage();
    }

    public double getLeftVoltage() {
        double volts = 0.0;
        for(SpeedController motor : leftMotors) {
            volts += motor.get() * getBatteryVoltage() / DrivetrainSettings.LEFT_VOLTAGE_MUL;
        }
        return volts / leftMotors.length;
    }

    public double getRightVoltage() {
        double volts = 0.0;
        for(SpeedController motor : rightMotors) {
            volts += motor.get() * getBatteryVoltage() / DrivetrainSettings.RIGHT_VOLTAGE_MUL;
        }
        return volts / leftMotors.length;
    }

    public void tankDriveVolts(double leftVolts, double rightVolts) {
        for(SpeedController motor : leftMotors) {
            motor.setVoltage(leftVolts * DrivetrainSettings.LEFT_VOLTAGE_MUL);
        }

        for(SpeedController motor : rightMotors) {
            motor.setVoltage(rightVolts * DrivetrainSettings.RIGHT_VOLTAGE_MUL);
        }

        drivetrain.feed();
    }

    /********************
     * DRIVING COMMANDS *
     ********************/

    // Stops drivetrain from moving
    public void stop() {
        tankDrive(0, 0);
    }

    // Drives using tank drive
    public void tankDrive(double left, double right) {
        drivetrain.tankDrive(left, right, false);
    }

    // Drives using arcade drive
    public void arcadeDrive(double speed, double rotation) {
        drivetrain.arcadeDrive(speed, rotation, false);
    }

    // Drives using curvature drive algorithm
    public void curvatureDrive(double speed, double rotation, boolean quickturn) {
        drivetrain.curvatureDrive(speed, rotation, quickturn);
    }

    // Drives using curvature drive algorithm with automatic quick turn
    public void curvatureDrive(double speed, double rotation) {
        if (Math.abs(speed) < DrivetrainSettings.QUICKTURN_THRESHOLD) {
            curvatureDrive(speed, rotation * DrivetrainSettings.QUICKTURN_SPEED, true);
        } else {
            curvatureDrive(speed, rotation, false);
        }
    }

    /*******************
     * STATE FUNCTIONS *
     *******************/

    public void setIsAligned(boolean aligned) {
        isAligned = aligned;
    }

    public boolean getIsAligned() {
        return isAligned;
    }
}
