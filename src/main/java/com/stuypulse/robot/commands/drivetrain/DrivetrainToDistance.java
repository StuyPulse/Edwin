package main.java.com.stuypulse.robot.commands.drivetrain;

import com.stuypulse.robot.subsystems.Drivetrain;
import com.stuypulse.stuylib.control.feedback.PIDController;
import com.stuypulse.stuylib.network.SmartNumber;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class DrivetrainToDistance extends CommandBase {

private static interface PID {
    SmartNumber kP = new SmartNumber("DrivetrainToDistance/kP", 0.0);
    SmartNumber kI = new SmartNumber("DrivetrainToDistance/kI", 0.0);
    SmartNumber kD = new SmartNumber("DrivetrainToDistance/kD", 0.0);
}

private final Drivetrain drivetrain;

private final PIDController controller;

private SmartNumber setpoint;

public DrivetrainToDistance(Drivetrain drivetrain) {
    this.drivetrain = drivetrain;

    controller = new PIDController(PID.kP, PID.kI, PID.kD);

    setpoint = new SmartNumber("DrivetrainToDistance/Setpoint", 0.0);

    addRequirements(drivetrain);
}

@Override
public void execute() {
    controller.update(setpoint.get(), drivetrain.getDistance());

    drivetrain.driveVolts(controller.getOutput(), controller.getOutput());
}

@Override
public boolean isFinished() {
    return controller.isDone(Units.inchesToMeters(1.0));
}

@Override
public void end(boolean interrupted) {
    drivetrain.stop();
}

}
