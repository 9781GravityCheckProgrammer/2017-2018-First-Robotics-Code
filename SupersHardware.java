package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.hardware.bosch.BNO055IMU; //rev hub thingy
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import java.util.Locale;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import java.lang.*;



public class SupersHardware {


  /* Drive Motors */
    public DcMotor FLMotor, FRMotor, BLMotor, BRMotor;

    /*Other Motors */
    public DcMotor leftClaw, rightClaw, relicArm, flip;

    /* Servos */
    public Servo jewel, jewelY, relicClaw, relicWrist;

    /* Sensors */
    public ColorSensor color;
    public ModernRoboticsI2cRangeSensor ultra;
    //public TouchSensor touch;
    public BNO055IMU imu; //rev accelerometer

    public Orientation angles; //for accelerometer to understand data
   
    /* Local OpMode members. */
    HardwareMap hwMap;
    private ElapsedTime period  = new ElapsedTime();
    
    /* Constructor */
    public SupersHardware() {
        
    }
    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // save reference to HW Map
        hwMap = ahwMap;
        /* Drive Motors */
        FLMotor = hwMap.dcMotor.get("1");
        FRMotor = hwMap.dcMotor.get("0");
        BLMotor = hwMap.dcMotor.get("2");
        BRMotor = hwMap.dcMotor.get("3");

        /* Other Motors */
        leftClaw  = hwMap.dcMotor.get("00");
        rightClaw = hwMap.dcMotor.get("11");
        flip      = hwMap.dcMotor.get("22");
        relicArm  = hwMap.dcMotor.get("33"); 
       

        /* Servos */
        jewel      = hwMap.servo.get("s0");
        jewelY     = hwMap.servo.get("s4");
        relicClaw  = hwMap.servo.get("s2");
        relicWrist = hwMap.servo.get("s3"); 
    
        /* Sensors */
        color = hwMap.colorSensor.get("color");
        ultra = hwMap.get(ModernRoboticsI2cRangeSensor.class, "ultra");

        /* Motor Config */
        FLMotor.setDirection(DcMotor.Direction.REVERSE);
        BLMotor.setDirection(DcMotor.Direction.REVERSE);
        
        BLMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BLMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //flip.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //flip.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //relicArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //relicArm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        
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
    
    String formatAngle(AngleUnit angleUnit, double angle){
        return formatDegrees(AngleUnit.DEGREES.fromUnit(angleUnit, angle));
    }
    
    String formatDegrees(double degrees)
    {
        return String.format(Locale.getDefault(), "%.1f", AngleUnit.DEGREES.normalize(degrees));
    }
    
    String IMUHeading(){
        angles  = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        return formatAngle(angles.angleUnit, angles.firstAngle);
    }
}
