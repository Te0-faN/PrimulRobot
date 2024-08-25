package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "TestOP")
public class Test extends LinearOpMode
{
    @Override
    public void runOpMode()
    {
        DcMotor front_left_motor = hardwareMap.dcMotor.get("frontLeftMotor");
        DcMotor front_right_motor = hardwareMap.dcMotor.get("frontRightMotor");
        DcMotor back_left_motor  = hardwareMap.dcMotor.get("backLeftMotor");
        DcMotor back_right_motor = hardwareMap.dcMotor.get("backRightMotor");

        front_right_motor.setDirection(DcMotorSimple.Direction.REVERSE);
        back_right_motor.setDirection(DcMotorSimple.Direction.REVERSE);
        front_left_motor.setDirection(DcMotorSimple.Direction.FORWARD); /* Motorul e pus invers */
        back_left_motor.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();

        while (opModeIsActive())
	{
		/* Stick ul din stanga e folosit pentru deplasare pe orizontala si cel din stanga pentru rotire */
		double left_stick_x  =  gamepad1.left_stick_x;		
		double left_stick_y  = -gamepad1.left_stick_y;		
		double right_stick_x =  gamepad1.right_stick_x;

		double normalizer = Math.max(Math.abs(left_stick_x) +
				    Math.abs(left_stick_y) + 
				    Math.abs(right_stick_x), 1.0);

		double frontLeftPower  = ( left_stick_y + left_stick_x + right_stick_x) / normalizer;
		double backLeftPower   = (-left_stick_y + left_stick_x - right_stick_x) / normalizer;
		double frontRightPower = ( left_stick_y - left_stick_x - right_stick_x) / normalizer;
		double backRightPower  = ( left_stick_y + left_stick_x - right_stick_x) / normalizer;
		
		front_left_motor.setPower(frontLeftPower);
		back_left_motor.setPower(backLeftPower);
		front_right_motor.setPower(frontRightPower);
		back_right_motor.setPower(backRightPower);

		sleep(10);
	}
    }
}
