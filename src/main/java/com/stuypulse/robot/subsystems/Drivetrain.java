package com.stuypulse.robot.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.stuypulse.robot.Constants.DrivetrainSettings;
import com.stuypulse.robot.Constants.Ports;
import com.stuypulse.stuylib.util.TankDriveEncoder;

import java.util.Arrays;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

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

    private TankDriveEncoder greyhills;

    // DifferentialDrive and Gear Information
    private Gear gear;
    private Solenoid gearShift;
    private DifferentialDrive highGearDrive;
    private DifferentialDrive lowGearDrive;

    // NAVX for Gyro
    private AHRS navx;

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

        greyhills = new TankDriveEncoder(
            new Encoder(Ports.Drivetrain.LEFT_ENCODER_A, Ports.Drivetrain.LEFT_ENCODER_B), 
            new Encoder(Ports.Drivetrain.RIGHT_ENCODER_A, Ports.Drivetrain.RIGHT_ENCODER_B)
        );

        // Create DifferentialDrive for different gears
        highGearDrive = new DifferentialDrive(
            makeControllerGroup(leftMotors),
            makeControllerGroup(rightMotors)
        );

        lowGearDrive = highGearDrive;

        // lowGearDrive = new DifferentialDrive(
        //     makeControllerGroup(getLowGear(leftMotors)),
        //     makeControllerGroup(getLowGear(rightMotors))
        // );

        gearShift = new Solenoid(Ports.Drivetrain.GEAR_SHIFT);

        // Initialize NAVX
        navx = new AHRS(SPI.Port.kMXP);

        // Configure Motors and Other Things
        setInverted(DrivetrainSettings.IS_INVERTED);
        setSmartCurrentLimit(DrivetrainSettings.CURRENT_LIMIT);
        setNEODistancePerRotation(DrivetrainSettings.Encoders.WHEEL_CIRCUMFERENCE);
        setGreyhillDistancePerPulse(DrivetrainSettings.Encoders.GREYHILL_FEET_PER_PULSE);
        setLowGear();
    }

    /**
     * Set the smart current limit of all the motors
     * 
     * @param limit smart current limit
     */
    public void setSmartCurrentLimit(int limit) {
        for (CANSparkMax motor : leftMotors) {
            motor.setSmartCurrentLimit(limit);
        }

        for (CANSparkMax motor : rightMotors) {
            motor.setSmartCurrentLimit(limit);
        }

    }

    /**
     * Set isInverted of all the motors
     * 
     * @param inverted desired settings
     */
    public void setInverted(boolean inverted) {
        for (CANSparkMax motor : leftMotors) {
            motor.setInverted(inverted);
        }

        for (CANSparkMax motor : rightMotors) {
            motor.setInverted(inverted);
        }
    }

    /**
     * @return current gear the robot is in
     */
    public Gear getGear() {
        return gear;
    }

    /**
     * @param gear value for gear on robot
     */
    public void setGear(Gear gear) {
        if (this.gear != gear) {
            this.gear = gear;
            // stop();
            gearShift.set(this.gear == Gear.HIGH);
        }
    }

    /**
     * Sets drivetrain into low gear
     */
    public void setLowGear() {
        setGear(Gear.LOW);
    }

    /**
     * Sets drivetrain into high gear
     */
    public void setHighGear() {
        setGear(Gear.HIGH);
    }

    /**
     * @return the navx on the drivetrain used for positioning
     */
    public AHRS getNavX() {
        return navx;
    }

    /**
     * @return get the angle of the robot
     */
    public double getGyroAngle() {
        return navx.getAngle();
    }

    /**
     * Set the distance that is traveled when one rotation of a motor is complete,
     * this helps improve encoder readings
     * 
     * @param distance distance robot moves in one rotation
     */
    public void setNEODistancePerRotation(double distance) {
        leftNEO.setPositionConversionFactor(distance);
        rightNEO.setPositionConversionFactor(distance);
    }

    /**
     * @return distance left side of drivetrain has moved
     */
    public double getLeftNEODistance() {
        return leftNEO.getPosition();
    }

    /**
     * @return distance right side of drivetrain has moved
     */
    public double getRightNEODistance() {
        return rightNEO.getPosition();
    }

    /**
     * @return distance drivetrain has moved
     */
    public double getNEODistance() {
        double left = getLeftNEODistance();
        double right = getRightNEODistance();

        return Math.max(left, right);
    }

    /**
     * Set the distance that is traveled when one rotation of a motor is complete,
     * this helps improve encoder readings
     * 
     * @param distance distance robot moves in one rotation
     */
    public void setGreyhillDistancePerPulse(double distance) {
        greyhills.getLeftEncoder().setDistancePerPulse(distance);
        greyhills.getRightEncoder().setDistancePerPulse(distance);
    }

    /**
     * @return distance left side of drivetrain has moved
     */
    public double getLeftGreyhillDistance() {
        return greyhills.getLeftDistance();
    }

    /**
     * @return distance right side of drivetrain has moved
     */
    public double getRightGreyhillDistance() {
        return greyhills.getRightDistance();
    }

    /**
     * @return distance drivetrain has moved
     */
    public double getGreyhillDistance() {
        return greyhills.getDistance();
    }

    /**
     * Resets the greyhills distance
     */
    public void resetGreyhill() {
        greyhills.reset();
    }

    /**
     * @return DifferentialDrive class based on current gear
     */
    public DifferentialDrive getCurrentDrive() {
        if (gear == Gear.HIGH) {
            return highGearDrive;
        } else {
            return lowGearDrive;
        }
    }

    /**
     * Stops drivetrain from moving
     */
    public void stop() {
        tankDrive(0, 0);
    }

    /**
     * Drives using tank drive
     * 
     * @param left  speed of left side
     * @param right speed of right side
     */
    public void tankDrive(double left, double right) {
        getCurrentDrive().tankDrive(left, right, false);
    }

    /**
     * Drives using arcade drive
     * 
     * @param speed    speed of drive train
     * @param rotation amount that it is turning
     */
    public void arcadeDrive(double speed, double rotation) {
        getCurrentDrive().arcadeDrive(speed, rotation, false);
    }

    /**
     * Drives using curvature drive algorithm
     * 
     * @param speed     speed of robot
     * @param rotation  amount that it turns
     * @param quickturn overrides constant curvature
     */
    public void curvatureDrive(double speed, double rotation, boolean quickturn) {
        getCurrentDrive().curvatureDrive(speed, rotation, quickturn);
    }

    /**
     * Drives using curvature drive algorithm with automatic quick turn
     * 
     * @param speed    speed of robot
     * @param rotation amount that it turns
     */
    public void curvatureDrive(double speed, double rotation) {
        if (Math.abs(speed) < DrivetrainSettings.QUICKTURN_THRESHOLD) {
            curvatureDrive(speed, rotation * DrivetrainSettings.QUICKTURN_SPEED, true);
        } else {
            curvatureDrive(speed, rotation, false);
        }
    }
}
