package org.timecrafters.Summer_2020.Nathaniel;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class MiniBotTesting extends State {

    private BNO055IMU IMU;
    private DcMotor DriveLeft;
    private DcMotor DriveRight;
    private double HighPower;
    private double LowPower;
    private float relativeRotation;
    private float targetRotation;
    private boolean hasRun;
    private Rev2mDistanceSensor DistanceSensor;
    private double avoidThreshold;
    private double reverseThreshold;
    private boolean HasChangedDirection;
    private boolean HasSetDirection;
    private long lastTurnTime;
    private double speedAdjust;
    private boolean Reverseing;
    private int ReverseTicks;

    public MiniBotTesting(Engine engine, double highPower, double lowPower, double cmThreshold, double reverseThreshold , int reverseTicks) {
        this.engine = engine;
        HighPower = highPower;
        LowPower = lowPower;
        avoidThreshold = cmThreshold;
        this.reverseThreshold = reverseThreshold;
        ReverseTicks = reverseTicks;
    }

    @Override
    public void init() {


        //IMU init
        //------------------------------------------------------------------------------------------

        IMU = engine.hardwareMap.get(BNO055IMU.class, "imu");

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();

        parameters.mode = BNO055IMU.SensorMode.IMU;
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.loggingEnabled = false;

        IMU.initialize(parameters);

        //Drive Motor Init
        //------------------------------------------------------------------------------------------

        DriveRight = engine.hardwareMap.dcMotor.get("rightDrive");
        DriveLeft = engine.hardwareMap.dcMotor.get("leftDrive");

        DriveRight.setDirection(DcMotorSimple.Direction.REVERSE);

        DriveLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        DriveRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        DriveLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        DriveRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //Distance Sensor

        DistanceSensor = engine.hardwareMap.get(Rev2mDistanceSensor.class, "forwardDistance");
    }

    @Override
    public void exec() throws InterruptedException {

        float currentRotatoin = IMU.getAngularOrientation().firstAngle;
        double distanceToObject = DistanceSensor.getDistance(DistanceUnit.CM);

        if (!hasRun) {
            targetRotation = currentRotatoin;
            hasRun = true;
        }

        if (!Reverseing) {
            relativeRotation = currentRotatoin - targetRotation;

            if (relativeRotation < -180) {
                relativeRotation += 360;
            }

            if (relativeRotation > 180) {
                relativeRotation -= 360;
            }

            double bacePower = ((HighPower - LowPower)*(Math.pow(1.2, -Math.abs(relativeRotation)))) + LowPower;

            //Adjusts power based on degrees off from target.
            double leftPower = bacePower - (relativeRotation * .01);
            double rightPower = bacePower + (relativeRotation * .01);

            //calculates speed adjuster so
            speedAdjust = ((2 * bacePower) / (Math.abs(leftPower) + Math.abs(rightPower)));

            DriveLeft.setPower(leftPower * speedAdjust);
            DriveRight.setPower(rightPower * speedAdjust);

            long currentTime = System.currentTimeMillis();

            if (distanceToObject < avoidThreshold && distanceToObject > reverseThreshold && !HasChangedDirection && currentTime > lastTurnTime + 500) {
            targetRotation = randomDirection(targetRotation);

                lastTurnTime = currentTime;
                HasChangedDirection = true;
            } else {
                HasChangedDirection = false;
            }

            if (engine.gamepad1.right_bumper) {
                if (!HasSetDirection) {
                    targetRotation = getJoystickDegrees();
                    HasSetDirection = true;
                }
            } else {
                HasSetDirection = false;
            }

            if (distanceToObject < reverseThreshold) {

                DriveLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                DriveRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                DriveLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                DriveRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

                DriveRight.setPower(-HighPower);
                DriveLeft.setPower(-HighPower);
                Reverseing = true;
            }
        }

        if (Reverseing && Math.abs(DriveRight.getCurrentPosition()) > ReverseTicks) {
            targetRotation = randomDirection(targetRotation);
//            targetRotation += 100;
            Reverseing = false;
        }

    }

    @Override
    public void telemetry() {
        engine.telemetry.addData("Distance Sensor", DistanceSensor.getDistance(DistanceUnit.CM));
        engine.telemetry.addData("target Rotation", targetRotation);
        engine.telemetry.addData("Rotation Difference", relativeRotation);
        engine.telemetry.addData("Speed Adjust", speedAdjust);
        engine.telemetry.addData("Left Motor Power", DriveLeft.getPower());
        engine.telemetry.addData("Right Motor Power", DriveRight.getPower());
    }

    private float randomDirection(float currentRotatoin) {
        float randomRotation = (float) ((Math.random() -.5) * 160);
        if (randomRotation > 0) {
            randomRotation += 100;
        } else {
            randomRotation -= 100;
        }
        float adjustedRotation = currentRotatoin + randomRotation;

        if (adjustedRotation > 180) {
            adjustedRotation -= 360;
        }
        if (adjustedRotation < -180) {
            adjustedRotation += 360;
        }

        return adjustedRotation;
    }

    private float getJoystickDegrees() {

        double left_stick_y = -engine.gamepad1.left_stick_y;
        double left_stick_x = engine.gamepad1.left_stick_x;
        float zeroToNinety = (float) Math.toDegrees(Math.atan(left_stick_x / left_stick_y));

        engine.telemetry.addData("raw", zeroToNinety);
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
