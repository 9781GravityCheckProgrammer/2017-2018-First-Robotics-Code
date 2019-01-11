package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import java.util.Locale;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;

public abstract class RoverRuckusMethodsAlpha extends RoverRuckusHardwareAlpha {
 
    public void moveInches(double power, double distance) throws InterruptedException{
        double pos = brMotor.getCurrentPosition();//now you have the position in some int
        double target = pos + distance * ENCODER_PER_IN;
        
        runMotors(power);
        
        while (brMotor.getCurrentPosition() < target){//while the position is less than the target, do nothing
            
        }

        stopMotors();
    }

    public void stopMotors(){
        flMotor.setPower(0);
        frMotor.setPower(0);
        blMotor.setPower(0);
        brMotor.setPower(0);
    }
    
    public void runMotors(double power) {
        flMotor.setPower(power);
        frMotor.setPower(power);
        blMotor.setPower(power);
        brMotor.setPower(power);
    }
 public void moveBackwardsTime(double power, int milli) throws InterruptedException{
        flMotor.setPower(power);
        frMotor.setPower(power);
        blMotor.setPower(power);
        brMotor.setPower(power);

        Thread.sleep(milli);

        stopMotors();
    }
    public void moveBack(double power, int milli) throws InterruptedException{
       flMotor.setPower(power);
        frMotor.setPower(power);
        blMotor.setPower(power);
        brMotor.setPower(power);

        Thread.sleep(milli);

        stopMotors();  
    }
    public void moveForwardTime(double power, int milli) throws InterruptedException{
        flMotor.setPower(power);
        frMotor.setPower(power);
        blMotor.setPower(power);
        brMotor.setPower(power);

        Thread.sleep(milli);

        stopMotors();
    }

    public void turnRightTime(double power, int milli) throws InterruptedException {
        flMotor.setPower(-power);
        frMotor.setPower(power);
        blMotor.setPower(-power);
        brMotor.setPower(power);

        Thread.sleep(milli);

        stopMotors();
    }
       public void turnLeftTime(double power, int milli) throws InterruptedException {
        flMotor.setPower(power);
        frMotor.setPower(-power);
        blMotor.setPower(power);
        brMotor.setPower(-power);

        Thread.sleep(milli);

        stopMotors();
    }
  public void armEncoLift() throws InterruptedException {
      
      int intPos = armLiftMotor.getCurrentPosition(); //This is the value of the postition
      armLiftMotor.setPower(-0.8);
      while (armLiftMotor.getCurrentPosition() > intPos - 22000){
          telemetry.addData("enco", armLiftMotor.getCurrentPosition());
          telemetry.update();
      }
     armLiftMotor.setPower(0);
  }
  public void moveEncoder(double power, double in, LinearOpMode linOp) {
      int distance = (int)(ENCODER_PER_IN * in);
      int prevPos = brMotor.getCurrentPosition();
      while (linOp.opModeIsActive() && (brMotor.getCurrentPosition() < prevPos + distance)){
        flMotor.setPower(-power);
        brMotor.setPower(-power);
        frMotor.setPower(-power);
        blMotor.setPower(-power);   
        linOp.telemetry.addData("Encoder", brMotor.getCurrentPosition());
        linOp.telemetry.update();
          
      }
  stopMotors();
  }
  
   public void moveBackEncoder(double power, double in, LinearOpMode linOp) {
      int distance = (int)(ENCODER_PER_IN * in);
      int prevPos = brMotor.getCurrentPosition();
      while (linOp.opModeIsActive() && (brMotor.getCurrentPosition() > prevPos - distance)){ //changed signs to opposite
        flMotor.setPower(power);
        brMotor.setPower(power);
        frMotor.setPower(power);
        blMotor.setPower(power);   
        linOp.telemetry.addData("Encoder",brMotor.getCurrentPosition());
        linOp.telemetry.update();
          
      }
  stopMotors();
  }
 
