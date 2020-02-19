package com.stuypulse.robot.commands;

import com.stuypulse.robot.subsystems.Intake;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class IntakeDeacquireCommand extends CommandBase {
  
  private final Intake intake;

  public IntakeDeacquireCommand(Intake intake) {
    this.intake = intake;
    addRequirements(intake);
  }

  @Override
  public void initialize() {

  }

  @Override
  public void execute() {
    intake.deacquire();
  }
  
  @Override
  public void end(boolean interrupted) {
    intake.stop();
  }
}