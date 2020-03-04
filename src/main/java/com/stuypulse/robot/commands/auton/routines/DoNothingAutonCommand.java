package com.stuypulse.robot.commands.auton.routines;

import com.stuypulse.robot.commands.LEDSetCommand;
import com.stuypulse.robot.util.LEDController;
import com.stuypulse.robot.util.LEDController.Color;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class DoNothingAutonCommand extends SequentialCommandGroup {
    
    public DoNothingAutonCommand(LEDController controller) {
        addCommands(
            new LEDSetCommand(Color.RED_SOLID, controller)
            // new DrivetrainMovementCommand(drivetrain, 0, 0).setNeverFinish().withTimeout(15)
        );
    }

}