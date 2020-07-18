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
    public BNO055IMU imu = hardwareMap.get(BNO055IMU.class, "imu");;
    public DcMotor motorDriveLeft = hardwareMap.dcMotor.get("rightDrive");
    public DcMotor motorDriveRight = hardwareMap.dcMotor.get("leftDrive");
    public Rev2mDistanceSensor sensorDistanceLeft = hardwareMap.get(Rev2mDistanceSensor.class,"distanceLeft");
    public Rev2mDistanceSensor sensorDistanceRight = hardwareMap.get(Rev2mDistanceSensor.class, "distanceRight");


    public void initHardware() {

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

        motorDriveLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        motorDriveLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorDriveRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

    }


}
