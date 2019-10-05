package org.timecrafters.SkyStone_2019_2020.TeleOp;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.timecrafters.SkyStone_2019_2020.Drive;
import org.timecrafters.engine.Engine;


public class TeleOpState extends Drive {

    private double JoystickDegrees;
    private double forwardLeftPower;
    private double forwardRightPower;
    private DcMotor RightLift;
    private DcMotor LeftLift;

    @Override
    public void init() {
        super.init();

        RightLift = engine.hardwareMap.dcMotor.get("liftRight");
        RightLift.setDirection(DcMotor.Direction.REVERSE);
        LeftLift = engine.hardwareMap.dcMotor.get("liftLeft");

        RightLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LeftLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RightLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        LeftLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

    public TeleOpState(Engine engine) {
        this.engine = engine;
    }

    @Override
    public void exec() throws InterruptedException {

        //Lift
        double lift_stick_y = engine.gamepad2.left_stick_y;

        if (lift_stick_y > 0) {
            if (RightLift.getCurrentPosition() < 400) {
                RightLift.setPower(0.5*lift_stick_y);
            }
            if (LeftLift.getCurrentPosition() < 400) {
                LeftLift.setPower(0.5*lift_stick_y);
            }
        } else if (lift_stick_y < 0) {
            if (RightLift.getCurrentPosition() > 0) {
                RightLift.setPower(0.5*lift_stick_y);
            }
            if (LeftLift.getCurrentPosition() > 0) {
                LeftLift.setPower(0.5*lift_stick_y);
            }
        } else {
            RightLift.setPower(0);
            LeftLift.setPower(0);
        }


        //Drive
        //--------------------------------------------------------------------------
        double powerThrottle = engine.gamepad1.right_trigger;

        if (engine.gamepad1.right_stick_x != 0) {

            //if the right joystick is moved to either side, turn the robot
            if (engine.gamepad1.right_stick_x > 0) {
                DriveForwardLeft.setPower(powerThrottle);
                DriveForwardRight.setPower(-powerThrottle);
                DriveBackLeft.setPower(powerThrottle);
                DriveBackRight.setPower(-powerThrottle);
            } else {
                DriveForwardLeft.setPower(-powerThrottle);
                DriveForwardRight.setPower(powerThrottle);
                DriveBackLeft.setPower(-powerThrottle);
                DriveBackRight.setPower(powerThrottle);
            }

        } else if (engine.gamepad1.left_stick_x !=0 || engine.gamepad1.left_stick_y !=0) {

            //if the robot isn't being turned, drive the robot in the direction of the left Joystick

            calcJoystickDegrees();

            forwardLeftPower = powerThrottle * getForwardLeftPower(JoystickDegrees + getRobotRotation(), 0.1);
            forwardRightPower = powerThrottle * getForwardRightPower(JoystickDegrees +  getRobotRotation(), 0.1);

            DriveForwardLeft.setPower(forwardLeftPower);
            DriveForwardRight.setPower(forwardRightPower);
            DriveBackLeft.setPower(forwardRightPower);
            DriveBackRight.setPower(forwardLeftPower);
        } else {
            DriveForwardLeft.setPower(0);
            DriveForwardRight.setPower(0);
            DriveBackLeft.setPower(0);
            DriveBackRight.setPower(0);
        }
        //------------------------------------------------------------------------

        engine.telemetry.addData("Power Throttle", powerThrottle);
        engine.telemetry.addData("Stick Degrees", JoystickDegrees);
        engine.telemetry.addData("Forward Left Power", forwardLeftPower);
        engine.telemetry.addData("Forward Right Power", forwardRightPower);
        engine.telemetry.addData("Robot Rotation", getRobotRotation());

        engine.telemetry.update();
    }

    private void calcJoystickDegrees() {

        double left_stick_y = -engine.gamepad1.left_stick_y;
        double left_stick_x = -engine.gamepad1.left_stick_x;
        double zeroToNinety = Math.toDegrees(Math.atan(left_stick_x / left_stick_y));

        engine.telemetry.addData("raw", zeroToNinety);

        if (left_stick_y == 0 && left_stick_x == 0) {

            JoystickDegrees = 0;

        } else if (left_stick_y > 0) {

            zeroToNinety = -zeroToNinety;
            JoystickDegrees = zeroToNinety;

        } else if (left_stick_y <= 0) {

            if (zeroToNinety > 0) {
                JoystickDegrees = 180 - zeroToNinety;
            } else if (zeroToNinety < 0) {
                JoystickDegrees = -180 - zeroToNinety ;
            } else if (left_stick_x == 0) {
                JoystickDegrees = 180;
            }
        }


    }

    @Override
    public void telemetry() {

    }
}

