/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;

import frc.robot.RobotMap;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Add your docs here.
 */
public class Elevator extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  private CANSparkMax elevatorMotor;
  public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput;
  public static boolean CalibrateSparkMax = false;
  public static int CalibrateNumber;
  public static boolean atBottom = false; 

  /**
   * Setting Motors
   * @param liftMotor elevator motor
   */
  public Elevator(CANSparkMax liftMotor) {
    elevatorMotor = liftMotor;

    elevatorMotor.getPIDController().setP(kP);
    elevatorMotor.getPIDController().setI(kI);
    elevatorMotor.getPIDController().setD(kD);
    // PID coefficients
    kP = 0.011; 
    kI = 0;
    kD = 0; 
    kIz = 0; 
    kFF = 0; 
    kMaxOutput = .5; 
    kMinOutput = -.5;
    
    // set PID coefficients
    RobotMap.elevatorController.setP(kP);
    RobotMap.elevatorController.setI(kI);
    RobotMap.elevatorController.setD(kD);
    RobotMap.elevatorController.setIZone(kIz);
    RobotMap.elevatorController.setFF(kFF);
    RobotMap.elevatorController.setOutputRange(kMinOutput, kMaxOutput);

  }

  /**
  * Set the elevator's speed
  * @param speed from -1 to 1
  */
  public void setElevatorSpeed(double speed) {
    //elevatorMotor.getPIDController().setReference(speed, ControlType.kDutyCycle);

    elevatorMotor.set(speed);
    //System.out.println("Ele Speed: " + speed);
  }

  /**
   * Returns the position of the encoder
   * @return double elevator encoder position 
   */
  public double getElevatorEncoder() {
    return(elevatorMotor.getEncoder().getPosition());
  }

  /**
   * Resets the encoder by subtracting the current value as an offset, but only when the limit switch is hit 
   */
  public void resetElevatorEncoder(){
  //   if(RobotMap.elevatorBottomLimit.get() && !atBottom && !CalibrateSparkMax){
  //     //This reduces the amount of times that this piece of code runs
  //    if(CalibrateNumber % 10 == 0){
  //      RobotMap.rotations = RobotMap.elevatorMotor.getEncoder().getPosition() - 1;
  //      RobotMap.elevatorMotor.getPIDController().setReference(RobotMap.rotations, ControlType.kPosition);
  //    }
  //  }
  //  else if(!RobotMap.elevatorBottomLimit.get() && !CalibrateSparkMax){
  //    atBottom = true;
  //  }
  //  else if(!CalibrateSparkMax){
  //      CalibrateNumber ++;
  //      if(RobotMap.elevatorBottomLimit.get()){
  //        if(CalibrateNumber % 25 == 0){
  //          RobotMap.rotations = (RobotMap.elevatorMotor.getEncoder().getPosition() - 1);
  //          RobotMap.elevatorMotor.getPIDController().setReference(RobotMap.rotations, ControlType.kPosition);
  //          //System.out.println("Moving up");
  //        }
  //      }else if(!RobotMap.elevatorBottomLimit.get()){
  //          RobotMap.rotations = 0;
  //          RobotMap.elevatorMotor.getEncoder().setPosition(0);
  //          CalibrateSparkMax = true;
  //          //System.out.println("Zeroed");
  //      }
  //    }else{
  //     Scheduler.getInstance().run();
      //System.out.println(RobotMap.elevatorMotor.getEncoder().getPosition());
    // }
    if(getElevatorBottomLimitSwitch()) {
      elevatorMotor.getEncoder().setPosition(0);
      System.out.println("resetEnc");
    }
  }

  public void stopElevatorPID(){
    elevatorMotor.getPIDController().setReference(0, ControlType.kDutyCycle);
  }

  public double getElevatorCurrent(){
    return elevatorMotor.getOutputCurrent();
  }
  /** 
   * Gets the value of the bottom limit switch
   * @return boolean
  */
  public boolean getElevatorBottomLimitSwitch(){
    return(RobotMap.elevatorBottomLimit.get());
  }

  /**
   * Gets the value of the upper limit switch 
   * @return boolean
   */
  public boolean getElevatorUpperLimitSwitch(){
    return (RobotMap.elevatorUpperLimit.get());
  }
  
  /**
   * Sets the elevator position 
   * @param position in rotations
   */
  public void setElevatorPosition(double rotation){
     // read PID coefficients from SmartDashboard
     double p = 0.018; //0.018
     double i = 0.00; //0.00 
     double d = 0.04; //0.05
     double iz = 0.0;
     double ff = 0.0;
     double max = 0.9;
     double min = -0.9;
     double rotations = rotation;
 
     // if PID coefficients on SmartDashboard have changed, write new values to controller
     if((p != kP)) { elevatorMotor.getPIDController().setP(p); kP = p; }
     if((i != kI)) { elevatorMotor.getPIDController().setI(i); kI = i; }
     if((d != kD)) { elevatorMotor.getPIDController().setD(d); kD = d; }
     if((iz != kIz)) { elevatorMotor.getPIDController().setIZone(iz); kIz = iz; }
     if((ff != kFF)) { elevatorMotor.getPIDController().setFF(ff); kFF = ff; }
     if((max != kMaxOutput) || (min != kMinOutput)) { 
      elevatorMotor.getPIDController().setOutputRange(min, max); 
       kMinOutput = min; kMaxOutput = max; 
     }
    
     /**
      * PIDController objects are commanded to a set point using the 
      * SetReference() method.
      * 
      * 
      * The first parameter is the value of the set point, whose units vary
      * depending on the control type set in the second parameter.
      * 
      * The second parameter is the control type can be set to one of four 
      * parameters:
      *  com.revrobotics.ControlType.kDutyCycle
      *  com.revrobotics.ControlType.kPosition
      *  com.revrobotics.ControlType.kVelocity
      *  com.revrobotics.ControlType.kVoltage
      */
      elevatorMotor.getPIDController().setReference(rotations, ControlType.kPosition);
     

  }
  
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
