/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class CargoLineAuto extends Command {
  public CargoLineAuto() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.drive);
    requires(Robot.lineDetector);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  boolean finished = false;

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    boolean debug = true;
    switch (RobotMap.curCargoAutoState) {

    case IDLE:
      if (debug) {
          System.out.println("cur IDLE state");
        }
      if (Robot.oi.getDriverStick().getRawButton(RobotMap.cargoOuttakeAutoButtonNumber)) {
        RobotMap.curCargoAutoState = RobotMap.cargoAutoState.LINE;
        if (debug) {
          System.out.println("Line State chk1");
        }
      } 
      else {
        RobotMap.curCargoAutoState = RobotMap.cargoAutoState.IDLE;
        if (debug) {
          System.out.println("Idle State chk1");
        }
      }
      break;

    case LINE:
      if (debug) {
        System.out.println("cur LINE state");
      }
      if (!Robot.oi.getDriverStick().getRawButton(RobotMap.cargoOuttakeAutoButtonNumber)) {
        RobotMap.curCargoAutoState = RobotMap.cargoAutoState.IDLE;
        if (debug) {
          System.out.println("Idle State chk2");
        }
        break;
      }

      //Check for sensor2 lit
      if (((Robot.lineDetector.getIRSensors() & Robot.lineDetector.SENSOR_L2) == Robot.lineDetector.SENSOR_L2)
          || ((Robot.lineDetector.getIRSensors() & Robot.lineDetector.SENSOR_R2) == Robot.lineDetector.SENSOR_R2)) {
        RobotMap.curCargoAutoState = RobotMap.cargoAutoState.ANGLE;
        if(debug){
          System.out.println("Angle State chk2, Sensor2 lit");
        }
      }

      // Checks if the previously gotten value is beneath the maximum encoder
      // reliability value.
      else if ((Math.abs(RobotMap.lastLeftOne[0] - Robot.drive.getLeftEncoder()) <= RobotMap.maxReliableEncoder)
        && (Math.abs(RobotMap.lastLeftOne[1] - Robot.drive.getRightEncoder()) <= RobotMap.maxReliableEncoder)) {
        if ((Math.abs(Robot.drive.LeftError) <= RobotMap.encoderErrorTolerance)
        && (Math.abs(Robot.drive.RightError) <= RobotMap.encoderErrorTolerance)) {
          RobotMap.curCargoAutoSide = RobotMap.cargoAutoSide.LEFT;
          Robot.drive.setLeftPosition(RobotMap.lastLeftOne[0] + RobotMap.halfIRDistance); // TODO check these
          Robot.drive.setRightPosition(RobotMap.lastLeftOne[1] + RobotMap.halfIRDistance); // TODO check these
          if (debug) {
            System.out.println("Try LL0");
          }
        }
      }

      else if ((Math.abs(RobotMap.lastRightOne[0] - Robot.drive.getLeftEncoder()) <= RobotMap.maxReliableEncoder)
        && (Math.abs(RobotMap.lastRightOne[1] - Robot.drive.getRightEncoder()) <= RobotMap.maxReliableEncoder)) {
        if ((Math.abs(Robot.drive.LeftError) <= RobotMap.encoderErrorTolerance)
        && (Math.abs(Robot.drive.RightError) <= RobotMap.encoderErrorTolerance)) {
          RobotMap.curCargoAutoSide = RobotMap.cargoAutoSide.RIGHT;
          Robot.drive.setLeftPosition(RobotMap.lastRightOne[0] + RobotMap.halfIRDistance); // TODO check these
          Robot.drive.setRightPosition(RobotMap.lastRightOne[1] + RobotMap.halfIRDistance); // TODO check these
          if (debug) {
            System.out.println("Try LR0");
          }
        }
      }
      /*  
      if ((Math.abs(RobotMap.lastLeftThree[0] - Robot.drive.getLeftEncoder()) <= RobotMap.maxReliableEncoder)
          && (Math.abs(RobotMap.lastLeftThree[1] - Robot.drive.getRightEncoder()) <= RobotMap.maxReliableEncoder)) {
        RobotMap.curCargoAutoSide = RobotMap.cargoAutoSide.LEFT;
        Robot.drive.setLeftPosition(RobotMap.lastLeftThree[0] - RobotMap.halfIRDistance); // TODO check these
        Robot.drive.setRightPosition(RobotMap.lastLeftThree[1] - RobotMap.halfIRDistance); // TODO check these
        if (debug) {
          System.out.println("LEFT set");
        }
      }

      else if ((Math.abs(RobotMap.lastRightThree[0] - Robot.drive.getLeftEncoder()) <= RobotMap.maxReliableEncoder)
          && (Math.abs(RobotMap.lastRightThree[1] - Robot.drive.getRightEncoder()) <= RobotMap.maxReliableEncoder)) {
        RobotMap.curCargoAutoSide = RobotMap.cargoAutoSide.RIGHT;
        Robot.drive.setLeftPosition(RobotMap.lastRightThree[0] - RobotMap.halfIRDistance); // TODO check these
        Robot.drive.setRightPosition(RobotMap.lastRightThree[1] - RobotMap.halfIRDistance); // TODO check these
        if (debug) {
          System.out.println("RIGHT Set");
        }
      }
      */

      else if (Robot.lineDetector.getIRSensors() == 0) {
        // Robot has driven to be below the PID tolerance.
        if (debug) {
          System.out.println("NoIRDetected");
        }
        if ((Math.abs(Robot.drive.LeftError) <= RobotMap.encoderErrorTolerance)
            && (Math.abs(Robot.drive.RightError) <= RobotMap.encoderErrorTolerance)) {

          // Move the one inch fowards
          Robot.drive.setLeftPosition(Robot.drive.getLeftEncoder() + RobotMap.oneInchEncoder);
          Robot.drive.setRightPosition(Robot.drive.getRightEncoder() + RobotMap.oneInchEncoder);
          if (debug) {
            System.out.println("Slow Movement");
          }
        }
      }
      break;

      case ANGLE:
        RobotMap.curCargoAutoState = RobotMap.cargoAutoState.IDLE;
        Robot.lineDetector.clearLastLines();
        if(debug){
          System.out.println("State set to IDLE");
        }
      break;
    /* case ANGLE:
      if (!Robot.oi.getDriverStick().getRawButton(1)) {
          RobotMap.curCargoAutoState = RobotMap.cargoAutoState.IDLE;
          break;
        }
        if(Robot.lineDetector.getIRSensors() == 0) {
          RobotMap.curCargoAutoState = RobotMap.cargoAutoState.LINE;
          break;
        }
        switch(RobotMap.curCargoAutoSide) {

          case NONE:
            System.err.println("ERROR: NONE sided");
            RobotMap.curCargoAutoState = RobotMap.cargoAutoState.LINE;
            break;

          case LEFT:
            if(Robot.ultrasonicSubsystem.getLeftDist() >= RobotMap.maxUltrasonicDist) {
              System.err.println("ERROR: Too far, get closer");
            }
            
            else {
              if((Robot.ultrasonicSubsystem.getLeftValues()[0] - Robot.ultrasonicSubsystem.getLeftValues()[1]) >= RobotMap.ultrasonicErrorTolerance) {

                int leftDifference = Robot.ultrasonicSubsystem.getLeftValues()[0]-Robot.ultrasonicSubsystem.getLeftValues()[1];
                int distToTicks = leftDifference / -7; //TODO verify my math properly
                //Turns it the proper amount of ticks

                Robot.drive.setLeftPosition(Robot.drive.getLeftEncoder() + distToTicks);
                Robot.drive.setRightPosition(Robot.drive.getRightEncoder() - distToTicks);
              }
              else {
                finished = true;
              }
            }
            break;

          case RIGHT:
            if(Robot.ultrasonicSubsystem.getRightDist() >= RobotMap.maxUltrasonicDist) {
              System.err.println("ERROR: Too far, get closer");
            }
            else {
              if((Robot.ultrasonicSubsystem.getRightValues()[0] - Robot.ultrasonicSubsystem.getRightValues()[1]) >= RobotMap.ultrasonicErrorTolerance) {
                
                int rightDifference = Robot.ultrasonicSubsystem.getRightValues()[0] - Robot.ultrasonicSubsystem.getRightValues()[1];
                int distToTicks = rightDifference / -7; //TODO verify my math properly
                //Turns it the proper amount of ticks

                Robot.drive.setLeftPosition(Robot.drive.getRightEncoder() - distToTicks);
                Robot.drive.setRightPosition(Robot.drive.getRightEncoder() + distToTicks);
              }
              else {
                finished = true;
              }
            }
            break;
        }
        break;*/
    }
}
  
  public void clearStores() {
    RobotMap.curCargoAutoSide = RobotMap.cargoAutoSide.NONE;
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return finished;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    //Robot.drive.setLeftPosition(Robot.drive.getLeftEncoder());
    //Robot.drive.setRightPosition(Robot.drive.getRightEncoder());
    Robot.drive.setAllSpeed(0, 0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end(); 
  }
}
