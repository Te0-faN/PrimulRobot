package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "TeofanTestOP")
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
		float left_stick_x  =  gamepad1.left_stick_x;		
		float left_stick_y  = -gamepad1.left_stick_y;		
		float right_stick_x =  gamepad1.right_stick_x;

		float normal

		sleep(10);
	}
    }
}
