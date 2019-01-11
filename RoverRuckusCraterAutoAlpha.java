package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import java.lang.reflect.Method;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection;
import java.util.List;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
@Autonomous
public class RoverRuckusCraterAutoAlpha extends RoverRuckusMethodsAlpha {
    private static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";
    private static final String LABEL_GOLD_MINERAL = "Gold Mineral";
    private static final String LABEL_SILVER_MINERAL = "Silver Mineral";
    private static final String VUFORIA_KEY = "AUJRTEb/////AAABmT6+kq7WxUHBgzxGyI1cFeAo571iwswGIOpbMuz7F+w1Zb6lS4OpP+zDW11xFv/nOmvFML2QKCfQGaIbma97rufCKTI9K8XjEPiScqGc8a+NM6d6xKMil2EcuctbhgK7xBo+gX29t+4V48lYD2V+vD0an1+vXxEfmssobKWZN6dIaIQEMMuSstIaryGLJjQOmOC6U/+MdJca2j7slh8R9SMJHaiZ1nd7LnVJRItRgK73YUkIKOpnhS6jwC1dNwd5qwSdwLw4Ui0SgL3W/xSqL91dCRkkvn16XPQIiDzRfBytuR8C17A6jtj3yBQWR8c2c0ZVaslbm9ogv2KFsQeRzKFf4SZdsf0zXJE7NsvnSead";
    private VuforiaLocalizer vuforia;
    private TFObjectDetector tfod;
    
    RoverRuckusHardware robot = new RoverRuckusHardware();
    
    /**
     * Initialize the Vuforia localization engine.
     */
    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = CameraDirection.BACK;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Loading trackables is not necessary for the Tensor Flow Object Detection engine.
    }

    /**
     * Initialize the Tensor Flow Object Detection engine.
     */
    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
            "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_GOLD_MINERAL, LABEL_SILVER_MINERAL);
    }
     private String tfodResult(){
          if (opModeIsActive()) {
                /** Activate Tensor Flow Object Detection. */
                if (tfod != null) {
                    tfod.activate();
                }
                   ElapsedTime theTime = new ElapsedTime();
                   
               while (theTime.time() < 1.5) {
                   
               
                    if (tfod != null) {
                        // getUpdatedRecognitions() will return null if no new information is available since
                        // the last time that call was made.
                        List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                        if (updatedRecognitions != null) {
                          telemetry.addData("# Object Detected", updatedRecognitions.size());
                          if (updatedRecognitions.size() == 1 || updatedRecognitions.size() == 2) { //changed to 2 instead of 3
                            int goldMineralX = -1;
                            int silverMineral1X = -1;
                            int silverMineral2X = -1;
                            String Left;
                           // int goldPosition = 0;
                            for (Recognition recognition : updatedRecognitions) {
                              if (recognition.getLabel().equals(LABEL_GOLD_MINERAL)) {
                                goldMineralX = (int) recognition.getLeft();
                              } 
                            }
                           // if ((goldMineralX != -1 && silverMineral1X != -1 )|| (silverMineral2X != -1 && silverMineral1X != -1) || (goldMineralX != -1)) {
                              if (goldMineralX < 700 && goldMineralX > 0) {
                                 return "CENTER";
                              } else if (goldMineralX > 700) {
                                 return "RIGHT";
                              }
                              else if (goldMineralX == -1) {
                                 return "LEFT";
                             
                             } 
                            }
                          }
                    }
            }
          }
   return "Yikes";
    }


    public void runOpMode() throws InterruptedException{
        robot.init(hardwareMap);
        
        
        initVuforia();

        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            initTfod();
        } else {
            telemetry.addData("Sorry!", "This device is not compatible with TFOD");
        }
        
        waitForStart();
       
        telemetry.addData("Correct?", tfodResult());
        telemetry.update();
        
    /* Useful Values */
    int ticksPerRev = 560;
    double inPerRev = 6 * 3.14159; //6 for diamater 
    double ticksPerIn = ticksPerRev / inPerRev;
     
     //Unlatch  
   /* 
    //robot.servoStop.setPosition(0.32);
    sleep(100);
    robot.armLiftMotor.setPower(0.75);
    sleep(500);
    robot.armLiftMotor.setPower(0);
    sleep(100);
    robot.encoStrafeNeg(0.5, 5, this);
    sleep(100);
    robot.armLiftMotor.setPower(-0.75);
    sleep(500);
    robot.armLiftMotor.setPower(0);
    robot.encoStrafe(0.5, 5, this);
    sleep(1000);
    */
   if (tfodResult().equals("CENTER")) {
  sleep(1000);
  robot.moveEncoder(0.3, 25, this);
  sleep(500);
  robot.moveBackEncoder(0.5, 5, this);
  sleep(500);
  robot.encoStrafe(0.5, 50, this);
  sleep(500);
  robot.turnLeftTime(0.5, 1030);
  sleep(500);
  robot.encoStrafeNeg(0.5, 26, this);
  sleep(500);
  robot.moveEncoder(0.5, 40, this);
  sleep(500);
  robot.wardServo.setPosition(0.4);
  sleep(2000);
  robot.wardServo.setPosition(1);
  sleep(2000);
  robot.moveBackEncoder(1, 70, this);
   }
  if (tfodResult().equals("Yikes")) {
  sleep(1000);
  robot.moveEncoder(0.3, 25, this);
  sleep(500);
  robot.moveBackEncoder(0.5, 5, this);
  sleep(500);
  robot.encoStrafe(0.5, 50, this);
  sleep(500);
  robot.turnLeftTime(0.5, 1030);
  sleep(500);
  robot.encoStrafeNeg(0.5, 26, this);
  sleep(500);
  robot.moveEncoder(0.5, 40, this);
  sleep(500);
  robot.wardServo.setPosition(0.4);
  sleep(2000);
  robot.wardServo.setPosition(1);
  sleep(2000);
  robot.moveBackEncoder(1, 70, this);
  }
   
/*       while (opModeIsActive()){
       
         

        telemetry.addData("red", robot.color.red()); 
        telemetry.addData("blue", robot.color.blue()); 
        telemetry.addData("light", robot.color.alpha()); 
        telemetry.addData("green", robot.color.green()); 
        telemetry.update();
           
       }
      */
    }
}
