package com.stuypulse.robot.subsystems;

import org.photonvision.PhotonCamera;
import org.photonvision.PhotonUtils;
import org.photonvision.common.hardware.VisionLEDMode;

import com.stuypulse.robot.constants.Field;
import com.stuypulse.robot.constants.Settings;
import com.stuypulse.robot.constants.Settings.Alignment;
import com.stuypulse.robot.constants.Settings.Alignment.Measurements;
import com.stuypulse.robot.constants.Settings.Alignment.Measurements.Limelight;
import com.stuypulse.stuylib.math.Angle;

import edu.wpi.first.math.ComputerVisionUtil;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.net.PortForwarder;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static com.stuypulse.robot.constants.Settings.Alignment.Measurements.Limelight.*;

public class Camera extends SubsystemBase {
    /** HARDWARE */
    private final PhotonCamera camera;

    public Camera() {

        this.camera = new PhotonCamera("gloworm");

        PortForwarder.add(5800, "gloworm.local", 5800);
    }

    public boolean hasAnyTarget() {
        return (camera != null) && camera.getLatestResult().hasTargets();
    }

    public boolean hasTarget() {
        return hasAnyTarget();
        // && Alignment.MIN_DISTANCE < getDistance()
        // && getDistance() < Alignment.MAX_DISTANCE;
    }

    public double getDistance() {
        var result = camera.getLatestResult();
        if (!result.hasTargets()) {
            System.out.println("Unable To Find Target! [getDistance() was called]");
            // return Alignment.APRIL_TAG_DISTANCE.get();
            return Alignment.RING_DISTANCE.get();
        }

        return PhotonUtils.calculateDistanceToTargetMeters(
                HEIGHT,
                Measurements.APRIL_TAG_16H52_2.get(),
                PITCH,
                Units.degreesToRadians(result.getBestTarget().getPitch()));
        // return PhotonUtils.calculateDistanceToTargetMeters(HEIGHT, Field.Hub.HEIGHT,
        // PITCH,
        // Units.degreesToRadians(result.getBestTarget().getPitch()));
    }

    public Angle getAngle() {
        var result = camera.getLatestResult();
        if (!result.hasTargets()) {
            System.out.println("Unable To Find Target! [getAngle() was called]");
            return Angle.kZero;
        }

        return Angle.fromDegrees(result.getBestTarget().getYaw());
    }

    public Pose3d getPose3d() {
        var result = camera.getLatestResult();
        if (!result.hasTargets()) {
            System.out.println("Unable To Find Target! [getAngle() was called]");
            return new Pose3d();
        }

        return ComputerVisionUtil.objectToRobotPose(
                new Pose3d(
                        0, 0, Units.inchesToMeters(64),
                        new Rotation3d()),
                result.getBestTarget().getBestCameraToTarget(),
                // new Transform3d());
                new Transform3d(new Translation3d(Units.inchesToMeters(10), 0, 0),
                        new Rotation3d(0, /*-Limelight.PITCH*/ 0, 0)));
    }

    public Pose2d getPose2d(Rotation2d gyroAngle) {
        var result = camera.getLatestResult();
        if (!result.hasTargets()) {
            System.out.println("Unable To Find Target! [getAngle() was called]");
            return new Pose2d();
        }

        return PhotonUtils.estimateFieldToRobot(
                HEIGHT,
                Measurements.APRIL_TAG_16H52_2.get(),
                PITCH,
                Units.degreesToRadians(result.getBestTarget().getPitch()),
                getAngle().getRotation2d(),
                gyroAngle,
                new Pose2d(0, 0, Rotation2d.fromRadians(Math.PI)),
                new Transform2d());
    }

    @Override
    public void periodic() {
        if (Settings.DEBUG_MODE.get() && hasAnyTarget()) {
            SmartDashboard.putNumber("Camera/Distance", getDistance());
            SmartDashboard.putNumber("Camera/Angle", getAngle().toDegrees());

            Pose3d pose = getPose3d();
            SmartDashboard.putNumber("Camera/Pose X", pose.getX());
            SmartDashboard.putNumber("Camera/Pose Y", pose.getY());
            // SmartDashboard.putNumber("Camera/Pose Z", pose.getZ());
            SmartDashboard.putNumber("Camera/Pose Angle", pose.getRotation().toRotation2d().getDegrees());
        }

        SmartDashboard.putBoolean("Camera/Has Target", hasAnyTarget());

        if (DriverStation.isDisabled()) {
            camera.setLED(VisionLEDMode.kOff);
        } else {
            camera.setLED(VisionLEDMode.kOn);
        }
    }
}