package com.stuypulse.robot.commands;

import com.stuypulse.robot.Constants;
import com.stuypulse.robot.subsystems.ControlPanel;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ControlPanelSpinToColorCommand extends CommandBase {
    private final ControlPanel cPanel;
    private Color goal;
    public ControlPanelSpinToColorCommand(ControlPanel cPanel) {
        this.cPanel = cPanel;
        addRequirements(cPanel);
    }

    @Override
    public void initialize() {
    }

    private void setTargetColor() {
        String gameData = DriverStation.getInstance().getGameSpecificMessage();
        if(gameData != null && gameData.length() > 0) {

            switch (gameData.charAt(0)) {
                case 'B' :
                    goal = Color.kCyan;
                    break;
                case 'G' :
                    goal = Color.kGreen;
                    break;
                case 'R' :
                    goal = Color.kRed;
                    break;
                case 'Y' :
                    goal = Color.kYellow;
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
        } else {
            cPanel.turn(Constants.CONTROL_PANEL_TURN_SPEED);
        }
    }
    @Override
    public boolean isFinished() {
        return goal == cPanel.getColor();
    }

    @Override
    public void end(boolean interrupted) {
        // code to run when ends
        cPanel.stop();
    }
}