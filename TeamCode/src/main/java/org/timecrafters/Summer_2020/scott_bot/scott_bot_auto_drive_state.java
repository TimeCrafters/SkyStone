package org.timecrafters.Summer_2020.scott_bot;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class scott_bot_auto_drive_state extends State {

    public scott_bot_auto_drive_state(Engine engine) { this.engine = engine;}

    /* wheels */
    private DcMotor pLeftDrive;
    private DcMotor pRightDrive;
    private double dLeftDirection;  // -1 = backwards, 0 = stop, 1 = forward
    private double dRightDirection; // -1 = backwards, 0 = stop, 1 = forward
    private int iDriveState; // 0 = forward, 1 = back off, 2 = rotate

    /* distance sensor */
    private Rev2mDistanceSensor pFrontDistance;
    private double dFrontDistance; // measured in mm

    /* IMU */
    private BNO055IMU pIMU;
    private double dIMU_Angle; /* value between 0 and 2 (counter clockwise) */
    private double dIMU_AngleTarget; /* where are we going to */


    @Override
    public void init() {
        pLeftDrive = engine.hardwareMap.dcMotor.get("LeftWheel");
        pRightDrive = engine.hardwareMap.dcMotor.get("RightWheel");
        pFrontDistance = engine.hardwareMap.get(Rev2mDistanceSensor.class, "FrontDistance");


        pIMU = engine.hardwareMap.get(BNO055IMU.class, "imu");
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();

        parameters.mode = BNO055IMU.SensorMode.IMU;
        parameters.angleUnit = BNO055IMU.AngleUnit.RADIANS;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.loggingEnabled = false;

        pIMU.initialize(parameters);

        pLeftDrive.setDirection(DcMotor.Direction.FORWARD);
        pRightDrive.setDirection(DcMotor.Direction.REVERSE);
        dLeftDirection = 0;
        dRightDirection = 0;

        iDriveState = 0;
    }

    @Override
    public void exec() throws InterruptedException {

        pLeftDrive.setPower(dLeftDirection*1);
        pRightDrive.setPower(dRightDirection*1);
        dFrontDistance = pFrontDistance.getDistance(DistanceUnit.MM);
        dIMU_Angle = pIMU.getAngularOrientation().firstAngle / 3.141592653589793;
        if(dIMU_Angle<0){
            dIMU_Angle+=2;
        }

        engine.telemetry.addData("distance", dFrontDistance);
        engine.telemetry.addData("angle", dIMU_Angle);
        engine.telemetry.update();

        switch (iDriveState) {
            case 0:
                if (dFrontDistance > 300) {
                    dLeftDirection = .75;
                    dRightDirection = .75;

                } else {
                    iDriveState = 1;
                }
                break;

            case 1:
                if (dFrontDistance < 600) {
                     dLeftDirection = -.5;
                    dRightDirection = -.5;

                } else {
                    iDriveState = 2;
                    dIMU_AngleTarget = dIMU_Angle + .5;
                    if(dIMU_AngleTarget > 2){
                        dIMU_AngleTarget-=2;
                    }
                }
                break;

            case 2:
                if (    dIMU_Angle >= dIMU_AngleTarget -.1 &&
                        dIMU_Angle <= dIMU_AngleTarget +.1) {
                    iDriveState = 0;
                } else {
                    dLeftDirection = -.3;
                    dRightDirection = .3;

                }
                break;

        }

        pLeftDrive.setPower(dLeftDirection*1);
        pRightDrive.setPower(dRightDirection*1);

    }
}


