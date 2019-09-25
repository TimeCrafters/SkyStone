package org.timecrafters.SkyStone_2019_2020;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.timecrafters.engine.State;

public class Drive extends State {

    public DcMotor DriveForwardLeft;
    public DcMotor DriveForwardRight;
    public DcMotor DriveBackLeft;
    public DcMotor DriveBackRight;
    private BNO055IMU IMU;
    private Orientation StartOrientation;

    public void init() {

        DriveForwardLeft = engine.hardwareMap.dcMotor.get("forwardLeftDrive");
        DriveForwardRight = engine.hardwareMap.dcMotor.get("forwardRightDrive");
        DriveBackLeft = engine.hardwareMap.dcMotor.get("forwardLeftDrive");
        DriveBackRight = engine.hardwareMap.dcMotor.get("forwardRightDrive");

        IMU = engine.hardwareMap.get(BNO055IMU.class, "imu");

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();

        parameters.mode = BNO055IMU.SensorMode.IMU;
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.loggingEnabled = false;

        IMU.initialize(parameters);

        IMU.initialize(parameters);
        while (!IMU.isGyroCalibrated()) {
            sleep(10);
        }

        engine.telemetry.addLine("IMU Calibrated...");
        engine.telemetry.update();

        setStartOrientation();

    }

    @Override
    public void exec() throws InterruptedException {

    }

    //The forward-left and backward-right motors need the same powers and the forward-right and
    //backward-left need the same power.

    //positive degrees are towards the right (clockwise), negative degrees are towards the left
    //(counterclockwise).

    public double getForwardLeftPower(double directionDegrees, double zeroPowerThreshold) {

        return degreesToPower(45, directionDegrees, zeroPowerThreshold);
    }

    public double getForwardRightPower(double directionDegrees, double zeroPowerThreshold) {

        return degreesToPower(135, directionDegrees, zeroPowerThreshold);

    }

    private double degreesToPower(int translation, double directionDegrees, double zeroPowerThreshold) {

        double power = 1.415 * (Math.sin(Math.toRadians(directionDegrees + translation)));

        if (power > 1) {power = 1;}
        if (power < -1) {power = -1;}

        if (power < zeroPowerThreshold && power > -zeroPowerThreshold) {power = 0;}

        return power;
    }

    public void setStartOrientation() {
        StartOrientation = IMU.getAngularOrientation();
    }

    public float getRobotRotation() {
        Orientation currentOrientation = IMU.getAngularOrientation();
        Orientation startOrientation = StartOrientation;

        return currentOrientation.firstAngle - startOrientation.firstAngle;
    }


}
