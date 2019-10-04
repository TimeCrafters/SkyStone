package org.timecrafters.SkyStone_2019_2020;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

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
        DriveBackLeft = engine.hardwareMap.dcMotor.get("backLeftDrive");
        DriveBackRight = engine.hardwareMap.dcMotor.get("backRightDrive");

        DriveForwardRight.setDirection(DcMotorSimple.Direction.REVERSE);
        DriveBackRight.setDirection(DcMotorSimple.Direction.REVERSE);

        DriveForwardLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        DriveForwardRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        DriveBackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        DriveBackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        IMU = engine.hardwareMap.get(BNO055IMU.class, "imu");

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
