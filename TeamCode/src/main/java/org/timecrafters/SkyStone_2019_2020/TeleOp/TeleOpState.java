package org.timecrafters.SkyStone_2019_2020.TeleOp;

import org.timecrafters.SkyStone_2019_2020.Drive;
import org.timecrafters.engine.Engine;


public class TeleOpState extends Drive {

    private double JoystickDegrees;
    private double forwardLeftPower;
    private double forwardRightPower;

    @Override
    public void init() {

        super.init();
    }

    public TeleOpState(Engine engine) {
        this.engine = engine;
    }

    @Override
    public void exec() throws InterruptedException {


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

        } else {

            //if the robot isn't being turned, drive the robot in the direction of the left Joystick

            calcJoystickDegrees();

            forwardLeftPower = powerThrottle * getForwardLeftPower(JoystickDegrees, 0.1);
            forwardRightPower = powerThrottle * getForwardRightPower(JoystickDegrees, 0.1);

            DriveForwardLeft.setPower(forwardLeftPower);
            DriveForwardRight.setPower(forwardRightPower);
            DriveBackLeft.setPower(forwardRightPower);
            DriveBackRight.setPower(forwardLeftPower);
        }


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

