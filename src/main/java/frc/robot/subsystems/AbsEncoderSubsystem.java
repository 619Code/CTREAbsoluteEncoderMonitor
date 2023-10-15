// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.HashMap;
import java.util.Map;

import com.ctre.phoenix.sensors.CANCoderFaults;
import com.ctre.phoenix.sensors.CANCoderStickyFaults;
import com.ctre.phoenix.sensors.MagnetFieldStrength;
import com.ctre.phoenix.sensors.WPI_CANCoder;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.helpers.Crashboard;

public class AbsEncoderSubsystem extends SubsystemBase {

  HashMap<String, WPI_CANCoder> canCoders;

  /** Creates a new ExampleSubsystem. */
  public AbsEncoderSubsystem(HashMap<String, Integer> encoders) {

    canCoders = new HashMap<>();

    for(var entry : encoders.entrySet()) {
      String key = entry.getKey();
      Integer canId = entry.getValue();
      
      var canCoder = new WPI_CANCoder(canId);
      
      canCoders.put(key, canCoder);
    }
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    for(var entry : canCoders.entrySet()) {
      
      var tabName = entry.getKey();
      var canCoder = entry.getValue();
      logIt(canCoder, tabName);
    }
  }

  private void logIt(WPI_CANCoder canCoder, String name) {
    /* Report position, absolute position, velocity, battery voltage */
    double posValue = canCoder.getPosition();
    String posUnits = canCoder.getLastUnitString();
    double posTstmp = canCoder.getLastTimestamp();
    logValue(posValue, posUnits, posTstmp, "Position", name);
    
    double absValue = canCoder.getAbsolutePosition();
    String absUnits = canCoder.getLastUnitString();
    double absTstmp = canCoder.getLastTimestamp();
    logValue(absValue, absUnits, absTstmp, "Absolute", name);

    double velValue = canCoder.getVelocity();
    String velUnits = canCoder.getLastUnitString();
    double velTstmp = canCoder.getLastTimestamp();
    logValue(velValue, velUnits, velTstmp, "Velocity", name);

    double batValue = canCoder.getBusVoltage();
    String batUnits = canCoder.getLastUnitString();
    double batTstmp = canCoder.getLastTimestamp();
    logValue(batValue, batUnits, batTstmp, "Voltage", name);

    /* Report miscellaneous attributes about the CANCoder */
    // MagnetFieldStrength magnetStrength = canCoder.getMagnetFieldStrength();
    // String magnetStrengthUnits = canCoder.getLastUnitString();
    // double magnetStrengthTstmp = canCoder.getLastTimestamp();

    
    /* Fault reporting */
    // CANCoderFaults faults = new CANCoderFaults();
    // canCoder.getFaults(faults);
    // CANCoderStickyFaults stickyFaults = new CANCoderStickyFaults();
    // canCoder.getStickyFaults(stickyFaults);
  }

  private void logValue(double value, String unit, double timeStamp, String name, String tabName) {
    
    Crashboard.toDashboard(name + " Value", value, tabName);
    Crashboard.toDashboard(name + " Unit", unit, tabName);
    Crashboard.toDashboard(name + " TimeStamp", timeStamp, tabName);
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
