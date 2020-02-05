package com.stuypulse.robot.commands;

import com.stuypulse.robot.subsystems.Intake;
import edu.wpi.first.wpilibj2.command.InstantCommand;;

public class IntakeToggleCommand extends InstantCommand {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Intake intake;

  public IntakeToggleCommand(Intake intake) {
    this.intake = intake;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(this.intake);
  }

  @Override
  public void initialize() {
    if(intake.isExtended())
      intake.retract();
    else {
      intake.extend();
    }
  }
}