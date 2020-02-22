package com.stuypulse.robot.commands.auton.routines;

import com.stuypulse.robot.Constants;
import com.stuypulse.robot.Constants.Alignment;

import com.stuypulse.robot.commands.DrivetrainAlignmentCommand;
import com.stuypulse.robot.commands.DrivetrainGoalAligner;
import com.stuypulse.robot.commands.DrivetrainMovementCommand;
import com.stuypulse.robot.commands.DrivetrainStopCommand;
import com.stuypulse.robot.commands.FeedBallsCommand;
import com.stuypulse.robot.commands.IntakeAcquireSetupCommand;
import com.stuypulse.robot.commands.IntakeExtendCommand;
import com.stuypulse.robot.commands.LEDSetCommand;
import com.stuypulse.robot.commands.ShooterControlCommand;
import com.stuypulse.robot.commands.TimeoutCommand;
import com.stuypulse.robot.subsystems.Chimney;
import com.stuypulse.robot.subsystems.Drivetrain;
import com.stuypulse.robot.subsystems.Funnel;
import com.stuypulse.robot.subsystems.Intake;
import com.stuypulse.robot.subsystems.Shooter;
import com.stuypulse.robot.subsystems.Shooter.ShooterMode;
import com.stuypulse.robot.util.LEDController;
import com.stuypulse.robot.util.LEDController.Color;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class ShootThreeMoveTowardShooterAutonCommand extends SequentialCommandGroup {

    public ShootThreeMoveTowardShooterAutonCommand(Drivetrain drivetrain, Shooter shooter, Intake intake, Funnel funnel, Chimney chimney, LEDController controller) {

        final double DISTANCE_TO_MOVE_TOWARD_SHOOTER = -10.0 / 12.0;

        addCommands(
            new LEDSetCommand(Color.WHITE_SOLID, controller),
            new ShooterControlCommand(shooter, Constants.Shooting.INITATION_LINE_RPM, ShooterMode.SHOOT_FROM_INITIATION_LINE),
            new WaitCommand(2),

            new LEDSetCommand(Color.YELLOW_SOLID, controller),
            new DrivetrainAlignmentCommand(drivetrain, new DrivetrainGoalAligner(Constants.Alignment.INITATION_LINE_DISTANCE)).setSpeed(Alignment.Speed.LIMELIGHT_MAX_SPEED).setTimeout(2.0),

            new DrivetrainStopCommand(drivetrain),

            new LEDSetCommand(Color.RED_SOLID, controller),
            new TimeoutCommand(new FeedBallsCommand(shooter, funnel, chimney), 3.0),
            
            new LEDSetCommand(Color.GREEN_SOLID, controller),
            new DrivetrainMovementCommand(drivetrain, 0, DISTANCE_TO_MOVE_TOWARD_SHOOTER).setTimeout(1.0),
            
            new LEDSetCommand(Color.PURPLE_SOLID, controller),
            new IntakeAcquireSetupCommand(intake)
        );
    }
    
}