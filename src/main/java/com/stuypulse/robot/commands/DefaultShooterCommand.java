package com.stuypulse.robot.commands;

import com.stuypulse.robot.Constants;
import com.stuypulse.robot.subsystems.Shooter;
import com.stuypulse.stuylib.control.Controller;
import com.stuypulse.stuylib.control.PIDCalculator;
import com.stuypulse.stuylib.control.PIDController;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class DefaultShooterCommand extends CommandBase {
    public Shooter shooter;
    public Controller speedController;

    public DefaultShooterCommand(Shooter shooter, Controller speedController) {
        this.shooter = shooter;
        this.speedController = speedController;
    }
    
    public DefaultShooterCommand(Shooter shooter) {
        this(shooter, new PIDController());
    }

    @Override
    public void execute() {
        if(speedController instanceof PIDController) {
            PIDController controller = (PIDController) speedController;

            controller.setP(Constants.SHOOTER_SHOOT_P.get());
            controller.setI(Constants.SHOOTER_SHOOT_I.get());
            controller.setD(Constants.SHOOTER_SHOOT_D.get());
        }

        if(speedController instanceof PIDCalculator) {
            
            PIDCalculator calculator = (PIDCalculator) speedController;
            PIDController controller = (PIDController) calculator.getPIDController();

            Constants.SHOOTER_SHOOT_P.set(controller.getP());
            Constants.SHOOTER_SHOOT_I.set(controller.getI());
            Constants.SHOOTER_SHOOT_D.set(controller.getD());
        }

        double error = shooter.getTargetVelocity() - shooter.getCurrentShooterVelocityInRPM();
        shooter.setShooterSpeed(speedController.update(error));
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        throw new RuntimeException("If this gets run, shit's fucked");
    }
}