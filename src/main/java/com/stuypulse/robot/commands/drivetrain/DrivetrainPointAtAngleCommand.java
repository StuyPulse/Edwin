package com.stuypulse.robot.commands.drivetrain;

import com.stuypulse.robot.constants.Settings.Alignment;
import com.stuypulse.robot.subsystems.Drivetrain;
import com.stuypulse.stuylib.control.angle.AngleController;
import com.stuypulse.stuylib.math.Angle;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class DrivetrainPointAtAngleCommand extends CommandBase {
    private final Drivetrain drivetrain;
    private final Angle angle;

    private final AngleController angleController;

    public DrivetrainPointAtAngleCommand(Drivetrain drivetrain, Angle angle) {
        this.drivetrain = drivetrain;
        this.angle = angle;

        this.angleController = Alignment.Angle.getPID();

        addRequirements(drivetrain);
    }

    @Override
    public void initialize() {
        drivetrain.setLowGear();
    }

    @Override
    public void execute() {
        drivetrain.arcadeDrive(0,
                angleController.update(angle, Angle.fromRotation2d(drivetrain.getPose().getRotation())));
    }
}
