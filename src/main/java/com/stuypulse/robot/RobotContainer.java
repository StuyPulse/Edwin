/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.stuypulse.robot;

import edu.wpi.first.wpilibj2.command.Command;
import com.stuypulse.robot.subsystems.*;
import com.stuypulse.robot.commands.*;

import com.stuypulse.stuylib.input.Gamepad;
import com.stuypulse.stuylib.input.gamepads.*:

import com.stuypulse.robot.Constants.Ports;

import java.util.ResourceBundle.Control;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {

  private final boolean DEBUG = true;

  private final Funnel funnel = new Funnel();
  private final Climber climber = new Climber();
  private final Drivetrain drivetrain = new Drivetrain();
  private final Intake intake = new Intake();
  private final ControlPanel controlPanel = new ControlPanel();
  private final Shooter shooter = new Shooter();
  
  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {

    // Default driving command that uses gamepad
    drivetrain.setDefaultCommand(new DrivetrainDriveCommand(drivetrain, driver));

    // Configure the button bindings
    configureButtonBindings();

    controlPanel.setDefaultCommand(new ControlPanelManualControlCommand(controlPanel, operator));
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    driver.getLeftButton().whenHeld(new DrivetrainAlignmentCommand(drivetrain, new DrivetrainGoalAligner(10)));
    driver.getTopButton().whenHeld(new DrivetrainAlignmentCommand(drivetrain, new DrivetrainGoalAligner(20)));

    /**
     * 
     */

    if(DEBUG) {
      // Auto alignment for angle and speed and update pid values
      debug.getLeftButton().toggleWhenPressed(new DrivetrainAutoAngleCommand(drivetrain, new DrivetrainGoalAligner(10)));
      debug.getTopButton().toggleWhenPressed(new DrivetrainAutoSpeedCommand(drivetrain, new DrivetrainGoalAligner(10)));

      // Steal driving abilities from the driver
      debug.getBottomButton().toggleWhenPressed(new DrivetrainDriveCommand(drivetrain, debug));

      // DPad controls for 90 degree turns and 2.5 ft steps
      debug.getDPadUp().whenPressed(new DrivetrainMovementCommand(drivetrain, 0, 2.5));
      debug.getDPadDown().whenPressed(new DrivetrainMovementCommand(drivetrain, 0, -2.5));
      debug.getDPadLeft().whenPressed(new DrivetrainMovementCommand(drivetrain, -90));
      debug.getDPadRight().whenPressed(new DrivetrainMovementCommand(drivetrain, 90));
    }
  }


  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return null;
  }

}