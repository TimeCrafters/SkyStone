package org.timecrafters.Summer_2020.Nathaniel;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.cyberarm.engine.V2.CyberarmStateV2;

public class MiniBotTesting2 extends CyberarmStateV2 {

    private BNO055IMU IMU;
    private DcMotor DriveLeft;
    private DcMotor DriveRight;
    private float relativeRotation;
    private float targetRotation;
    private double speedAdjust;
    private double basePower;
    private float currentRotation;
    private float startingRotation;
    private boolean resetCurrentPosition;
    private boolean changedControlSpeed;
    private double controlSpeed =0.6;



    @Override
    public void init() {

        //IMU init
        //------------------------------------------------------------------------------------------

        IMU = cyberarmEngine.hardwareMap.get(BNO055IMU.class, "imu");

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();

        parameters.mode = BNO055IMU.SensorMode.IMU;
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.loggingEnabled = false;

        IMU.initialize(parameters);

        //Drive Motor Init
        //------------------------------------------------------------------------------------------

        DriveRight = cyberarmEngine.hardwareMap.dcMotor.get("rightDrive");
        DriveLeft = cyberarmEngine.hardwareMap.dcMotor.get("leftDrive");

        DriveRight.setDirection(DcMotorSimple.Direction.REVERSE);

        DriveLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        DriveRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        DriveLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        DriveRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


    }

    @Override
    public void exec() {

        if (cyberarmEngine.gamepad1.dpad_up && !changedControlSpeed && controlSpeed < 1.0) {
            controlSpeed += 0.2;
            changedControlSpeed = true;
        } else if (cyberarmEngine.gamepad1.dpad_down && !changedControlSpeed && controlSpeed > 0.2) {
            controlSpeed -= 0.2;
            changedControlSpeed = true;
        } else {
            changedControlSpeed = false;
        }

        double leftStickX = cyberarmEngine.gamepad1.left_stick_x;
        double leftStickY = cyberarmEngine.gamepad1.left_stick_y;

        float sensorRotation = IMU.getAngularOrientation().firstAngle;


        resetCurrentPosition = (cyberarmEngine.gamepad1.start && cyberarmEngine.gamepad1.left_bumper && !resetCurrentPosition);

        if (resetCurrentPosition) {
            startingRotation = sensorRotation;
        }


        if ((leftStickX != 0 || leftStickY !=0) && !cyberarmEngine.gamepad1.start) {

            basePower = controlSpeed * Math.sqrt(Math.pow(leftStickX,2) + Math.pow(leftStickY, 2));

            currentRotation = getRelativeAngle(startingRotation, sensorRotation);

            targetRotation= getJoystickDegrees();

            //calculate the angle of the robot relative to the targeted rotation.
            //--------------------------------------------------------------------------------------
            relativeRotation = getRelativeAngle(targetRotation, currentRotation);
            //--------------------------------------------------------------------------------------

            //calculate how the power of each motor should be adjusted to make the robot curve
            //towards the target angle
            //--------------------------------------------------------------------------------------
            double turnPowerCorrection = (Math.pow(relativeRotation, 2)) / 900;

            if (relativeRotation < 0) {
                turnPowerCorrection*=-1;
            }

            //Adjusts power based on degrees off from target.
            double leftPower = basePower - turnPowerCorrection;
            double rightPower = basePower + turnPowerCorrection;
            //--------------------------------------------------------------------------------------


            //calculates speed adjuster that slows the motors to be closer to the BasePower while
            // maintaining the power ratio nesesary to execute the turn.
            speedAdjust = ((2 * basePower) / (Math.abs(leftPower) + Math.abs(rightPower)));

            DriveLeft.setPower(leftPower * speedAdjust);
            DriveRight.setPower(rightPower * speedAdjust);


        } else {
            DriveLeft.setPower(0);
            DriveRight.setPower(0);
        }

    }

    @Override
    public void telemetry() {

        cyberarmEngine.telemetry.addData("Starting Rotation", startingRotation);
        cyberarmEngine.telemetry.addData("Relative Rotation", relativeRotation);
        cyberarmEngine.telemetry.addData("Speed Adjust", speedAdjust);
    }

    private float getRelativeAngle(float target, float current) {
        float relative = current - target;

        if (relative < -180) {
            relative += 360;
        }

        if (relative > 180) {
            relative -= 360;
        }
        return relative;
    }

    private float getJoystickDegrees() {

        double left_stick_y = -cyberarmEngine.gamepad1.left_stick_y;
        double left_stick_x = cyberarmEngine.gamepad1.left_stick_x;
        float zeroToNinety = (float) Math.toDegrees(Math.atan(left_stick_x / left_stick_y));

        cyberarmEngine.telemetry.addData("raw", zeroToNinety);
        float JoystickDegrees = 0;

        if (left_stick_y > 0) {

            zeroToNinety = -zeroToNinety;
            JoystickDegrees = zeroToNinety;

        } else if (left_stick_y <= 0) {

            if (zeroToNinety > 0) {
                JoystickDegrees = 180 - zeroToNinety;
            } else if (zeroToNinety < 0) {
                JoystickDegrees = -180 - zeroToNinety;
            } else if (left_stick_x == 0) {
                JoystickDegrees = 180;
            }
        }
        return JoystickDegrees;
    }
}
