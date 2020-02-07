package com.stuypulse.robot.commands.auton.routines;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import com.stuypulse.robot.commands.DriveCommand;
import com.stuypulse.robot.subsystems.Drivetrain;
import com.stuypulse.stuylib.input.Gamepad;

public class MobilityAutonCommand extends SequentialCommandGroup {
    private final Drivetrain drivetrain = new Drivetrain();
    private final Gamepad gamepad = new Gamepad();
    public MobilityAutonCommand() {
        //Constructors
        addCommands(new DriveCommand(drivetrain,gamepad));
    }


}