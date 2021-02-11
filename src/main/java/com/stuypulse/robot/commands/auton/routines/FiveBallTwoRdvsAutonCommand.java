package com.stuypulse.robot.commands.auton.routines;

import com.stuypulse.robot.Constants;
import com.stuypulse.robot.Constants.Alignment;
import com.stuypulse.robot.Constants.ShooterSettings;
import com.stuypulse.robot.commands.DrivetrainAlignmentCommand;
import com.stuypulse.robot.commands.DrivetrainGoalAligner;
import com.stuypulse.robot.commands.DrivetrainGoalCommand;
import com.stuypulse.robot.commands.DrivetrainMovementCommand;
import com.stuypulse.robot.commands.FeedBallsCommand;
import com.stuypulse.robot.commands.IntakeAcquireCommand;
import com.stuypulse.robot.commands.IntakeAcquireForeverCommand;
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

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class FiveBallTwoRdvsAutonCommand extends SequentialCommandGroup {

    public FiveBallTwoRdvsAutonCommand(Intake intake, Shooter shooter, Funnel funnel, Chimney chimney,
            Drivetrain drivetrain, LEDController controller) {
        final double DISTANCE_TO_RDVS_IN_FEET = 10.75;
        final double ANGLE_TO_RDVS = 70.0;
        final double DISTANCE_TO_ACQUIRE_RDVS_BALLS_IN_FEET = 2.35;
        final double DISTANCE_TO_BACKUP_AFTER_RDVS = -1.6;
        final double ANGLE_TO_TRENCH = -50.0;
        final double DISTANCE_TO_ACQUIRE_TRENCH_BALLS = 8.5;
        final double ANGLE_TO_WIGGLE = 10.0;

        addCommands(
                new LEDSetCommand(Color.WHITE_SOLID, controller), 
                new IntakeExtendCommand(intake),
                new ShooterControlCommand(shooter, ShooterSettings.INITATION_LINE_RPM,
                        ShooterMode.SHOOT_FROM_INITIATION_LINE),

                new WaitCommand(1.0), 
                new IntakeAcquireForeverCommand(intake),

                new LEDSetCommand(Color.RED_SOLID, controller),
                new TimeoutCommand(new FeedBallsCommand(funnel, chimney), 1.0),

                new ShooterControlCommand(shooter, ShooterSettings.TRENCH_RPM, ShooterMode.SHOOT_FROM_TRENCH),
                new WaitCommand(0.5),

                new LEDSetCommand(Color.ORANGE_SOLID, controller),
                new DrivetrainMovementCommand(drivetrain, 0, DISTANCE_TO_RDVS_IN_FEET).setMaxSpeed(0.9)
                        .withTimeout(15.0),

                new LEDSetCommand(Color.YELLOW_SOLID, controller),
                new DrivetrainMovementCommand(drivetrain, ANGLE_TO_RDVS).withTimeout(1.0),

                // new LEDSetCommand(Color.GREEN_SOLID, controller),
                // new ShooterControlCommand(shooter, ShooterSettings.TRENCH_RPM,
                // ShooterMode.SHOOT_FROM_TRENCH),

                new LEDSetCommand(Color.BLUE_SOLID, controller),
                new DrivetrainMovementCommand(drivetrain, 0, DISTANCE_TO_ACQUIRE_RDVS_BALLS_IN_FEET).setMaxSpeed(0.65)
                        .withTimeout(1.5),

                new DrivetrainMovementCommand(drivetrain, ANGLE_TO_WIGGLE).withTimeout(1.0),
                new DrivetrainMovementCommand(drivetrain, -ANGLE_TO_WIGGLE).withTimeout(1.0),

                new DrivetrainMovementCommand(drivetrain, 0, DISTANCE_TO_BACKUP_AFTER_RDVS).withTimeout(1.5),
                new LEDSetCommand(Color.PURPLE_SOLID, controller),
                new DrivetrainMovementCommand(drivetrain, -ANGLE_TO_RDVS).withTimeout(0.75),

                new LEDSetCommand(Color.GREEN_SOLID, controller),
                new DrivetrainGoalCommand(drivetrain, Alignment.TRENCH_DISTANCE).withTimeout(15.0),

                new LEDSetCommand(Color.BLUE_SOLID, controller),
                new ParallelCommandGroup(new FeedBallsCommand(funnel, chimney), new IntakeAcquireCommand(intake)));

    }

}