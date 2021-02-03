package com.stuypulse.robot.commands;

import com.stuypulse.robot.Constants.Shooting;
import com.stuypulse.robot.Constants.Ports.Gamepad;
import com.stuypulse.robot.subsystems.Shooter;
import com.stuypulse.stuylib.control.Controller;
import com.stuypulse.stuylib.control.PIDCalculator;
import com.stuypulse.stuylib.control.PIDController;
import com.stuypulse.stuylib.input.WPIGamepad;
import com.stuypulse.stuylib.math.SLMath;
import com.stuypulse.stuylib.streams.filters.IFilter;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ShooterDefaultCommand extends CommandBase {
    public static final IFilter INTEGRAL_FILTER = (x) -> SLMath.limit(x, Shooting.I_LIMIT.doubleValue());
    public static final IFilter RESET_FILTER = (x) -> 0;

    public Shooter shooter;
    public Controller shootController;
    public Controller feedController;

    public ShooterDefaultCommand(Shooter shooter, Controller shootController,Controller feedController) {
        this.shooter = shooter;
        this.shootController = shootController;
        this.feedController = feedController;

        addRequirements(this.shooter);
    }

    public ShooterDefaultCommand(Shooter shooter) {
        this(shooter, new PIDController(
            Shooting.Shooter.P,
            Shooting.Shooter.I,
            Shooting.Shooter.D
        ), new PIDController(
            Shooting.Feeder.P,
            Shooting.Feeder.I,
            Shooting.Feeder.D
        ));
    }

    public void updateAutoPID() {
        if (shootController instanceof PIDCalculator) {

            PIDCalculator calculator = (PIDCalculator) shootController;
            PIDController controller = (PIDController) calculator.getPIDController();

            Shooting.Shooter.P.set(controller.getP());
            Shooting.Shooter.I.set(controller.getI());
            Shooting.Shooter.D.set(controller.getD());
        }

        if (feedController instanceof PIDCalculator) {

            PIDCalculator calculator = (PIDCalculator) feedController;
            PIDController controller = (PIDController) calculator.getPIDController();

            Shooting.Feeder.P.set(controller.getP());
            Shooting.Feeder.I.set(controller.getI());
            Shooting.Feeder.D.set(controller.getD());
        }
    }

    public void updateShooter() {
        // Target speed to go at
        double speed = shooter.getCurrentShooterVelocityInRPM();
        double target = shooter.getTargetVelocity();

        // The error from current speed to target
        double error = target - speed;

        // Speed to set the motor plus feed forward
        double output = shootController.update(error);
        output += target * Shooting.Shooter.FF.get();

        // Set the shooter to that
        shooter.setShooterSpeed(output);
    }

    public void updateFeeder() {
        // Target speed to go at
        double speed = shooter.getCurrentFeederVelocityInRPM();
        double target = shooter.getTargetVelocity() * Shooting.Feeder.SPEED_MUL;

        // The error from current speed to target
        double error = target - speed;

        // Speed to set the motor plus feed forward
        double output = feedController.update(error);
        output += target * Shooting.Feeder.FF.get();

        // Set the shooter to that
        shooter.setFeederSpeed(output);
    }

    @Override
    public void execute() {
        // If using AutoPID, report its values
        updateAutoPID();

        // Update shooter and feeder with controller
        updateShooter();
        updateFeeder();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
