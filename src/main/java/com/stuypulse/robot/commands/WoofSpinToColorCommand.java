package com.stuypulse.robot.commands;

import com.stuypulse.robot.Constants.WoofSettings;
import com.stuypulse.robot.subsystems.Woof;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class WoofSpinToColorCommand extends CommandBase {

    private final Woof woof;
    private Color goal;

    public WoofSpinToColorCommand(Woof woof) {
        this.woof = woof;
        addRequirements(woof);
    }

    private void setTargetColor() {
        String gameData = DriverStation.getInstance().getGameSpecificMessage();
        if (gameData != null && gameData.length() > 0) {
            switch (gameData.charAt(0)) {
                case 'B':
                    goal = Color.kRed;
                    break;
                case 'G':
                    goal = Color.kYellow;
                    break;
                case 'R':
                    goal = Color.kCyan;
                    break;
                case 'Y':
                    goal = Color.kGreen;
                    break;
                default:
                    goal = null;
                    break;
            }
        }
    }

    @Override
    public void execute() {
        if (goal == null) {
            setTargetColor();
        } else if ((woof.getColor() == Color.kRed && goal == Color.kYellow)
                || (woof.getColor() == Color.kGreen && goal == Color.kRed)
                || (woof.getColor() == Color.kBlue && goal == Color.kGreen)
                || (woof.getColor() == Color.kYellow && goal == Color.kBlue)) {
            woof.turn(-WoofSettings.TURN_SPEED);
        } else {
            woof.turn(WoofSettings.TURN_SPEED);
        }
    }

    @Override
    public boolean isFinished() {
        return goal == woof.getColor();
    }

    @Override
    public void end(boolean interrupted) {
        // code to run when ends
        woof.stop();
    }
}