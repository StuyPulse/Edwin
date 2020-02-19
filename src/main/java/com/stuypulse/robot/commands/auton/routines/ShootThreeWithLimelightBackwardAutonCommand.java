package com.stuypulse.robot.commands.auton.routines;

import com.stuypulse.robot.Constants;
import com.stuypulse.robot.commands.DrivetrainAlignmentCommand;
import com.stuypulse.robot.commands.DrivetrainGoalAligner;
import com.stuypulse.robot.commands.DrivetrainInnerGoalAligner;
import com.stuypulse.robot.commands.DrivetrainMovementCommand;
import com.stuypulse.robot.commands.DrivetrainStopCommand;
import com.stuypulse.robot.commands.FeedAndShootOneBallAtTargetVelocityCommand;
import com.stuypulse.robot.commands.FeedBallsCommand;
import com.stuypulse.robot.commands.IntakeAcquireCommand;
import com.stuypulse.robot.commands.IntakeAcquireForeverCommand;
import com.stuypulse.robot.commands.IntakeExtendCommand;
import com.stuypulse.robot.commands.ShooterControlCommand;
import com.stuypulse.robot.subsystems.Chimney;
import com.stuypulse.robot.subsystems.Drivetrain;
import com.stuypulse.robot.subsystems.Funnel;
import com.stuypulse.robot.subsystems.Intake;
import com.stuypulse.robot.subsystems.Shooter;

import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class ShootThreeWithLimelightBackwardAutonCommand extends SequentialCommandGroup {


    public ShootThreeWithLimelightBackwardAutonCommand(Drivetrain drivetrain, Shooter shooter, Intake intake, Funnel funnel, Chimney chimney) {
        addCommands(
            new ShooterControlCommand(shooter, Constants.Shooting.INITATION_LINE_RPM),
            new WaitCommand(2),
            new ParallelRaceGroup(
                new DrivetrainAlignmentCommand(drivetrain, new DrivetrainGoalAligner(95.0/12.0)),
                //new DrivetrainAlignmentCommand(drivetrain, new DrivetrainInnerGoalAligner()),
                new WaitCommand(2.0)
            ),
            new ParallelRaceGroup(
                new FeedBallsCommand(shooter, funnel, chimney),
                new WaitCommand(3)
            ),
    
            //new FeedAndShootOneBallAtTargetVelocityCommand(funnel, chimney, shooter),
            //new FeedAndShootOneBallAtTargetVelocityCommand(funnel, chimney, shooter),
            //new FeedAndShootOneBallAtTargetVelocityCommand(funnel, chimney, shooter),
            //new FeedAndShootBallsAtTargetVelocityCommand(3, funnel, chimney, shooter),
            
            new DrivetrainStopCommand(drivetrain),
            new ParallelRaceGroup(
                new DrivetrainMovementCommand(drivetrain, 0, -10.0/12),
                new WaitCommand(1.0)
            ),
            new IntakeExtendCommand(intake),
            new IntakeAcquireForeverCommand(intake)


        );
    }
    
}