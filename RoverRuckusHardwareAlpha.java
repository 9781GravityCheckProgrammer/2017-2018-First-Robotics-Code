package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.ColorSensor;
import java.lang.reflect.Method;
import java.lang.reflect.Method;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import java.util.Locale;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;

public abstract class RoverRuckusHardwareAlpha extends LinearOpMode {
    /* Motors */
    public DcMotor flMotor, frMotor, blMotor, brMotor, armLiftMotor, flipMotor, extentionMotor; //spinMotor
    /* Sensors */
    public ColorSensor color;
    //public ModernRoboticsI2cRangeSensor ultra;
    public BNO055IMU imu;
    public Orientation angles;
    /* Servos */
    public Servo grabServo, wardServo, cockBlocker;
 
    /* Encoder Values */
    public final int ENCODER_REVOLUTION = 1120;//counts per revolution
    public final double WHEEL_DIAM = 6;
    public final double ENCODER_PER_IN = ENCODER_REVOLUTION / (WHEEL_DIAM * Math.PI); //this is an encoder count
    //public ColorSensor color;
    //public UltrasonicSensor ultra;

    HardwareMap hwMap;
    public void init(HardwareMap ahMap){
        hwMap = ahMap;
        
        /* Drive Train */    
        blMotor          = hwMap.dcMotor.get("blMotor");
        brMotor          = hwMap.dcMotor.get("brMotor");
        flMotor          = hwMap.dcMotor.get("flMotor");
        frMotor          = hwMap.dcMotor.get("frMotor");
        /* Other Motors */
        //spinMotor        = hwMap.dcMotor.get("spinMotor");
        armLiftMotor     = hwMap.dcMotor.get("armLiftMotor");
        flipMotor        = hwMap.dcMotor.get("flipMotor");
        extentionMotor   = hwMap.dcMotor.get("extentionMotor");
        /* Sensor */
        //color          = hwMap.colorSensor.get("color");
        //ultra          = hwMap.get(ModernRoboticsI2cRangeSensor.class, "ultra");
        /* Servos */
        grabServo        = hwMap.servo.get("grabServo");
        wardServo        = hwMap.servo.get("wardServo");
        cockBlocker      = hwMap.servo.get("cockBlocker");
        //ultra = (UltrasonicSensor)hwMap.get("ultra");
       
        /* Motor Config */
        blMotor.setDirection(DcMotor.Direction.REVERSE);
        brMotor.setDirection(DcMotor.Direction.FORWARD);
        flMotor.setDirection(DcMotor.Direction.REVERSE);
        frMotor.setDirection(DcMotor.Direction.FORWARD);
        
        brMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        brMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODERS);
        armLiftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armLiftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODERS);
                
        flipMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        flipMotor.setMode(DcMotor.RunMode.RESET_ENCODERS);
        //flipMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        
        //brMotor.setMode(RUN_WITH_ENCODER);
        //straight untill you reach corner certain distance
        //flMotor = (DcMotor)hwMap.get("m2");
        //flMotor = hwMap.get(DcMotor.class, "m2");
        
        /* IMU Gyro Param */
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit            = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit            = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.loggingEnabled       = true;
        parameters.useExternalCrystal   = true;
        parameters.mode                 = BNO055IMU.SensorMode.IMU;
        parameters.loggingTag           = "IMU";
        imu                             = hwMap.get(BNO055IMU.class, "imu");
        
        imu.initialize(parameters);
    }
        /* Short Cuts */
    
}
