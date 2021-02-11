package com.stuypulse.robot.commands.auton.routines;

import com.stuypulse.robot.Constants;
import com.stuypulse.robot.Constants.Alignment;
import com.stuypulse.robot.Constants.ShooterSettings;

import com.stuypulse.robot.commands.*;
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

public class ShootThreeMoveTowardIntakeAutonCommand extends SequentialCommandGroup {

    public ShootThreeMoveTowardIntakeAutonCommand(Drivetrain drivetrain, Shooter shooter, Intake intake, Funnel funnel,
            Chimney chimney, LEDController controller) {

        final double DISTANCE_TO_MOVE_TOWARD_INTAKE = 4.0;

        addCommands(
            new LEDSetCommand(Color.WHITE_SOLID, controller),
                new ShooterControlCommand(shooter, ShooterSettings.INITATION_LINE_RPM,
                        ShooterMode.SHOOT_FROM_INITIATION_LINE),
                new WaitCommand(2),

                new LEDSetCommand(Color.YELLOW_SOLID, controller),
                new DrivetrainGoalCommand(drivetrain, Alignment.INITATION_LINE_DISTANCE).withTimeout(2.0),

                new DrivetrainStopCommand(drivetrain),

                new LEDSetCommand(Color.RED_SOLID, controller),
                new TimeoutCommand(new FeedBallsCommand(funnel, chimney), 3.0),

                new LEDSetCommand(Color.GREEN_SOLID, controller), new IntakeExtendCommand(intake), new WaitCommand(0.1),
                new IntakeAcquireForeverCommand(intake),

                new LEDSetCommand(Color.BLUE_SOLID, controller),
                new DrivetrainMovementCommand(drivetrain, 0, DISTANCE_TO_MOVE_TOWARD_INTAKE).withTimeout(2.0),

                new LEDSetCommand(Color.PURPLE_SOLID, controller), new IntakeAcquireCommand(intake) // makes intake stop
                                                                                                    // once auton ends
        );
    }

}