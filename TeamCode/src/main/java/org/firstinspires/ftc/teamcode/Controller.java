package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "Controller")
public class Controller extends LinearOpMode
{
    DcMotor front_left_motor;
    DcMotor front_right_motor;
    DcMotor back_left_motor;
    DcMotor back_right_motor;

    DcMotor worm_gear;
    DcMotor wheel;

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
    }

    private void UseWormGear()
    {
        /* Unitatea cu care se roteste un motor */
        final float TICKS = 751.8f;
        double target;
        double turnage;
        
        if (gamepad1.dpad_up) {
            worm_gear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            turnage = -1.5;
            target = (turnage / 360) * 28 * TICKS;
            worm_gear.setTargetPosition((int)target);
            worm_gear.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            worm_gear.setPower(0.75);
        }

        if (gamepad1.dpad_down) {
            worm_gear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            turnage =  1.5;
            target = (turnage / 360) * 28 * TICKS;
            worm_gear.setTargetPosition((int)target);
            worm_gear.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            worm_gear.setPower(0.75);
        }

        if (gamepad1.left_trigger > 0.0) {
                worm_gear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                turnage = 10;
                target = (turnage / 360) * 28 * TICKS;
                worm_gear.setTargetPosition((int) target);
                worm_gear.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                worm_gear.setPower(0.75);
        }

        if (gamepad1.right_trigger > 0.0) {
                worm_gear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                turnage = -10;
                target = (turnage / 360) * 28 * TICKS;
                worm_gear.setTargetPosition((int) target);
                worm_gear.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                worm_gear.setPower(0.75);
        }
}

    private void InitializeWheel()
    {
        wheel = hardwareMap.dcMotor.get("roata");
        wheel.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    private void UseWheel()
    {
        float power = 0.0f;

        if (gamepad1.a)
            power =  0.5f;
        else if (gamepad1.x)
            power = -0.5f;
        else
            power = 0;

        if (gamepad1.left_bumper)
            power += 0.05f;
        else if (gamepad1.right_bumper)
            power -= 0.05f;

        wheel.setPower(power);
    }

    private void UpdateTelemetry()
    {
        telemetry.addData("Roata stanga sus :", front_left_motor.getPower());
        telemetry.addData("Roata stanga jos :", back_left_motor.getPower());
        telemetry.addData("Roata dreapta sus:", front_right_motor.getPower());
        telemetry.addData("Roata dreapta jos:", back_right_motor.getPower());
        telemetry.addData("Unghi worm gear  :", worm_gear.getCurrentPosition());
        telemetry.addData("Putere Roata     :", wheel.getPower());
        telemetry.update();
    }

    @Override
    public void runOpMode()
    {
        InitializeWheels();
        InitializeWormGear();
        InitializeWheel();

        waitForStart();

        while (opModeIsActive()) {
            SetWheelsPower();
            UseWormGear();
            UseWheel();

            UpdateTelemetry();
        }
    }

}
