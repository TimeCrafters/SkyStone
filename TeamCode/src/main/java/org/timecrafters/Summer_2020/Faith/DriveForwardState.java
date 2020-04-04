package org.timecrafters.Summer_2020.Faith;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

import java.util.IllegalFormatCodePointException;

public class DriveForwardState extends State {

private DcMotor DriveLeft;
private DcMotor DriveRight;
private double Power;
private int Ticks;
private StateConfiguration StateConfig;
private boolean FirstRun;
private String StateConfigID;
private BNO055IMU IMU;
private float CurrentRotation;

    public DriveForwardState(Engine Engine, StateConfiguration stateConfiguration, String stateConfigID) {
        this.engine = Engine;
        StateConfig = stateConfiguration;
        StateConfigID = stateConfigID;
    }

    @Override
    public void init() {
DriveLeft = engine.hardwareMap.dcMotor.get("rightDrive");
DriveRight = engine.hardwareMap.dcMotor.get("leftDrive");
DriveRight.setDirection(DcMotorSimple.Direction.REVERSE);
Power = StateConfig.get(StateConfigID).variable("Power");
Ticks = StateConfig.get(StateConfigID).variable("Distance");
FirstRun = true;

        IMU = engine.hardwareMap.get(BNO055IMU.class, "imu");

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();

        parameters.mode = BNO055IMU.SensorMode.IMU;
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.loggingEnabled = false;

        IMU.initialize(parameters);
    }

    @Override
    public void exec() throws InterruptedException {
if (FirstRun){
    DriveRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    DriveLeft. setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    DriveRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    DriveLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    FirstRun = false;
}
CurrentRotation = IMU.getAngularOrientation().firstAngle;

        DriveLeft.setPower(Power);
        DriveRight.setPower(Power);

        if (CurrentRotation > 0 ){
            DriveLeft.setPower(Power-.1);
            DriveRight.setPower(Power+.1);
        }

        if (CurrentRotation < 0 ){
            DriveLeft.setPower(Power+.1);
            DriveRight.setPower(Power-.1);
        }

        if (CurrentRotation == 0){
            DriveLeft.setPower(Power);
            DriveRight.setPower(Power);
        }


        if (Math.abs( DriveRight.getCurrentPosition())>= Ticks){
            DriveRight.setPower(0);
            DriveLeft.setPower(0);
            setFinished(true);
        }

    }


}
