package com.stuypulse.robot.subsystems;

import org.photonvision.PhotonCamera;
import org.photonvision.PhotonUtils;
import org.photonvision.common.hardware.VisionLEDMode;

import com.stuypulse.robot.constants.Settings;
import com.stuypulse.robot.constants.Settings.Alignment;
import com.stuypulse.stuylib.math.Angle;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static com.stuypulse.robot.constants.Settings.Alignment.Measurements.Limelight.*;

import static com.stuypulse.robot.constants.Field.*;

public class Camera extends SubsystemBase {
    /** HARDWARE */
    private final PhotonCamera camera;

    public Camera() {

        this.camera = new PhotonCamera("Edwin Camera");
    }

    public boolean hasAnyTarget() {
        return camera.getLatestResult().hasTargets();
    }

    public boolean hasTarget() {
        return hasAnyTarget()
                && Alignment.MIN_DISTANCE < getDistance()
                && getDistance() < Alignment.MAX_DISTANCE;
    }

    public double getDistance() {
        if (!hasAnyTarget()) {
            System.out.println("Unable To Find Target! [getDistance() was called]");
            return Alignment.RING_DISTANCE.get();
        }

        var result = camera.getLatestResult();
        return PhotonUtils.calculateDistanceToTargetMeters(
                HEIGHT,
                Hub.HEIGHT,
                PITCH,
                Units.degreesToRadians(result.getBestTarget().getPitch()));
    }

    public Angle getAngle() {
        if (!hasAnyTarget()) {
            System.out.println("Unable To Find Target! [getAngle() was called]");
            return Angle.kZero;
        }

        var result = camera.getLatestResult();
        return Angle.fromDegrees(result.getBestTarget().getYaw());
    }

    @Override
    public void periodic() {
        if (Settings.DEBUG_MODE.get() && hasAnyTarget()) {
            SmartDashboard.putNumber("Camera/Distance", getDistance());
            SmartDashboard.putNumber("Camera/Angle", getAngle().toDegrees());
        }

        if (DriverStation.isDisabled()) {
            camera.setLED(VisionLEDMode.kOff);
        } else {
            camera.setLED(VisionLEDMode.kOn);
        }
    }
}