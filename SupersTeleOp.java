package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.Hardware;

@TeleOp(name = "SupersTeleOp", group = "Testing")
public class SupersTeleOp extends LinearOpMode{

    SupersHardware robot = new SupersHardware();

    double nerf = .6;

    @Override
    public void runOpMode() throws InterruptedException {

        robot.init(hardwareMap);
        telemetry.addData("Status", "Inititialized");
        telemetry.update();
        
        //robot.ultra.close();
        //robot.color.close();
        
        waitForStart();
        
        //robot.relicWrist.scaleRange(0,1);
        robot.relicWrist.setPosition(.7);

        while (opModeIsActive()) {
            robot.jewel.setPosition(.36);
            robot.jewelY.setPosition(0);
           
            /* Relic Controls */
            /* Relic Arm */ 
            if (gamepad1.dpad_down) {
                robot.relicArm.setPower(1);
            } else if (gamepad1.dpad_up) {
                robot.relicArm.setPower(-1);
            } else {
                robot.relicArm.setPower(0);
            }
            /* Relic Wrist */
            if (gamepad1.y) {
                robot.relicWrist.setPosition(0.06);
            } else if (gamepad1.x) {
                robot.relicWrist.setPosition(.65);
            }
            /* Relic Claw */
            if (gamepad1.a) {
                robot.relicClaw.setPosition(0.3);
            } else if (gamepad1.b) {
                robot.relicClaw.setPosition(0.7);
            }
            /* Intake System */
            /* Roller Claw */
            if (gamepad2.left_bumper || gamepad1.left_bumper) {//sucks in
                robot.leftClaw.setPower(.8);
                robot.rightClaw.setPower(-.8);
            } else if (gamepad2.left_trigger > .4 || gamepad1.left_trigger > .4) {//push out
                robot.leftClaw.setPower(-.8);
                robot.rightClaw.setPower(.8);
            } else {
                robot.leftClaw.setPower(0);
                robot.rightClaw.setPower(0);
            }
            /* Dispensor System */
            /* Fliper */
            if (gamepad1.right_trigger > .4){
                robot.flip.setPower(.7);
            }else if(gamepad1.right_bumper){
                robot.flip.setPower(-.7);
            }else{
                robot.flip.setPower(0);
            }
            /*int flipPos = robot.flip.getCurrentPosition();
            if (gamepad1.right_trigger > .4){
                if (flipPos > -400){
                    robot.flip.setPower(.25);
                }else{
                    robot.flip.setPower(0);
                }
            }else{
                if (flipPos < -5){
                    robot.flip.setPower(-.25);
                }else{
                    robot.flip.setPower(0);
                }
            }
             telemetry.addData("flip", flipPos);
            telemetry.update();*/
            //if (gamepad.right_trigger) {
            //    robot.flip.setPower(right_trigger);
            //} else {
            //    robot.flip.setPower(0);
            //}
            
            /* Drive Control */
            
            if(gamepad2.b){
                nerf = .6;
            }else if(gamepad2.a){
                nerf = 1;
            }
            
            robot.FLMotor.setPower(nerf * (-gamepad1.left_stick_y + gamepad1.left_stick_x + gamepad1.right_stick_x));
            robot.FRMotor.setPower(nerf * (-gamepad1.left_stick_y - gamepad1.left_stick_x - gamepad1.right_stick_x));
            robot.BLMotor.setPower(nerf * (-gamepad1.left_stick_y - gamepad1.left_stick_x + gamepad1.right_stick_x));
            robot.BRMotor.setPower(nerf * (-gamepad1.left_stick_y + gamepad1.left_stick_x - gamepad1.right_stick_x));
            
            if(nerf < 1){
                telemetry.addData("Power", "HALVED");
            }else{
                telemetry.addData("Power", "full");
            }
            //sendTelemetry();
        }
    }
    public void sendTelemetry(){
        telemetry.addData("Wrist",robot.relicWrist.getPosition());
        telemetry.addData("BLMotorPos", robot.BLMotor.getCurrentPosition());
        telemetry.addData("Ultra", robot.ultra.cmUltrasonic());
        telemetry.addData("Color", robot.color.blue());
        telemetry.addData("IMU", robot.IMUHeading());
        telemetry.update();
    }
    

}





