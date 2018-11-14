package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous

public class SupersBlueTop extends LinearOpMode {
    /* Useful Values */
    int ticksPerRev = 560;
    final int TICKS_PER_REV = 560;
    double inPerRev = 4 * Math.PI;
    double ticksPerIn = ticksPerRev / inPerRev;
    
    /* Set up */
    SupersHardware robot = new SupersHardware();
    
    VuforiaLocalizer vuforia;
    
    @Override
    public void runOpMode(){
        robot.init(hardwareMap);
        
        /* Initialize Vuforia */
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        /* Vuforia Lisense */
        parameters.vuforiaLicenseKey = "Ack2CI//////AAAAGYmDQnL97kSYrMjJSAfwPykewQQ7WdpNQFeg0RsehBsCo4FgfhnnWDGPZNIorjEYxhgbxALgBMAz0S+S/+CxciHFNmiLHbheUsFcaEJBqiQU9JeAnNw65hiARvZyHo99I+TZBfp3/0XvKbnexYwXUaNAigKieu8ZrR3dwhD4ZazlZ8g7xEh9PiaJc4I048Y1rQrS3gjnhR12ft14j6KKJPqg/m1ngBg+5KGOgDr1NgoAft+FifAOWHZMYw23USKEdfXVOPdo1JS7zmoGJ9iGCJ6I5Gs1xs0Z/CyfquLUpFlPdukse6HARLU66k0EeYnXKa1PS/P4TC21RM7nEvfQPpWjFQ+GoOqPn6Y6pd6272wV";

        /* Camera Direction */
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;

        /* Create Vuforia Localizer with our set parameters */
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);

        VuforiaTrackables ciferKeyTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable ciferTemplate = ciferKeyTrackables.get(0); /* it seems as though  all three pictures are stored in this one location. */
        
        telemetry.addData("Doremy", "Ready to GO");
        telemetry.update();
        
        waitForStart();//------------------------------------------------------------------------------
        
        //encoDriveNeg(.3,10);
        //runAllMotors(.3,10);
        
        /*encoStrafe(.5,10);
        strafe(-.5,10);
        stop();*/
        
        ciferKeyTrackables.activate();

        ElapsedTime runtime = new ElapsedTime();

        RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(ciferTemplate);

        while (opModeIsActive() && (runtime.time() < 1)) {
            vuMark = RelicRecoveryVuMark.from(ciferTemplate);
            if (vuMark != RelicRecoveryVuMark.UNKNOWN) {
                break;
            }
        }
        telemetry.addData("Column", vuMark);
        telemetry.update();
        
        /* Set Jewel Arm Into Position */
        robot.jewel.setPosition(.36);
        robot.jewelY.setPosition(.62);
        
        robot.relicWrist.setPosition(.8);

        //stop();
        /* Decide What Color Is Seen */
        double colorSenseTime = runtime.time();

        int colorConfirm = 0;

        while ((runtime.time() < colorSenseTime + 1) && opModeIsActive()) {
            if (robot.color.blue() > robot.color.red() + 1) {//number added to color is a buffer, can be adjusted.
                colorConfirm = 1;//"blue";
            } else if ((robot.color.red() > robot.color.blue() + 1)) {
                colorConfirm = 2;//"red";
            }
        }

        /* Knock off the colored Jewel */
        if (colorConfirm == 1){
            robot.jewel.setPosition(.25); //was .25
        }else if (colorConfirm == 2){
            robot.jewel.setPosition(.5);
        }else{
            robot.jewelY.setPosition(.04);
        }
        sleep(700);

        robot.jewel.setPosition(.36);
        robot.jewelY.setPosition(0);
        sleep(500);
        
        /* Back Off Platform */
        encoDrive(.3,32);// 3/9 was 32
        runAllMotors(-.5,10);
        sleep(500);
        
        rotateToZero(.3);
        /* Strafe to First Column */
        double strafeTime = runtime.time();
        if (robot.ultra.cmUltrasonic() == 0) {//In case ultra is NOT working
            strafe(.5, 500);
            telemetry.addData("Ed", "DEFAULT TO TIME");
            telemetry.update();
        } else {
            while (opModeIsActive() && robot.ultra.cmUltrasonic() < 47 && strafeTime + 4 > runtime.time()) {//was 46cm 2/15 then 45
                robot.FLMotor.setPower(.5);
                robot.BRMotor.setPower(.5);
                robot.FRMotor.setPower(-.5);
                robot.BLMotor.setPower(-.5);

                /*telemetry.addData("Ultra", robot.ultra.cmUltrasonic());
                telemetry.update();*/
            }
        }
        strafe(-1,10);
        
