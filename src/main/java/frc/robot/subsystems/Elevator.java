/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Add your docs here.
 */
public class Elevator extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  private WPI_TalonSRX Elevator;
  private WPI_TalonSRX ElevatorSlave;

  public Elevator(WPI_TalonSRX liftMotor, WPI_TalonSRX slaveMotor) {
    Elevator = liftMotor;
    ElevatorSlave = slaveMotor;
    ElevatorSlave.set(ControlMode.Follower, Elevator.getDeviceID());
  }

  // Set elevator's speed
  public void setElevatorSpeed(double speed) {
    Elevator.set(speed);
  }

  // Returns the postition of the encoder
  // TODO check whether we need to divide it by 2 or not. Last year had a problem with it.
  public int getEncoder() {
    return Elevator.getSensorCollection().getQuadraturePosition();
  }

  // Resets the encoders to zero, and blocks for 0.01 second(s) for error checking
  public void elevatorEncoderReset() {
    Elevator.getSensorCollection().setQuadraturePosition(0, 10);
  }
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
