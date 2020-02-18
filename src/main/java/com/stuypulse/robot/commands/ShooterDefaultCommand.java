package com.stuypulse.robot.commands;

import com.stuypulse.robot.Constants;
import com.stuypulse.robot.subsystems.Shooter;
import com.stuypulse.stuylib.control.Controller;
import com.stuypulse.stuylib.control.PIDCalculator;
import com.stuypulse.stuylib.control.PIDController;
import com.stuypulse.stuylib.input.WPIGamepad;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ShooterDefaultCommand extends CommandBase {
    public Shooter shooter;
    public WPIGamepad gamepad;
    public Controller shootController;
    public Controller feedController;

    public ShooterDefaultCommand(Shooter shooter, WPIGamepad gamepad, Controller shootController,
        Controller feedController) {
        this.shooter = shooter;
        this.gamepad = gamepad;
        this.shootController = shootController;
        this.feedController = feedController;

        addRequirements(this.shooter);

    }

    public ShooterDefaultCommand(Shooter shooter, WPIGamepad gamepad) {
        this(shooter, gamepad, new PIDController(), new PIDController());

        addRequirements(this.shooter);
    }

    public void updatePID() {
        if (shootController instanceof PIDController) {
            PIDController controller = (PIDController) shootController;

            controller.setP(Constants.SHOOTER_P.get());
            controller.setI(Constants.SHOOTER_I.get());
            controller.setD(Constants.SHOOTER_D.get());
        }

        if (feedController instanceof PIDController) {
            PIDController controller = (PIDController) feedController;

            controller.setP(Constants.FEEDER_P.get());
            controller.setI(Constants.FEEDER_I.get());
            controller.setD(Constants.FEEDER_D.get());
        }
    }

    public void updateAutoPID() {
        if (shootController instanceof PIDCalculator) {

            PIDCalculator calculator = (PIDCalculator) shootController;
            PIDController controller = (PIDController) calculator.getPIController();

            Constants.SHOOTER_P.set(controller.getP());
            Constants.SHOOTER_I.set(controller.getI());
            Constants.SHOOTER_D.set(controller.getD());
        }

        if (feedController instanceof PIDCalculator) {

            PIDCalculator calculator = (PIDCalculator) feedController;
            PIDController controller = (PIDController) calculator.getPIController();

            Constants.FEEDER_P.set(controller.getP());
            Constants.FEEDER_I.set(controller.getI());
            Constants.FEEDER_D.set(controller.getD());
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
        output += target * Constants.SHOOTER_FF.get();

        // Set the shooter to that
        shooter.setShooterSpeed(output);

        double rumbleMag = Math.abs(speed - target);
        rumbleMag = Constants.SHOOTER_TOLERANCE - rumbleMag;
        rumbleMag = Math.max(error, 0.0);
        rumbleMag /= Constants.SHOOTER_TOLERANCE;

        gamepad.setRumble(rumbleMag);
    }

    public void updateFeeder() {
        // Target speed to go at
        double speed = shooter.getCurrentFeederVelocityInRPM();
        double target = shooter.getTargetVelocity() * Constants.FEEDER_SPEED_MUL;

        // The error from current speed to target
        double error = target - speed;

        // Speed to set the motor plus feed forward
        double output = shootController.update(error);
        output += target * Constants.FEEDER_FF.get();

        // Set the shooter to that
        shooter.setFeederSpeed(output);
    }

    @Override
    public void execute() {

        // If using PID, get its PID values
        updatePID();

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
