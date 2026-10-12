package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="TemaRobot", group="Competition")
public class TemaRobot extends OpMode {

    DcMotor leftMotor;
    DcMotor rightMotor;

    Servo armPivot;
    Servo claw;

    boolean clawOpen = false;
    boolean xWasPressed = false;

    @Override
    public void init() {
        leftMotor = hardwareMap.get(DcMotor.class, "motor_left");
        rightMotor = hardwareMap.get(DcMotor.class, "motor_right");
        armPivot = hardwareMap.get(Servo.class, "arm_pivot");
        claw = hardwareMap.get(Servo.class, "claw");

        leftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        rightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        armPivot.setPosition(0.2);
        claw.setPosition(0.0);

        telemetry.addData("Status", "Robot pregatit!");
        telemetry.update();
    }

    @Override
    public void loop() {
        double drive = -gamepad1.left_stick_y;
        double turn = gamepad1.right_stick_x;

        double leftPower = Range.clip(drive + turn, -1.0, 1.0);
        double rightPower = Range.clip(drive - turn, -1.0, 1.0);

        leftMotor.setPower(leftPower);
        rightMotor.setPower(rightPower);

        if (gamepad1.dpad_up) {
            armPivot.setPosition(0.85);
        }
        if (gamepad1.dpad_down) {
            armPivot.setPosition(0.2);
        }

        if (gamepad1.x && !xWasPressed) {
            clawOpen = !clawOpen;

            if (clawOpen) {
                claw.setPosition(0.5);
            } else {
                claw.setPosition(0.0);
            }
        }
        xWasPressed = gamepad1.x;

        telemetry.addData("Putere Stanga", leftPower);
        telemetry.addData("Putere Dreapta", rightPower);
        telemetry.addData("Brat", armPivot.getPosition());
        telemetry.addData("Gheara deschisa", clawOpen);
        telemetry.update();
    }
}
