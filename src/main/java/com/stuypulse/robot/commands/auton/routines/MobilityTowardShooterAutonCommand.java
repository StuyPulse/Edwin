package com.stuypulse.robot.commands.auton.routines;

import com.stuypulse.robot.Constants;
import com.stuypulse.robot.commands.DrivetrainMovementCommand;
import com.stuypulse.robot.commands.DrivetrainStopCommand;
import com.stuypulse.robot.commands.LEDSetCommand;
import com.stuypulse.robot.subsystems.Drivetrain;
import com.stuypulse.robot.util.LEDController;
import com.stuypulse.robot.util.LEDController.Color;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class MobilityTowardShooterAutonCommand extends SequentialCommandGroup {
    public MobilityTowardShooterAutonCommand(Drivetrain drivetrain, LEDController controller) {
        addCommands(
            new LEDSetCommand(Color.YELLOW_SOLID, controller),
            new DrivetrainMovementCommand(drivetrain, 0, -Constants.DISTANCE_TO_MOVE_AT_START),
            new LEDSetCommand(Color.RED_SOLID, controller),
            new DrivetrainStopCommand(drivetrain),
            new LEDSetCommand(Color.PURPLE_SOLID, controller)
        );
    }


}