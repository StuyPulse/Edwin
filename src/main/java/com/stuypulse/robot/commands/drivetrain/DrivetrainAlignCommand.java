// /************************ PROJECT DORCAS ************************/
// /* Copyright (c) 2022 StuyPulse Robotics. All rights reserved.  */
// /* This work is licensed under the terms of the MIT license.    */
// /****************************************************************/

// package com.stuypulse.robot.commands.drivetrain;

// import com.stuypulse.stuylib.control.Controller;
// import com.stuypulse.stuylib.control.angle.AngleController;
// import com.stuypulse.stuylib.math.Angle;
// import com.stuypulse.stuylib.streams.IFuser;
// import com.stuypulse.stuylib.streams.booleans.BStream;
// import com.stuypulse.stuylib.streams.booleans.filters.BDebounceRC;
// import com.stuypulse.stuylib.streams.filters.IFilter;
// import com.stuypulse.stuylib.streams.filters.LowPassFilter;
// import com.stuypulse.robot.commands.conveyor.ConveyorShootCommand;
// import com.stuypulse.robot.constants.Settings;
// import com.stuypulse.robot.constants.Settings.Alignment;
// import com.stuypulse.robot.constants.Settings.Alignment.Measurements.Limelight;
// import com.stuypulse.robot.subsystems.Camera;
// import com.stuypulse.robot.subsystems.Conveyor;
// import com.stuypulse.robot.subsystems.Drivetrain;

// import edu.wpi.first.math.geometry.Pose2d;
// import edu.wpi.first.wpilibj2.command.Command;
// import edu.wpi.first.wpilibj2.command.CommandBase;

// public class DrivetrainAlignCommand extends CommandBase {

//     private final Drivetrain drivetrain;

//     protected final Controller distanceController;
//     protected final AngleController angleController;

//     private IFilter speedAdjFilter;

//     public DrivetrainAlignCommand(Drivetrain drivetrain, Pose2d targetPose) {
//         this.drivetrain = drivetrain;

//         // handle errors

//         this.angleController = Alignment.Angle.getPID();
//         this.distanceController = Alignment.Speed.getPID();

//         speedAdjFilter = new LowPassFilter(Alignment.SPEED_ADJ_FILTER);

//         addRequirements(drivetrain);
//     }

//     @Override
//     public void initialize() {
//         drivetrain.setLowGear();

//         speedAdjFilter = new LowPassFilter(Alignment.SPEED_ADJ_FILTER);
//     }

//     private double getSpeedAdjustment() {
//         double error = angleController.getError().toDegrees() / Limelight.MAX_ANGLE_FOR_MOVEMENT.get();
//         return speedAdjFilter.get(Math.exp(-error * error));
//     }

//     private double getTurn() {
//         return angleController.update(camera.getAngle(), Angle.kZero);
//     }

//     private double getSpeed() {
//         return distanceController.update(camera.getDistance(),
//                 Alignment.APRIL_TAG_DISTANCE.get()) * getSpeedAdjustment();
//         // return 0;
//     }

//     @Override
//     public void execute() {
//         drivetrain.arcadeDrive(getSpeed(), getTurn());
//     }

//     // @Override
//     // public boolean isFinished() {
//     // return finished.get();
//     // }

//     public Command thenShoot(Conveyor conveyor) {
//         return new ConveyorShootCommand(conveyor);
//     }
// }