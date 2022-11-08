package com.stuypulse.robot.subsystems;

import org.photonvision.PhotonCamera;
import org.photonvision.PhotonUtils;
import org.photonvision.common.hardware.VisionLEDMode;

import com.stuypulse.robot.Constants;
import com.stuypulse.robot.Constants.Alignment;
import com.stuypulse.stuylib.control.Controller;
import com.stuypulse.stuylib.control.feedback.PIDController;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.stuypulse.stuylib.network.SmartNumber;

import static com.stuypulse.robot.Constants.Alignment.*;
import static com.stuypulse.robot.Constants.Alignment.Measurements.Limelight.*;

public class Camera extends SubsystemBase {
    /** HARDWARE */
    private final Shooter shooter;
    private final PhotonCamera camera;

    /** CONTROL */
    private final Controller driveController;
    private final Controller turnController;

    private final SmartNumber driveSpeed;
    private final SmartNumber turnSpeed;

    private final SmartNumber distance;

    public Camera(Shooter shooter, Drivetrain drivetrain) {
        this.shooter = shooter;

        this.camera = new PhotonCamera("Edwin Camera");

        driveController = new PIDController(Speed.P, Speed.I, Speed.D);
        turnController = new PIDController(Alignment.Angle.P, Alignment.Angle.I, Alignment.Angle.D);

        driveSpeed = new SmartNumber("Drive Speed", 0);
        turnSpeed = new SmartNumber("Turn Speed", 0);
        distance = new SmartNumber("Distance to Hub", 0);
    }

    public boolean hasAnyTarget() {
        return camera.getLatestResult().hasTargets();
    }

    public boolean hasTarget() {
        return hasAnyTarget()
                && shooter.isReady()
                && MIN_DISTANCE < getDistance()
                && getDistance() < MAX_DISTANCE;
    }

    public double getDistance() {
        if (!hasAnyTarget()) {
            System.out.println("Unable To Find Target! [getDistance() was called]");
            return TRENCH_DISTANCE;
        }
        return distance.get();
    }

    public double getDriveSpeed() {
        return driveSpeed.get();
    }

    public double getTurnSpeed() {
        return turnSpeed.get();
    }

    @Override
    public void periodic() {
        var result = camera.getLatestResult();
        if (hasTarget()) {
            distance.set(PhotonUtils.calculateDistanceToTargetMeters(
                    HEIGHT,
                    HUB_HEIGHT,
                    PITCH,
                    Units.degreesToRadians(result.getBestTarget().getPitch())));
            driveSpeed.set(driveController.update(distance.get(), RING_SHOT_DISTANCE));
            turnSpeed.set(turnController.update(result.getBestTarget().getYaw(), 0));
        }
        if (Constants.DEBUG_MODE.get() && hasAnyTarget()) {
            SmartDashboard.putNumber("Camera/Distance", getDistance());
        }

        if (DriverStation.isDisabled()) {
            camera.setLED(VisionLEDMode.kOff);
        } else {
            camera.setLED(VisionLEDMode.kOn);
        }
    }
}
