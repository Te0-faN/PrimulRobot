/*
 * STIL:
 *
 * Variabilele folosesc snake_case iar
 * clasele si functiile CamelCase.
 *
 * Intre if, while, for, switch si () se lasa un spatiu.
 *
 * Constantele se declara la inceputul clasei
 * si se denumesc folosind MAJUSCULE.
 *
 * Nu se folosesc comentarii de tipul // 
 * pentru a fi consistenti. 
 *
 * Codul trebuie sa fie in engleza deoarece
 * API-ul e in engleza.
 */
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.TouchSensor;

@TeleOp(name = "TestOp")
public class Test extends LinearOpMode
{
    final double ticks2 = 751.8f;
    double target2 = 0;
    double turnage2 = 0;

    DcMotor front_left_motor = hardwareMap.dcMotor.get("frontLeftMotor");
    DcMotor front_right_motor = hardwareMap.dcMotor.get("frontRightMotor");
    DcMotor back_left_motor  = hardwareMap.dcMotor.get("backLeftMotor");
    DcMotor back_right_motor = hardwareMap.dcMotor.get("backRightMotor");

    DcMotor worm_gear = hardwareMap.dcMotor.get("motor2");

    TouchSensor touch = hardwareMap.get(TouchSensor.class, "limitSwitch");

    private void InitializeWheels()
    {
        front_right_motor.setDirection(DcMotorSimple.Direction.REVERSE);
        back_right_motor.setDirection(DcMotorSimple.Direction.REVERSE);
        front_left_motor.setDirection(DcMotorSimple.Direction.FORWARD); /* Motorul e pus invers */
        back_left_motor.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    private void SetWheelsPower()
    {
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
    }

    private void InitializeWormGear()
    {
        worm_gear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        worm_gear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    private void UseWormGear()
    {
        boolean X = gamepad1.x;
        boolean dpad_up = gamepad1.dpad_up;
        boolean dpad_down = gamepad1.dpad_down;

        if (X) {
            worm_gear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            /*  Keep moving down until the touch sensor is pressed */
            while (!touch.isPressed()) {
                /*  Set a downward motion by providing a negative value for turnage2 */
                turnage2 = -0.5;  /*  Adjust this value according to your requirement */
                target2 = (turnage2 / 360) * 28 * ticks2;
                worm_gear.setTargetPosition((int) target2);
                worm_gear.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                worm_gear.setPower(0.5);
            }

            /*  Stop the motor once the touch sensor is pressed */
            worm_gear.setPower(0);

            /*  Move 2.5 degrees upward */
            turnage2 = 2.5;  /*  Set the turnage to positive 2.5 for upward movement */
            target2 = (turnage2 / 360) * 28 * ticks2;
            worm_gear.setTargetPosition((int) target2);
            worm_gear.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            worm_gear.setPower(0.5);

            /*  Wait until the motor reaches the target position */
            while (worm_gear.isBusy()) {
                /*  Optionally, you can include some code here if you need to perform other tasks while waiting */
            }

            /*  Stop the motor after reaching the target position */
            worm_gear.setPower(0);
        }

        if (dpad_up) {
            worm_gear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            turnage2 = 2.5;
            target2 = (turnage2 / 360) * 28 * ticks2;
            worm_gear.setTargetPosition((int) target2);
            worm_gear.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            worm_gear.setPower(0.5);
            if (touch.isPressed()) {
                worm_gear.setPower(0);
            }
        }

        if (dpad_down) {
            worm_gear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            turnage2 = -2.5;
            target2 = (turnage2 / 360) * 28 * ticks2;
            worm_gear.setTargetPosition((int) target2);
            worm_gear.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            worm_gear.setPower(0.5);
            if (touch.isPressed()) {
                worm_gear.setPower(0);
            }
        }


    }

    private void UpdateTelemetry() 
    {
        telemetry.addData("Roata stanga sus :", front_left_motor.getPower());
        telemetry.addData("Roata stanga jos :", back_left_motor.getPower());
        telemetry.addData("Roata dreapta sus:", front_right_motor.getPower());
        telemetry.addData("Roata dreapta jos:", back_right_motor.getPower());
        telemetry.update();                  
    }

    @Override
    public void runOpMode()
    {
        InitializeWheels();
        InitializeWormGear();
        waitForStart();

        while (opModeIsActive()) {
            SetWheelsPower();
            UseWormGear();

            UpdateTelemetry();
            sleep(10);
        }
    }

}