        sleep(500);
        
        if (vuMark == RelicRecoveryVuMark.CENTER){
           encoStrafe(.5, 11.2);//was Iris Units (~1.75 in per) //7.5
           strafe(-.5,10);
        }else if(vuMark == RelicRecoveryVuMark.RIGHT){
            encoStrafe(.5,22.7);//14.75
            strafe(-.5,10);
        }
        stopAllMotors();
        
        robot.relicWrist.setPosition(.67);

        robot.leftClaw.setPower(-1);
        robot.rightClaw.setPower(1);
        
        sleep(1500);
        
        runAllMotors(-.3,400);
        
        sleep(1000);
        
        robot.leftClaw.setPower(0);
        robot.rightClaw.setPower(0);
        
        //stop();//--------
        
        /* Flip back down */
         timeFlip(-.5,500);
        /* Rotate to zero */
        rotateToZero(.3);
        /* Linear alligning */
        if (vuMark == RelicRecoveryVuMark.CENTER){//Times To Strafe From Crypto Box --------------------------------------
            encoStrafe(.5,11.5);        
        }else if(vuMark == RelicRecoveryVuMark.RIGHT){
        }else{
            encoStrafe(.5,22.75);
        }
        /* Suck In */
         robot.leftClaw.setPower(1);
         robot.rightClaw.setPower(-1);
        /* Into the Pit */
        while (Double.parseDouble(robot.IMUHeading()) > -160) {
            robot.FLMotor.setPower(.3);
            robot.FRMotor.setPower(-.3);
            robot.BLMotor.setPower(.3);
            robot.BRMotor.setPower(-.3);
        }
        reverseRotLock(1);
        
        robot.relicWrist.setPosition(.6);

        
        //encoDrive(.4,45);
        double in = 40;//was 46 3/9
        double power = .4;
        
        double time = runtime.time();
        int distance = (int)(ticksPerIn * in);
        int prevPos = robot.BLMotor.getCurrentPosition();
        while (opModeIsActive() && (robot.BLMotor.getCurrentPosition() < prevPos + distance) && runtime.time() < time + 3){
            robot.FLMotor.setPower(power);
            robot.BRMotor.setPower(power);
            robot.FRMotor.setPower(power);
            robot.BLMotor.setPower(power);
        }
        stopAllMotors();
        sleep(500);
        
        /*while (Double.parseDouble(robot.IMUHeading()) > -170){
            robot.FLMotor.setPower(.3);
            robot.FRMotor.setPower(-.3);
            robot.BLMotor.setPower(.3);
            robot.BRMotor.setPower(-.3);
        }
        reverseRotLock(.3);*/
        
        if (Double.parseDouble(robot.IMUHeading()) > -160){
            while (Double.parseDouble(robot.IMUHeading()) > -160){
                robot.FLMotor.setPower(.3);
                robot.FRMotor.setPower(-.3);
                robot.BLMotor.setPower(.3);
                robot.BRMotor.setPower(-.3);
            }
        } else {
            while (Double.parseDouble(robot.IMUHeading()) < -160) {
                robot.FLMotor.setPower(-.3);
                robot.FRMotor.setPower(.3);
                robot.BLMotor.setPower(-.3);
                robot.BRMotor.setPower(.3);
            }
        }
        reverseRotLock(1);
        
        encoDriveNeg(.4, 52);//was 52 3/9
        timeFlip(.5,1000);
        
        runAllMotors(-.3,500);
        sleep(500);
        runAllMotors(.3,500);
        
        runAllMotors(-.3,500);
        sleep(600);
        runAllMotors(.3,200);
        
        stop();
        
