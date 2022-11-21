package com.stuypulse.robot.subsystems;

import com.stuypulse.stuylib.network.SmartBoolean;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * @author Ivan Chen (@anivanchen)
 */
public class Pump extends SubsystemBase {
    
    private final SmartBoolean enabled;
    private final Compressor compressor;

    public Pump() {
        enabled = new SmartBoolean("Pump/Enabled", true);
        compressor = new Compressor(PneumaticsModuleType.CTREPCM);
    }

    public void compress() {
        enabled.set(true);
    }

    public void stop() {
        enabled.set(false);
    }

    @Override
    public void periodic() {
        if (enabled.get() == true) compressor.enableDigital();
        else compressor.disable();
    }
}