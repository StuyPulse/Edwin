package com.stuypulse.robot.commands;

import com.stuypulse.robot.Constants;
import com.stuypulse.robot.subsystems.ControlPanel;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ControlPanelSpinToColorCommand extends CommandBase {

    private final ControlPanel controlPanel;
    private Color goal;
    public ControlPanelSpinToColorCommand(ControlPanel controlPanel) {
        this.controlPanel = controlPanel;
        addRequirements(controlPanel);
    }

    private void setTargetColor() {
        String gameData = DriverStation.getInstance().getGameSpecificMessage();
        if(gameData != null && gameData.length() > 0) {
            switch (gameData.charAt(0)) {
                case 'B' :
                    goal = Color.kRed;
                    break;
                case 'G' :
                    goal = Color.kYellow;
                    break;
                case 'R' :
                    goal = Color.kCyan;
                    break;
                case 'Y' :
                    goal = Color.kGreen;
                    break;
                default :
                    goal = null;
                    break;
            }
        }
    }

    @Override
    public void execute() {
        if (goal == null) {
            setTargetColor();
        } 
        else if((controlPanel.getColor() == Color.kRed && goal == Color.kYellow) ||
                (controlPanel.getColor() == Color.kGreen && goal == Color.kRed) ||
                (controlPanel.getColor() == Color.kBlue && goal == Color.kGreen) ||
                (controlPanel.getColor() == Color.kYellow && goal == Color.kBlue)) {
                controlPanel.turn(-Constants.CONTROL_PANEL_TURN_SPEED);
            }
        else {    
            controlPanel.turn(Constants.CONTROL_PANEL_TURN_SPEED);
        }
    }
    
    @Override
    public boolean isFinished() {
        return goal == controlPanel.getColor();
    }

    @Override
    public void end(boolean interrupted) {
        // code to run when ends
        controlPanel.stop();
    }
}