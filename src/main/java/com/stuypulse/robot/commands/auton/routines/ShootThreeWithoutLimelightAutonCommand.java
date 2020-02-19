package com.stuypulse.robot.commands.auton.routines;

import com.stuypulse.robot.Constants;
import com.stuypulse.robot.commands.DrivetrainMovementCommand;
import com.stuypulse.robot.commands.DrivetrainStopCommand;
import com.stuypulse.robot.commands.FeedAndShootBallsAtTargetVelocityCommand;
import com.stuypulse.robot.commands.FeedAndShootOneBallAtTargetVelocityCommand;
import com.stuypulse.robot.commands.ShooterControlCommand;
import com.stuypulse.robot.subsystems.Chimney;
import com.stuypulse.robot.subsystems.Drivetrain;
import com.stuypulse.robot.subsystems.Funnel;
import com.stuypulse.robot.subsystems.Shooter;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandGroupBase;

public class ShootThreeWithoutLimelightAutonCommand extends CommandGroupBase {

    private final double FEET_TO_MOVE_AFTER_SHOOTING = -1;

    public ShootThreeWithoutLimelightAutonCommand(Drivetrain drivetrain, Shooter shooter, Funnel funnel, Chimney chimney) {
        addCommands(
            new ShooterControlCommand(shooter, Constants.Shooting.INITATION_LINE_RPM),
            new FeedAndShootBallsAtTargetVelocityCommand(3, funnel, chimney, shooter),
            // new FeedAndShootOneBallAtTargetVelocityCommand(funnel, chimney, shooter),
            new DrivetrainMovementCommand(drivetrain, 0, FEET_TO_MOVE_AFTER_SHOOTING),
            new DrivetrainStopCommand(drivetrain));
    }

    @Override
    public void addCommands(Command... commands) {
        // TODO Auto-generated method stub

    }
    
}