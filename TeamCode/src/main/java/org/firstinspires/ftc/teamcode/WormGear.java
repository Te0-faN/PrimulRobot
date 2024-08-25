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
    @Override
    public void runOpMode() throws InterruptedException {

        DcMotor worm = hardwareMap.dcMotor.get("motor2");



        worm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        worm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        TouchSensor touch;
        touch = hardwareMap.get(TouchSensor.class, "limitSwitch");

        double ticks = 384.5;
        double target1 = 0;
        double turnage = 0;

        double ticks2 = 751.8;
        double target2 = 0;
        double turnage2 = 0;
        double unghi_max = 400;
        boolean reset2 = false;
        double duration = 1.2;
        ElapsedTime elapsedTime;
        elapsedTime = new ElapsedTime();
        double duration1 = 1.2;
        boolean intake = false;
        boolean score = false;
        boolean score2=false;
        int poz_jos = 1;//daca nr este impar nu se afla in pozitia de jos

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            double y = -gamepad1.left_stick_y;
            double x = gamepad1.left_stick_x * 1.1;
            double rx = gamepad1.right_stick_x;

            boolean X = gamepad1.x;
            boolean B = gamepad1.b;
            boolean A = gamepad1.a; //pozitia initiala slide care nu va mai exista
            boolean Y = gamepad1.y;
            double leftStickX = gamepad1.left_stick_x;
            double leftStickY = gamepad1.left_stick_y;
            double rightStickX = gamepad1.right_stick_x;
            double rightStickY = gamepad1.right_stick_y;
            boolean RB = gamepad1.right_bumper;
            boolean LB = gamepad1.left_bumper;
            float LT = gamepad1.left_trigger;
            float RT = gamepad1.right_trigger;
            boolean s = gamepad1.dpad_up;
            boolean d = gamepad1.dpad_right;
            boolean st = gamepad1.dpad_left;
            boolean j = gamepad1.dpad_down;
            boolean ps = gamepad1.ps;

            if (A) {
                worm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                turnage2 = 2.5;
                target2 = (turnage2 / 360) * 28 * ticks2;
                worm.setTargetPosition((int) target2);
                worm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                worm.setPower(0.5);
                target2 = 0;
                poz_jos = 2;//trebuie 2 aici ig
                if (touch.isPressed()) {
                    worm.setPower(0);
                }
            }

            if (X) {
                worm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                turnage2 = -2.5;
                target2 = (turnage2 / 360) * 28 * ticks2;
                worm.setTargetPosition((int) target2);
                worm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                worm.setPower(0.5);
                target2 = 0;
                poz_jos = 2;//trebuie 2 aici aparent
                if (touch.isPressed()) {
                    worm.setPower(0);
                }
            }

            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);

            telemetry.update();
        }
    }
}