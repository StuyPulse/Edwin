package com.stuypulse.robot.commands.auton.routines;

import com.stuypulse.robot.Constants;
import com.stuypulse.robot.commands.DrivetrainAlignmentCommand;
import com.stuypulse.robot.commands.DrivetrainGoalAligner;
import com.stuypulse.robot.commands.DrivetrainMovementCommand;
import com.stuypulse.robot.commands.DrivetrainStopCommand;
import com.stuypulse.robot.commands.FeedBallsCommand;
import com.stuypulse.robot.commands.IntakeAcquireCommand;
import com.stuypulse.robot.commands.IntakeAcquireForeverCommand;
import com.stuypulse.robot.commands.IntakeExtendCommand;
import com.stuypulse.robot.commands.ShooterControlCommand;
import com.stuypulse.robot.commands.TimeoutCommand;
import com.stuypulse.robot.subsystems.Chimney;
import com.stuypulse.robot.subsystems.Drivetrain;
import com.stuypulse.robot.subsystems.Funnel;
import com.stuypulse.robot.subsystems.Intake;
import com.stuypulse.robot.subsystems.Shooter;
import com.stuypulse.robot.subsystems.Shooter.ShooterMode;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class ShootThreeMoveTowardIntakeAutonCommand extends SequentialCommandGroup {

    public ShootThreeMoveTowardIntakeAutonCommand(Drivetrain drivetrain, Shooter shooter, Intake intake, Funnel funnel, Chimney chimney) {
        
        final double DISTANCE_TO_MOVE_TOWARD_INTAKE = 4.0;

        addCommands(
            new ShooterControlCommand(shooter, Constants.Shooting.INITATION_LINE_RPM, ShooterMode.SHOOT_FROM_INITIATION_LINE),

            new WaitCommand(2),
            new DrivetrainAlignmentCommand(drivetrain, new DrivetrainGoalAligner(Constants.Alignment.INITATION_LINE_DISTANCE)).setTimeout(2.0),
            new TimeoutCommand(new FeedBallsCommand(shooter, funnel, chimney), 3.0),
            new DrivetrainStopCommand(drivetrain),

            new IntakeExtendCommand(intake),
            new WaitCommand(0.1),
            new IntakeAcquireForeverCommand(intake),
            
            new DrivetrainMovementCommand(drivetrain, 0, DISTANCE_TO_MOVE_TOWARD_INTAKE).setTimeout(2.0),
            new IntakeAcquireCommand(intake) // makes intake stop once auton ends

        );
    }
    
}