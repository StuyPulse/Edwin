package com.stuypulse.robot.commands;

import com.stuypulse.robot.Constants;
import com.stuypulse.robot.subsystems.Climber;
import com.stuypulse.robot.subsystems.Intake;
import com.stuypulse.stuylib.input.Gamepad;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ClimberMoveCommand extends CommandBase {
    Climber climber;
    Intake intake;
    Gamepad gamepad;

    public ClimberMoveCommand(Climber climber, Intake intake, Gamepad gamepad) {
        this.climber = climber;
        this.intake = intake;
        this.gamepad = gamepad;
        addRequirements(climber, intake);
    }

    @Override
    public void execute() {
        if (Math.abs(gamepad.getLeftX()) > Math.abs(gamepad.getLeftY())) {
            climber.moveYoyo(gamepad.getLeftX());
        } else {
            if (gamepad.getLeftY() > Constants.CLIMBER_MOVE_DEADBAND) {
                climber.releaseLiftBrake();
                intake.retract();
                climber.climbUp();
            } else if (gamepad.getLeftY() < -Constants.CLIMBER_MOVE_DEADBAND) {
                climber.releaseLiftBrake();
                climber.climbDown();
            } else {
                climber.stopClimber();
                climber.enableLiftBrake();
            }
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        climber.stopClimber();
        climber.enableLiftBrake();
    }

}