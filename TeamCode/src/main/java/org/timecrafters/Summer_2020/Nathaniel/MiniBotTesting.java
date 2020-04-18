package org.timecrafters.Summer_2020.Nathaniel;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.cyberarm.engine.V2.CyberarmEngineV2;
import org.cyberarm.engine.V2.CyberarmStateV2;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class MiniBotTesting extends CyberarmStateV2 {

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


    public MiniBotTesting(double highPower, double lowPower, double cmThreshold, double reverseThreshold , int reverseTicks) {
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

        //Distance Sensor

        DistanceSensor = cyberarmEngine.hardwareMap.get(Rev2mDistanceSensor.class, "forwardDistance");
    }

    @Override
    public void exec() {


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

            double bacePower = ((HighPower - LowPower)*(Math.pow(1.05, -Math.abs(relativeRotation)))) + LowPower;

            //Adjusts power based on degrees off from target.
            double leftPower = bacePower - (relativeRotation * .01);
            double rightPower = bacePower + (relativeRotation * .01);

            //calculates speed adjuster so
            speedAdjust = ((2 * bacePower) / (Math.abs(leftPower) + Math.abs(rightPower)));

            DriveLeft.setPower(leftPower * speedAdjust);
            DriveRight.setPower(rightPower * speedAdjust);

            long currentTime = System.currentTimeMillis();

            if (distanceToObject < avoidThreshold && distanceToObject > reverseThreshold && !HasChangedDirection && currentTime > lastTurnTime + 1500) {
            targetRotation = randomDirection(targetRotation);

                lastTurnTime = currentTime;
                HasChangedDirection = true;
            } else {
                HasChangedDirection = false;
            }

            if (cyberarmEngine.gamepad1.right_bumper) {
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
        cyberarmEngine.telemetry.addData("Rotaion X", IMU.getAngularOrientation().firstAngle);
        cyberarmEngine.telemetry.addData("Rotaion Y", IMU.getAngularOrientation().secondAngle);
        cyberarmEngine.telemetry.addData("Rotaion Z", IMU.getAngularOrientation().thirdAngle);
        cyberarmEngine.telemetry.addData("Distance Sensor", DistanceSensor.getDistance(DistanceUnit.CM));
        cyberarmEngine.telemetry.addData("target Rotation", targetRotation);
        cyberarmEngine.telemetry.addData("Rotation Difference", relativeRotation);
        cyberarmEngine.telemetry.addData("Speed Adjust", speedAdjust);
        cyberarmEngine.telemetry.addData("Left Motor Power", DriveLeft.getPower());
        cyberarmEngine.telemetry.addData("Right Motor Power", DriveRight.getPower());
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
