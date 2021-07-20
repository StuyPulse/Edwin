package com.stuypulse.robot.util;

import com.revrobotics.CANEncoder;
import com.stuypulse.robot.subsystems.Drivetrain.Gear;

/**
 * A wrapper class for a 'raw' encoder that provides 
 * additional functionality like handling gear shifting and
 * can make the encoder easier to use / the methods easier to 
 * understand. 
 * 
 * TODO: velocity needs to be tested
 * 
 * @author Myles Pasetsky (@selym3)
 */
public class Encoder {
    
    // The encoder class that is closest to hardware
    private CANEncoder encoder;

    // These values are used to integrate an accurate distance
    // while switching gears 
    private double accumulated;
    private double lastRotations;

    /**
     * Create an Encoder wrapper
     * 
     * @param encoder the underlying encoder class
     */
    public Encoder(CANEncoder encoder) {
        this.encoder = encoder; 
        reset();
    }

    /**
     * Get the raw encoder rotations (the unscaled value)
     * 
     * @return encoder rotations
     */
    public double getRotations() {
        return encoder.getPosition();
    }

    /**
     * Gets the distance the encoder has traveled (the scaled value)
     * 
     * @return distance traveled
     */
    public double getDistance() {
        return accumulated;
    }
    
    /**
     * Gets the raw rotations per minute (the unscaled value)
     * 
     * @return rotations per minute
     */
    public double getRPM() {
        return encoder.getVelocity();
    }

    // This would return the scaled rotations per minute (real velocity)
    // public double getVelocity() {
    //     return (implement delta accumulated)
    // }

    /**
     * Resets the state of the encoder -- both the underlying 
     * encoder and the addtional measurements. 
     */
    public void reset() {
        // Reset encoder tracking
        encoder.setPositionConversionFactor(1.0); // <-- always ensure this is true
        encoder.setPosition(0);

        // Reset distance tracking
        accumulated = 0.0;
        lastRotations = 0.0;
    }

    /**
     * Updates the state of the encoder, based on a gear
     * 
     * @param gear gear of the encoder
     */
    public void periodic(Gear gear) {
        final double nowRotations = getRotations();

        double rotations = nowRotations - lastRotations;
        double distance = gear.getScaledDistance(rotations);

        accumulated += distance;
        lastRotations = nowRotations;
    }

    

}
