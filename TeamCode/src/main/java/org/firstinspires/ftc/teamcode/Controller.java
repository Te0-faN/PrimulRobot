package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;

@TeleOp(name = "Controller")
public class Controller extends LinearOpMode
{

    @Override
    public void runOpMode()
    {
         double ticks2 = 751.8f;
         double maxPos = 180;
         double curentPos = 0;
         double target2 = 0;
         double turnage2 = 0;


         DcMotor front_left_motor = hardwareMap.dcMotor.get("frontLeftMotor");
         DcMotor front_right_motor = hardwareMap.dcMotor.get("frontRightMotor");
         DcMotor back_left_motor  = hardwareMap.dcMotor.get("backLeftMotor");
         DcMotor back_right_motor = hardwareMap.dcMotor.get("backRightMotor");

         DcMotor worm_gear = hardwareMap.dcMotor.get("motor2");
         DcMotor wheel = hardwareMap.dcMotor.get("roata");



        front_right_motor.setDirection(DcMotorSimple.Direction.REVERSE);
        back_right_motor.setDirection(DcMotorSimple.Direction.REVERSE);
        front_left_motor.setDirection(DcMotorSimple.Direction.FORWARD);
        wheel.setDirection(DcMotorSimple.Direction.FORWARD);
        back_left_motor.setDirection(DcMotorSimple.Direction.REVERSE);
        worm_gear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        worm_gear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        waitForStart();
        if (isStopRequested()) return;

        while (opModeIsActive()) {

            /* Stick ul din stanga e folosit pentru deplasare pe orizontala si cel din stanga pentru rotire */
            double left_stick_x  =  gamepad1.left_stick_x;
            double left_stick_y  = -gamepad1.left_stick_y;
            double right_stick_x =  gamepad1.right_stick_x;

            double normalizer = Math.max(Math.abs(left_stick_x) +
                    Math.abs(left_stick_y) +
                    Math.abs(right_stick_x), 1.0);

            double front_left_power  = (left_stick_y + left_stick_x + right_stick_x) / normalizer;
            double back_left_power   = (left_stick_y - left_stick_x + right_stick_x) / normalizer;
            double front_right_power = (left_stick_y - left_stick_x - right_stick_x) / normalizer;
            double back_right_power  = (left_stick_y + left_stick_x - right_stick_x) / normalizer;

            front_left_motor.setPower(front_left_power);
            back_left_motor.setPower(back_left_power);
            front_right_motor.setPower(front_right_power);
            back_right_motor.setPower(back_right_power);

            boolean X = gamepad1.x;
            boolean A = gamepad1.a;
            boolean dpad_up = gamepad1.dpad_down;
            boolean dpad_down = gamepad1.dpad_up;

            if (X) {
                wheel.setPower(-0.75);
            } else {
                wheel.setPower(0);
            }

            if (A) {
                wheel.setPower(-1);
            } else {
                wheel.setPower(0);
            }
            if (dpad_up) {
                worm_gear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                turnage2 = 1.5;
                target2 = (turnage2 / 360) * 28 * ticks2;
                worm_gear.setTargetPosition((int) target2);
                worm_gear.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                worm_gear.setPower(0.75);
                    curentPos = curentPos + 2.5;

            }

            if (dpad_down) {
                worm_gear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                turnage2 = -1.5;
                target2 = (turnage2 / 360) * 28 * ticks2;
                worm_gear.setTargetPosition((int) target2);
                worm_gear.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                worm_gear.setPower(0.75);
                    curentPos = curentPos - 2.5;

            }

            telemetry.addData("Roata stanga sus :", front_left_motor.getPower());
            telemetry.addData("Roata stanga jos :", back_left_motor.getPower());
            telemetry.addData("Roata dreapta sus:", front_right_motor.getPower());
            telemetry.addData("Roata dreapta jos:", back_right_motor.getPower());
            telemetry.update();
        }
    }

}
