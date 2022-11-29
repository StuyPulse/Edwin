package com.stuypulse.robot.commands.conveyor;

import com.stuypulse.robot.subsystems.Conveyor;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ConveyorLiftDownCommand extends CommandBase {
    private final Conveyor conveyor;

    public ConveyorLiftDownCommand(Conveyor conveyor) {
        this.conveyor = conveyor;
        addRequirements(conveyor);
    }

    @Override
    public void execute() {
        conveyor.unfunnel();
        conveyor.liftDown();
    }

    @Override
    public void end(boolean interrupted) {
        conveyor.stopFunnel();
        conveyor.stopLift();
    }
}
