package com.stuypulse.robot.util.gear;

public class EncoderIntegrator {
    
    private double accumulated;
    private double lastRotations;

    public EncoderIntegrator() {
        reset();
    }

    public void reset() {
        accumulated = 0.0;
        lastRotations = 0.0;
    }

    public void update(Gear gear, double currentRotations) {
        double rotations = currentRotations - lastRotations;
        double distance = gear.getScaledDistance(rotations);

        accumulated += distance;
        lastRotations = currentRotations;
    }

    public double getDistance() {
        return accumulated;
    }

}
