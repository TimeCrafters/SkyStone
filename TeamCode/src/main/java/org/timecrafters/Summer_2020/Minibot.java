package org.timecrafters.Summer_2020;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.cyberarm.NeXT.StateConfiguration;

public class Minibot {

    private HardwareMap hardwareMap;

    public Minibot(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;
    }

    public StateConfiguration stateConfiguration = new StateConfiguration();
    public BNO055IMU imu;
    public DcMotor motorDriveLeft;
    public DcMotor motorDriveRight;
    public Rev2mDistanceSensor sensorDistanceLeft;
    public Rev2mDistanceSensor sensorDistanceRight;


    public void initHardware() {

        imu  = hardwareMap.get(BNO055IMU.class, "imu");
        motorDriveLeft = hardwareMap.dcMotor.get("rightDrive");
        motorDriveRight = hardwareMap.dcMotor.get("leftDrive");
        sensorDistanceLeft = hardwareMap.get(Rev2mDistanceSensor.class,"distanceLeft");
        sensorDistanceRight = hardwareMap.get(Rev2mDistanceSensor.class, "distanceRight");

        //initialize IMU
        //-------------------------------------------------------------------------------------
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();

        parameters.mode = BNO055IMU.SensorMode.IMU;
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.loggingEnabled = false;

        imu.initialize(parameters);

        //initialize drive motors
        //-------------------------------------------------------------------------------------

        motorDriveRight.setDirection(DcMotorSimple.Direction.REVERSE);

        motorDriveLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorDriveRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

    }

    public float getRelativeAngle(float reference, float current) {
        float relative = current - reference;

        if (relative < -180) {
            relative += 360;
        }

        if (relative > 180) {
            relative -= 360;
        }
        return relative;
    }

    public int getTicksFromCentimeters(double cm) {
        return (int) ((560/(Math.PI*11.5))*cm);
    }

    //Calculates the power the left and right drive motors should be adjusted so that the robot
    //steers towards the target power. A smaller angle between the targeted rotation and the robot's,
    //current rotation result in smaller correction. Any over correction results in oscillations.

}
