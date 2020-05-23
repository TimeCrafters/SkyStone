package org.timecrafters.Summer_2020.Nathaniel;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.cyberarm.engine.V2.CyberarmStateV2;

import java.util.ArrayList;

public class PositionTracking extends CyberarmStateV2 {

    private DcMotor DriveLeft;
    private DcMotor DriveRight;
    private BNO055IMU IMU;
    private int EncoderDiffLeft;
    private int EncoderDiffRight;
    private int EncoderPrevLeft;
    private int EncoderPrevRight;
    private float RotationCurrent;
    private ArrayList<NRPPosition> Positions = new ArrayList<NRPPosition>();


    @Override
    public void init() {
        DriveLeft = cyberarmEngine.hardwareMap.dcMotor.get("leftDrive");
        DriveRight = cyberarmEngine.hardwareMap.dcMotor.get("rightDrive");
        DriveRight.setDirection(DcMotorSimple.Direction.REVERSE);

        IMU = cyberarmEngine.hardwareMap.get(BNO055IMU.class, "imu");

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();

        parameters.mode = BNO055IMU.SensorMode.IMU;
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.loggingEnabled = false;

        IMU.initialize(parameters);

    }

    @Override
    public void start() {
        DriveLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        DriveRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        DriveLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        DriveRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        Positions.add(new NRPPosition(0, 0, 0));
        EncoderPrevLeft = 0;
        EncoderPrevRight = 0;
    }

    @Override
    public void exec() {
        RotationCurrent = IMU.getAngularOrientation().firstAngle;
        int encoderCurrentRight = DriveRight.getCurrentPosition();
        int encoderCurrentLeft = DriveLeft.getCurrentPosition();

        DriveLeft.setPower(cyberarmEngine.gamepad1.left_stick_y);
        DriveRight.setPower(cyberarmEngine.gamepad1.right_stick_y);

        if (encoderCurrentLeft != EncoderPrevLeft || encoderCurrentRight != EncoderPrevRight ) {
            EncoderDiffLeft = encoderCurrentLeft - EncoderDiffLeft;
            EncoderDiffRight = encoderCurrentRight - EncoderDiffRight;


        }
    }
}