        stop();
    }
    /* Auto Methods */
    public void stopAllMotors(){
        robot.FLMotor.setPower(0);
        robot.FRMotor.setPower(0);
        robot.BLMotor.setPower(0);
        robot.BRMotor.setPower(0);
    }
    
    public void runAllMotors(double power, int milli) {
        robot.FLMotor.setPower(power);
        robot.FRMotor.setPower(power);
        robot.BLMotor.setPower(power);
        robot.BRMotor.setPower(power);

        sleep(milli);
        stopAllMotors();
    }
    
    public void strafe(double power, int milli) {
        robot.FLMotor.setPower(power);
        robot.FRMotor.setPower(-power);
        robot.BLMotor.setPower(-power);
        robot.BRMotor.setPower(power);

        sleep(milli);
        stopAllMotors();
    }
    public void rotateToZero (double power) {
        if (Double.parseDouble(robot.IMUHeading()) < 3 && Double.parseDouble(robot.IMUHeading()) > -3){
        }else if (Double.parseDouble(robot.IMUHeading()) < 0) {
            while (opModeIsActive() && Double.parseDouble(robot.IMUHeading()) < 0) {
                robot.FLMotor.setPower(-power);
                robot.FRMotor.setPower(power);
                robot.BLMotor.setPower(-power);
                robot.BRMotor.setPower(power);
            }
        } else {
            while (opModeIsActive() && Double.parseDouble(robot.IMUHeading()) > 0) {
                robot.FLMotor.setPower(power);
                robot.FRMotor.setPower(-power);
                robot.BLMotor.setPower(power);
                robot.BRMotor.setPower(-power);
            }
        }
        reverseRotLock(power);
    }

    public void timeRotatation (double power, int milli){
        robot.FLMotor.setPower(-power);
        robot.FRMotor.setPower(power);
        robot.BLMotor.setPower(-power);
        robot.BRMotor.setPower(power);
        sleep(milli);
        stopAllMotors();
    }
    
    public void encoStrafe(double power, double in){
        int distance = (int)(ticksPerIn * in);
        int prevPos = robot.BLMotor.getCurrentPosition();
        while (opModeIsActive() && (robot.BLMotor.getCurrentPosition() > prevPos - distance)){
            robot.FLMotor.setPower(power);
            robot.BRMotor.setPower(power);
            robot.FRMotor.setPower(-power);
            robot.BLMotor.setPower(-power);
        }
        stopAllMotors();
    }
    
    public void encoStrafeNeg(double power, double in){
        int distance = (int)(ticksPerIn * in);
        int prevPos = robot.BLMotor.getCurrentPosition();
        while (opModeIsActive() && (robot.BLMotor.getCurrentPosition() < prevPos + distance)){
            robot.FLMotor.setPower(-power);
            robot.BRMotor.setPower(-power);
            robot.FRMotor.setPower(power);
            robot.BLMotor.setPower(power);
        }
        stopAllMotors();
    }
    
    public void encoDrive(double power, double in){
        int distance = (int)(ticksPerIn * in);
        int prevPos = robot.BLMotor.getCurrentPosition();
        while (opModeIsActive() && (robot.BLMotor.getCurrentPosition() < prevPos + distance)){
            robot.FLMotor.setPower(power);
            robot.BRMotor.setPower(power);
            robot.FRMotor.setPower(power);
            robot.BLMotor.setPower(power);
        }
        stopAllMotors();
    }
    
    public void encoDriveNeg(double power, double in){
        int distance = (int)(ticksPerIn * in);
        int prevPos = robot.BLMotor.getCurrentPosition();
        while (opModeIsActive() && (robot.BLMotor.getCurrentPosition() > prevPos - distance)){
            robot.FLMotor.setPower(-power);
            robot.BRMotor.setPower(-power);
            robot.FRMotor.setPower(-power);
            robot.BLMotor.setPower(-power);
        }
        stopAllMotors();
    }
    
    public void timeFlip(double power, int milli){
        //double power = .5;
        robot.flip.setPower(-power);
        sleep(milli);
        robot.flip.setPower(0);
    }
    
    public void reverseRotLock(double power){
        if (robot.BLMotor.getPower() > 0){
            robot.FLMotor.setPower(-power);
            robot.BRMotor.setPower(power);
            robot.FRMotor.setPower(power);
            robot.BLMotor.setPower(-power);
            sleep(10);
            
            robot.FLMotor.setPower(0);
            robot.BRMotor.setPower(0);
            robot.FRMotor.setPower(0);
            robot.BLMotor.setPower(0);
        }else{
            robot.FLMotor.setPower(power);
            robot.BRMotor.setPower(-power);
            robot.FRMotor.setPower(-power);
            robot.BLMotor.setPower(power);
            sleep(10);
            
            robot.FLMotor.setPower(0);
            robot.BRMotor.setPower(0);
            robot.FRMotor.setPower(0);
            robot.BLMotor.setPower(0);
        }
    }
}
