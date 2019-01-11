package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.Hardware;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class RoverRuckusTeleop extends LinearOpMode {
    RoverRuckusHardware robot = new RoverRuckusHardware();

    public void runOpMode() {
        robot.init(hardwareMap);
        telemetry.addData("Status", "Inititialized");
        telemetry.update();

        double nerf = 0.6;
        boolean targetSet=false;
        int targetVal;
        waitForStart();

        while (opModeIsActive()) {
            sendTelemetry();
            //    telemetry.addData("Status", "looping");
            //telemetry.addData("Ultra", robot.ultra.cmUltrasonic());
            // This sets the motor powers to allow the mecanum wheels to drive in the direction the left stick
            //
            /*
             robot.flMotor.setPower(-gamepad1.left_stick_y + gamepad1.left_stick_x - gamepad1.right_stick_x);
             robot.frMotor.setPower(-gamepad1.left_stick_y - gamepad1.left_stick_x + gamepad1.right_stick_x);
             robot.blMotor.setPower(-gamepad1.left_stick_y - gamepad1.left_stick_x - gamepad1.right_stick_x);
             robot.brMotor.setPower(-gamepad1.left_stick_y + gamepad1.left_stick_x + gamepad1.right_stick_x);
            */

            if (gamepad1.b) {
                nerf = .6;
            } else if (gamepad1.a) {
                nerf = 1;
            }

            robot.flMotor.setPower(nerf * (-gamepad1.left_stick_y + gamepad1.left_stick_x - gamepad1.right_stick_x));
            robot.frMotor.setPower(nerf * (-gamepad1.left_stick_y - gamepad1.left_stick_x + gamepad1.right_stick_x));
            robot.blMotor.setPower(nerf * (-gamepad1.left_stick_y - gamepad1.left_stick_x - gamepad1.right_stick_x));
            robot.brMotor.setPower(nerf * (-gamepad1.left_stick_y + gamepad1.left_stick_x + gamepad1.right_stick_x));

            if (nerf < 1) {
                telemetry.addData("Power", "HALVED");
            } else {
                telemetry.addData("Power", "full");
            }
            /* Spin Motor Controls */
            //if (gamepad2.dpad_left) {

            //    robot.spinMotor.setPower(0.5);
            //}
            //else if (gamepad2.dpad_right) {
            //    robot.spinMotor.setPower(-0.5);
            // }
            // else { 
            //robot.spinMotor.setPower(0);
            //}
            /* Endgame Lift Controls */
            if (gamepad1.dpad_up) {
                robot.armLiftMotor.setPower(-1);
            } else if (gamepad1.dpad_down) {
                robot.armLiftMotor.setPower(1);
            } else {
                robot.armLiftMotor.setPower(0);
            }

            /* Grab Servo Controls */
            // while (gamepad2.right_bumper){
            if (gamepad2.a) {
                robot.grabServo.setPosition(1);
            } else if (gamepad2.b) {
                robot.grabServo.setPosition(-1);
            } else {
                robot.grabServo.setPosition(0.5);
            }

              /* Flip Motor 
            if (gamepad2.dpad_up) {
                if (flipPos.getCurrentPosition() < 400)
                robot.flipMotor.setPower(-1);
                else (flip)
            } else if (gamepad2.dpad_down) {
                robot.flipMotor.setPower(1);
            } else {
                robot.flipMotor.setPower(0);
            }
            */
            /* Flip Motor */
            int x = 0, y = 0, z = 0;
            if (gamepad2.dpad_up) {
                if (robot.flipMotor.getCurrentPosition() < x) {
                    robot.flipMotor.setPower(1);
                } else {
                    robot.flipMotor.setPower(0);
                }
            } else if (gamepad2.dpad_right) {
                if (robot.flipMotor.getCurrentPosition() < y) {
                    robot.flipMotor.setPower(1);
                } else {
                    robot.flipMotor.setPower(0);
                }
            } else if (gamepad2.dpad_left) {
                if (robot.flipMotor.getCurrentPosition() < z) {
                    robot.flipMotor.setPower(1);
                } else {
                    robot.flipMotor.setPower(0);
                }
            } else if (gamepad2.dpad_down) {
                robot.flipMotor.setPower(-1);
            } else {
                robot.flipMotor.setPower(0);
            } 
    
             /* Flip Motor */
            if (gamepad2.dpad_up) {
                robot.flipMotor.setPower(-1);
            } else if (gamepad2.dpad_down) {
                robot.flipMotor.setPower(1);
            } else {
                robot.flipMotor.setPower(0);
            }


            if (gamepad1.left_bumper) {
                robot.wardServo.setPosition(1);
            } else if (gamepad1.right_bumper) {
                robot.wardServo.setPosition(0);
            }
            /* Extention Motor */
            if (gamepad2.x) {
                robot.extentionMotor.setPower(-1);
            } else if (gamepad2.y) {
                robot.extentionMotor.setPower(1);
            } else {
                robot.extentionMotor.setPower(0);
            }

            if (gamepad2.left_bumper) {
                robot.cockBlocker.setPosition(0.2);
            } else if (gamepad2.right_bumper) {
                robot.cockBlocker.setPosition(0.9);
            }
            
            //  robot.extensionMotor.setPower(gamepad2.right_stick_y);
             //if (gamepad2.left_stick_y>.1)
            //robot.flipMotor.setPower(gamepad2.left_stick_y);
            
            /*if (gamepad2.left_stick_y<.1&&gamepad2.left_stick_y>-.1){
                if (!targetSet){
                    targetVal=robot.flipMotor.getCurrentPosition();
                    robot.flipMotor.setTargetPosition(targetVal);
                    robot.flipMotor.setPower(1);
                    targetSet = true;
                }
                robot.flipMotor.getCurrentPosition();
                robot.flipMotor.setPower(1);
            }
            else if (gamepad2.left_stick_y<-.1){ 
                targetSet = false;
                robot.flipMotor.setTargetPosition(1000000000);
                robot.flipMotor.setPower(1);
            }
            else if (gamepad2.left_stick_y>.1){
                targetSet = false;
                robot.flipMotor.setTargetPosition(-1000000000);
                robot.flipMotor.setPower(1);
            }   
            */
            telemetry.addData("BackWheelEncoVal", robot.brMotor.getCurrentPosition());
            telemetry.addData("Flip Pos", robot.flipMotor.getCurrentPosition());
            telemetry.addData("Flip Pow", gamepad2.left_stick_y);
            telemetry.addData("TargetVal", targetSet);
            telemetry.addData("TargetPos", robot.flipMotor.getTargetPosition());
            telemetry.update();
        }
    }
    
    public void sendTelemetry() {
        telemetry.addData("armLiftMotor", robot.armLiftMotor.getCurrentPosition());
    }
}


