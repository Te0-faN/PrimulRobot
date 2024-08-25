package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="Victor Sutac")
public class WormGear extends LinearOpMode {
    final double ticks2 = 751.8;
    double target2 = 0;
    double turnage2 = 0;
    @Override
    public void runOpMode() throws InterruptedException {

        DcMotor worm = hardwareMap.dcMotor.get("motor2");



        worm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        worm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        TouchSensor touch;
        touch = hardwareMap.get(TouchSensor.class, "limitSwitch");



        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {

            boolean X = gamepad1.x;
            boolean s = gamepad1.dpad_up;
            boolean j = gamepad1.dpad_down;
            if (X) {
                worm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

                // Keep moving down until the touch sensor is pressed
                while (!touch.isPressed()) {
                    // Set a downward motion by providing a negative value for turnage2
                    turnage2 = -0.5;  // Adjust this value according to your requirement
                    target2 = (turnage2 / 360) * 28 * ticks2;
                    worm.setTargetPosition((int) target2);
                    worm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    worm.setPower(0.5);

                    // Break the loop if the touch sensor is pressed
                    if (touch.isPressed()) {
                        worm.setPower(0);
                        break;
                    }
                }

                // Stop the motor once the touch sensor is pressed
                worm.setPower(0);

                // Move 2.5 degrees upward
                turnage2 = 2.5;  // Set the turnage to positive 2.5 for upward movement
                target2 = (turnage2 / 360) * 28 * ticks2;
                worm.setTargetPosition((int) target2);
                worm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                worm.setPower(0.5);

                // Wait until the motor reaches the target position
                while (worm.isBusy()) {
                    // Optionally, you can include some code here if you need to perform other tasks while waiting
                }

                // Stop the motor after reaching the target position
                worm.setPower(0);
            }

            if (s) {
                worm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                turnage2 = 2.5;
                target2 = (turnage2 / 360) * 28 * ticks2;
                worm.setTargetPosition((int) target2);
                worm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                worm.setPower(0.5);
                if (touch.isPressed()) {
                    worm.setPower(0);
                }
            }

            if (j) {
                worm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                turnage2 = -2.5;
                target2 = (turnage2 / 360) * 28 * ticks2;
                worm.setTargetPosition((int) target2);
                worm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                worm.setPower(0.5);
                if (touch.isPressed()) {
                    worm.setPower(0);
                }
            }


            telemetry.update();
        }
    }
}