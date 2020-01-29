package com.stuypulse.robot.commands;

import com.stuypulse.robot.subsystems.ControlPanel;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * An example command that uses an example subsystem.
 */
public class ControlPanelTurnAutoCommand extends CommandBase {
    private final ControlPanel m_cPanel;
    private Color goal;

    /**
     * Creates a new ExampleCommand.
     *
     * @param subsystem The subsystem used by this command.
     */
    public ControlPanelTurnAutoCommand(ControlPanel subsystem) {
        m_cPanel = subsystem;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(subsystem);
    }

    @Override
    public void initialize() {
        String gameData = DriverStation.getInstance().getGameSpecificMessage();
        if(gameData.length() > 0) {
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
            m_cPanel.turnToColor(goal);
        }

    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
