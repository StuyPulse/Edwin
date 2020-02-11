package com.stuypulse.robot.commands;

import com.stuypulse.robot.subsystems.Intake;
import edu.wpi.first.wpilibj2.command.InstantCommand;;

public class IntakeExtendCommand extends InstantCommand {
  private final Intake intake;

  public IntakeExtendCommand(Intake intake) {
    this.intake = intake;
    addRequirements(this.intake);
  }

  @Override
  public void initialize() {
      intake.extend();
  }
}