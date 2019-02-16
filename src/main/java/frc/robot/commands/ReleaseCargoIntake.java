/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.*;
/**
 * Releases the cargo
 */
public class ReleaseCargoIntake extends Command {
  public ReleaseCargoIntake() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.cargoIntake);
  }

  @Override
  protected void initialize() {
  }

  @Override
  protected void execute() {
    Robot.cargoIntake.setRectractSpeed(RobotMap.cargoReleaseSpeed);
  }

  @Override
  protected boolean isFinished() {
    //Checks to see whether the cargo potentiometer value is the same as the cargo intake value
    if(Robot.cargoIntake.getPotentiometer() == RobotMap.cargoIntakeDown){
      return true;
    } else{
      return false;
    }
  }

  @Override
  protected void end() {
    Robot.cargoIntake.setRectractSpeed(0);
  }

  @Override
  protected void interrupted() {
    end();
  }
}
