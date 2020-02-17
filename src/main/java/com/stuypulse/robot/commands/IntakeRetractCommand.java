package com.stuypulse.robot.commands;

import com.stuypulse.robot.subsystems.Intake;
import edu.wpi.first.wpilibj2.command.InstantCommand;;

public class IntakeRetractCommand extends InstantCommand {

  private final Intake intake;

  public IntakeRetractCommand(Intake intake) {
    this.intake = intake;
    addRequirements(intake);
  }

  @Override
  public void initialize() {
      intake.retract();
  }
}