/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.stuypulse.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import com.stuypulse.robot.subsystems.Funnel;

import java.util.ResourceBundle.Control;

import com.stuypulse.robot.subsystems.Climber;
import com.stuypulse.robot.subsystems.ControlPanel;
import com.stuypulse.robot.subsystems.Drivetrain;
import com.stuypulse.robot.subsystems.Intake;
import com.stuypulse.stuylib.input.gamepads.Logitech;
import com.stuypulse.robot.commands.ClimberMoveCommand;
import com.stuypulse.robot.commands.ClimberToggleLiftBreakCommand;
import com.stuypulse.robot.commands.ControlPanelManualControlCommand;
/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
import com.stuypulse.stuylib.input.gamepads.PS4;
public class RobotContainer {

  private final Funnel funnel = new Funnel();
  private final Climber climber = new Climber();
  private final Drivetrain drivetrain = new Drivetrain();
  private final Intake intake = new Intake();
  private final ControlPanel controlPanel = new ControlPanel();

  private final PS4 driverGampead = new PS4(Constants.DRIVER_GAMEPAD_PORT);
  private final Logitech operatorGamepad = new Logitech(Constants.OPERATOR_GAMEPAD_PORT);

  
  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();

    controlPanel.setDefaultCommand(new ControlPanelManualControlCommand(controlPanel));
    
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    operatorGamepad.getLeftAnalogButton().whenPressed(new ClimberToggleLiftBreakCommand(climber));
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
