package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.TouchSensor;

@TeleOp(name = "TestOp")
public class Test extends LinearOpMode
{
    DcMotor front_left_motor;
    DcMotor front_right_motor;
    DcMotor back_left_motor;
    DcMotor back_right_motor;

    DcMotor worm_gear;

    private void InitializeWheels()
    {
        front_left_motor  = hardwareMap.dcMotor.get("frontLeftMotor");
        front_right_motor = hardwareMap.dcMotor.get("frontRightMotor");
        back_left_motor   = hardwareMap.dcMotor.get("backLeftMotor");
        back_right_motor  = hardwareMap.dcMotor.get("backRightMotor");

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
        worm_gear = hardwareMap.dcMotor.get("motor2");

        worm_gear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        worm_gear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    private void UseWormGear()
    {
        TouchSensor touch = hardwareMap.get(TouchSensor.class, "limitSwitch");

        /* De ce sunt sufixate cu 2? */  //Pentru ca la un momentdat exista 1
        final float ticks = 751.8f;
        double target;
        double turnage;

        float MAX_POSITION = -180; /* ? */ // Daca nu sti ce face ceva nu schimba, ai redenumit variabila si probabil ai luat pana la -1 in loc de -180
        float current_position   = 0.0f;

        boolean X         = gamepad1.x;
        boolean dpad_up   = gamepad1.dpad_up;
        boolean dpad_down = gamepad1.dpad_down;

        if (X) {
            worm_gear.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

            worm_gear.setPower(0.5);
            while (!touch.isPressed()) {

            }; /* Asteapta sa se apese */

            worm_gear.setPower(0);
            worm_gear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            worm_gear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            turnage = 10;
            target = (turnage / 360) * 28 * ticks;
            worm_gear.setTargetPosition((int) target);
            worm_gear.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            worm_gear.setPower(0.5);
            current_position += 10f;

            worm_gear.setPower(0);
        }

        if (dpad_up) {
            if (current_position != MAX_POSITION )  {/* Explica */ //aveai cod vechi
                worm_gear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                turnage = 2.5;
                target = (turnage / 360) * 28 * ticks;
                worm_gear.setTargetPosition((int) target);
                worm_gear.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                worm_gear.setPower(0.5);
                current_position += 2.5f;

            }
        }

        if (dpad_down) {
            worm_gear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            turnage = -2.5;
            target = (turnage / 360) * 28 * ticks;
            worm_gear.setTargetPosition((int) target);
            worm_gear.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            worm_gear.setPower(0.5);
            if (touch.isPressed()) {
                worm_gear.setPower(0);
            } else {
                current_position -= 2.5f;
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
        waitForStart();

        InitializeWheels();
        InitializeWormGear();

        while (opModeIsActive()) {
            SetWheelsPower();
            UseWormGear();

            UpdateTelemetry();
        }
    }

}
