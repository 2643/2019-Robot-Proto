/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSink;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
  // For example to map the left and right motors, you could define the
  // following variables to use with your drivetrain subsystem.
  // public static int leftMotor = 1;
  // public static int rightMotor = 2;
    
  // If you are using multiple modules, make sure to define both the port
  // number and the module. For example you with a rangefinder:
  // public static int rangefinderPort = 1;
  // public static int rangefinderModule = 1;

  // Everything is public because vscode likes to complain a lot about it.

  /* Ports */
  //drive ports
  public static int lFrontMotorPort = 29;   
  public static int lBackMotorPort = 21;    
  public static int rFrontMotorPort = 26;   
  public static int rBackMotorPort = 38;    
  public static int rEncoderPort1 = 0;     //TODO Check to make sure that forward makes the output increase
  public static int rEncoderPort2 = 1;     //TODO Check to make sure that forwards makes the output increase
  public static int lEncoderPort1 = 2;     //TODO Check to make sure that forwards makes the output increase
  public static int lEncoderPort2 = 3;     //TODO Check to make sure that forwards maeks the output increase

  //elevator ports
  public static int elevatorPort = 20; 
  public static int elevatorBottomLimitPort = 18; //TODO Elevator Limit Switch : unplugged, need to find new port  

  //solenoid ports
  public static int hatchMechanismSolenoidPort1 = 1;     //TODO Check the first port of the solenoid on the PCM
  public static int hatchMechanismSolenoidPort2 = 2;     //TODO Check the second port of the solenoid on the PCM
  public static int releaseHatchSolenoidPort1 = 3;     //TODO Check the first port of the solenoid on the PCM
  public static int releaseHatchSolenoidPort2 = 4;     //TODO Check the second port of the solenoid on the PCM

  // IR sensor ports
  public static int irLeftPort1 = 1;  //TODO Check if these are correct
  public static int irLeftPort2 = 2;  //TODO Check if these are correct
  public static int irLeftPort3 = 3;  //TODO Check if these are correct
  public static int irRightPort1 = 5; //TODO Check if these are correct
  public static int irRightPort2 = 6; //TODO Check if these are correct
  public static int irRightPort3 = 7; //TODO Check if these are correct

  //cargo ports
  public static int cargoIntakePort1 = 27; 
  public static int cargoIntakePort2 = 23; 
  public static int cargoRetractPort = 22; 
  public static int cargoOuttakePort = 20; 

  //Ultrasonic ports
  public static int ultrasonicLeftOneTrigger = 16; //TODO Ultrasonics: were unplugged, make sure these ports are correct
  public static int ultrasonicLeftOneEcho = 15; //TODO Ultrasonics: were unplugged, make sure these ports are correct
  public static int ultrasonicLeftTwoTrigger = 19; //TODO Ultrasonics: were unplugged, make sure these ports are correct
  public static int ultrasonicLeftTwoEcho = 17; //TODO Ultrasonics: were unplugged, make sure these ports are correct
  public static int ultrasonicRightOneTrigger = 13; //TODO Ultrasonics: were unplugged, make sure these ports are correct
  public static int ultrasonicRightOneEcho = 12; //TODO Ultrasonics: were unplugged, make sure these ports are correct
  public static int ultrasonicRightTwoTrigger = 10; //TODO Ultrasonics: were unplugged, make sure these ports are correct
  public static int ultrasonicRightTwoEcho = 14; //TODO Ultrasonics: were unplugged, make sure these ports are correct

  //DriveTrainPIDTarget
  public static double RightEncoderTarget = 0;
  public static double LeftEncoderTarget = 0;
  

  /* Motors */
  //Drive motors
  public static WPI_TalonSRX LeftFrontMotor = new WPI_TalonSRX(lFrontMotorPort);
  public static WPI_TalonSRX LeftBackMotor = new WPI_TalonSRX(lBackMotorPort);
  public static WPI_TalonSRX RightFrontMotor = new WPI_TalonSRX(rFrontMotorPort);
  public static WPI_TalonSRX RightBackMotor = new WPI_TalonSRX(rBackMotorPort);

  //elevator motors
  public static CANSparkMax elevatorMotor = new CANSparkMax(elevatorPort, MotorType.kBrushless);

  //hatch solenoids
  public static DoubleSolenoid hatchMechanismSolenoid = new DoubleSolenoid(hatchMechanismSolenoidPort1, hatchMechanismSolenoidPort2);
  public static DoubleSolenoid releaseHatchSolenoid = new DoubleSolenoid(releaseHatchSolenoidPort1, releaseHatchSolenoidPort2);
 
  //cargo intake 
  public static WPI_TalonSRX cargoIntakeMotor1 = new WPI_TalonSRX(cargoIntakePort1);
  public static WPI_TalonSRX cargoIntakeMotor2 = new WPI_TalonSRX(cargoIntakePort2);
  public static WPI_TalonSRX cargoRetractMotor = new WPI_TalonSRX(cargoRetractPort);
  
  //cargo outtake
  public static WPI_TalonSRX cargoOuttakeMotor = new WPI_TalonSRX(cargoOuttakePort);

  //cargo sensors

  /* Sensors */
  //drive sensors
  public static Encoder RightEncoder = new Encoder(rEncoderPort1, rEncoderPort2);
  public static Encoder LeftEncoder = new Encoder(lEncoderPort1, lEncoderPort2);
  public static UsbCamera leftCamera;
  public static UsbCamera rightCamera;
  public static VideoSink server;
  
  //Ultrasonic Initiation
  public static Ultrasonic ultrasonicLeftOne = new Ultrasonic(ultrasonicLeftOneTrigger, ultrasonicLeftOneEcho);
  public static Ultrasonic ultrasonicLeftTwo = new Ultrasonic(ultrasonicLeftTwoTrigger, ultrasonicLeftTwoEcho);
  public static Ultrasonic ultrasonicRightOne = new Ultrasonic(ultrasonicRightOneTrigger, ultrasonicRightOneEcho);
  public static Ultrasonic ultrasonicRightTwo = new Ultrasonic(ultrasonicRightTwoTrigger, ultrasonicRightTwoEcho);

  //elevator sensors
  public static DigitalInput elevatorBottomLimit = new DigitalInput(elevatorBottomLimitPort);
  public static CANEncoder elevatorEncoder = new CANEncoder(elevatorMotor);
  public static CANPIDController elevatorController = new CANPIDController(elevatorMotor);

  //IR sensors
  public static DigitalInput irLeft1 = new DigitalInput(irLeftPort1);
  public static DigitalInput irLeft2 = new DigitalInput(irLeftPort2);
  public static DigitalInput irLeft3 = new DigitalInput(irLeftPort3);
  public static DigitalInput irRight1 = new DigitalInput(irRightPort1);
  public static DigitalInput irRight2 = new DigitalInput(irRightPort2);
  public static DigitalInput irRight3 = new DigitalInput(irRightPort3);

  //gyroscope 
  //TODO ProbablyanAsian: change to PigeonIMU

  /* Variables */
  //drive variables
  public static int rightDriverAxis = 5; 
  public static int leftDriverAxis = 1; 
  public static double MotorCurrent = 0; //Use to work out if it is up agaisnt a wall.
  public static double MotorCurrentSum = 0;
  public static double multiplier = 0.7;

  //elevator variables
  public static double elevatorSpeed = -0.7;
  public static int elevatorEncoderMaxLimit = 100; //This is the maximum encoder ticks allowed from the bottom upwards. //TODO check this upper limit on the real robot 
  public static int elevatorTolerance; //TODO test the tolerance of the elevator
  
  //hatch variables
  public static int hatchReleaseTimeout = 2; //TODO Change this

  //cargo variables
  public static double cargoIntakeSpeed = 1; 
  public static double cargoReleaseSpeed = 0.6; 
  public static double cargoRetractSpeed = -0.6; 
  public static double cargoOuttakeLeftSpeed = -1; 
  public static double cargoOuttakeRightSpeed = 1;

  //Line targets
  public static int encoderErrorTolerance = 4; //in encoder ticks //TODO change this.
  public static int ultrasonicErrorTolerance = 33; // in millimeters. //TODO change this.

  public static int halfIRDistance = 3; //in ticks, from the middle between the sensors. //TODO change this. //TODO William: check encoder Ratios.
  public static int IRDistance = 7; // in ticks, distance between 2 sensors //TODO, check to make sure.
  public static int oneInchEncoder = 4; 

  //DO NOT CHANGE WHTHOUT ASKING 
  public static int[] lastLeftOne = new int[2];
  public static int[] lastLeftThree = new int[2];

  public static int[] lastRightOne = new int[2];
  public static int[] lastRightThree = new int[2];

  public static int maxReliableEncoder = 20; //currently around 5 inches //maximum distance we can trust the last known line encoder position to stay valid.
  public static int maxUltrasonicDist = 457; //Defined in MMs, currently around 13 inches
  //No touchy either I(CargoAutoCode) need these 
  public static enum IRState {
    IDLE, TRUE, WAIT;
  }

  public static IRState curIRStateLeftOne;
  public static int counterLeftOne = 0;
  public static IRState curIRStateLeftTwo;
  public static int counterLeftTwo = 0;
  public static IRState curIRStateLeftThree;
  public static int counterLeftThree = 0;
  public static IRState curIRStateRightOne;
  public static int counterRightOne = 0;
  public static IRState curIRStateRightTwo;
  public static int counterRightTwo = 0;
  public static IRState curIRStateRightThree;
  public static int counterRightThree = 0;


  //driver joystick button numbers

  public static int retractCargoIntakeButtonNumber = 2;
  public static int releaseCargoIntakeButtonNumber = 3;
  public static int switchToLeftCameraButtonNumber = 0; //TODO check button number; this will be the buttons near the trigger
  public static int switchToRightCameraButtonNumber = 0; //TODO check button number; this will be the buttons near the trigger

  //operator board button numbers

  public static int cancelAutoSafetyButtonNumber = 1; 
  public static int elevatorDownButtonNumber = 5; 
  public static int cargoOuttakeLeftButtonNumber = 9; 
  public static int cargoOuttakeRightButtonNumber = 10; 
  public static int elevatorPresetButtonNumber = 6; 
  public static int elevatorUpButtonNumber = 4; 
  public static int intakeButtonNumber = 14; 
  public static int hatchReleaseButtonNumber = 7; 
  public static int hatchMechanismSwitchNumber = 12; 
  public static int hatchAutoButtonNumber = 2; 
  public static int cargoOuttakeAutoButtonNumber = 3; 

  //Networktables for vision
  public static NetworkTable visionTable = NetworkTableInstance.getDefault().getTable("vision");

  //operator board values for the six position switch 
  //DO NOT MESS WITH IT!

  public static final int[] rocketLevel = {0 /* TODO find encoder tick values*/, 0};
  public static final double[] rocketHatchLevels = {-1.0, -0.7, -0.2, 0.15, 0.4, 0.6, 0.85, 1.0};

  public static double[] nothingSelected = {0.86, 1.0};
}