  public void encoStrafe(double power, double in, LinearOpMode linOp){
        int distance = (int)(ENCODER_PER_IN * in);
        int prevPos = brMotor.getCurrentPosition();
        while (linOp.opModeIsActive() && (brMotor.getCurrentPosition() < prevPos + distance)){ //changed signs to opposite
            flMotor.setPower(power);
            brMotor.setPower(power);
            frMotor.setPower(-power);
            blMotor.setPower(-power);
        }
        stopMotors();
    }
     public void encoStrafeNeg(double power, double in, LinearOpMode linOp){
        int distance = (int)(ENCODER_PER_IN * in);
        int prevPos = brMotor.getCurrentPosition();
        while (linOp.opModeIsActive() && (brMotor.getCurrentPosition() > prevPos - distance)){ //changed signs to opposite
            flMotor.setPower(-power);
            brMotor.setPower(-power);
            frMotor.setPower(power);
            blMotor.setPower(power);
        }
        stopMotors();
    }
        // maybe needs cange. will see after tests
    public void rotateToZero (double power, LinearOpMode linOp) {
        if (Double.parseDouble(IMUHeading()) < 3 && Double.parseDouble(IMUHeading()) > -3){
        }else if (Double.parseDouble(IMUHeading()) < 0) {
            while (linOp.opModeIsActive() && Double.parseDouble(IMUHeading()) < 0) {
                flMotor.setPower(-power);
                frMotor.setPower(power);
                blMotor.setPower(-power);
                brMotor.setPower(power);
            }
        }
    }
    public void turnImu (double power, double angle, LinearOpMode linOp) {
        if (Double.parseDouble(IMUHeading()) < 2 && Double.parseDouble(IMUHeading()) > -2){
        }else if (Double.parseDouble(IMUHeading()) < angle) {
            while (linOp.opModeIsActive() && Double.parseDouble(IMUHeading()) < angle) {
                flMotor.setPower(-power);
                frMotor.setPower(power);
                blMotor.setPower(-power);
                brMotor.setPower(power);
            }
        }
        else {
            while (linOp.opModeIsActive() && Double.parseDouble(IMUHeading()) > 0) {
                flMotor.setPower(power);
                frMotor.setPower(-power);
                blMotor.setPower(power);
                brMotor.setPower(-power);
            }
        }
        try{
        reverseRotLockBOIIIIIIIII();
        }
        catch(Exception e){
            
        }
    }
    public void reverseRotLockBOIIIIIIIII() throws Exception {
        if (Double.parseDouble(IMUHeading()) < 2 && Double.parseDouble(IMUHeading()) > -2){
        }else if (Double.parseDouble(IMUHeading()) < 0) {
                flMotor.setPower(.1);
                frMotor.setPower(-0.1);
                blMotor.setPower(.1);
                brMotor.setPower(-0.1);
                Thread.sleep(10);
                flMotor.setPower(0);
                frMotor.setPower(0);
                blMotor.setPower(0);
                brMotor.setPower(0);
        }
        else {
                flMotor.setPower(-0.1);
                frMotor.setPower(1);
                blMotor.setPower(-0.1);
                brMotor.setPower(1);
                Thread.sleep(100000);
                flMotor.setPower(0);
                frMotor.setPower(0);
                blMotor.setPower(0);
                brMotor.setPower(0);
        }
    }

    

   
    /*  public void moveEncoder(double power, double in, LinearOpMode linOp) {
          int distance = (int)(ENCODER_PER_IN * in);
        int prevPos = brMotor.getCurrentPosition();
      if (in < 0) {
          
       while (linOp.opModeIsActive() && (brMotor.getCurrentPosition() > prevPos + distance)){
            flMotor.setPower(-power);
            brMotor.setPower(-power);
            frMotor.setPower(-power);
            blMotor.setPower(-power);
       linOp.telemetry.addData("Encoder",brMotor.getCurrentPosition());
       linOp.telemetry.update();
       }
      
      }
      else {
          
      
        while (linOp.opModeIsActive() && (brMotor.getCurrentPosition() < prevPos + distance)){
            flMotor.setPower(power);
            brMotor.setPower(power);
            frMotor.setPower(power);
            blMotor.setPower(power);
        }
          
      }
       stopMotors();
      }
      
      */
      
   /* public void rotateRight(double power) {
        
        flMotor.setPower(power);
        frMotor.setPower(-power);
        blMotor.setPower(power);
        brMotor.setPower(-power);

    }
    */
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
