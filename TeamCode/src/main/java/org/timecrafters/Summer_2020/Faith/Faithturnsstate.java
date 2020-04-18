package org.timecrafters.Summer_2020.Faith;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class Faithturnsstate extends State {

    private DcMotor Leftdrive;
    private DcMotor Rightdrive;
    private double Power;
    private float Degrees;
    private StateConfiguration StateConfig;
    private String StateConfigID;
    private boolean Firstrun;
    private int Direction;
    private BNO055IMU IMU;
    private float Currentposition;

    public Faithturnsstate(Engine engine, StateConfiguration stateConfiguration, String StateConfigID) {
        this.engine = engine;
        this. StateConfig = stateConfiguration;
        this. StateConfigID = StateConfigID;
    }

    @Override
    public void init() {
        Leftdrive = engine.hardwareMap.dcMotor.get("leftDrive");
        Rightdrive = engine.hardwareMap.dcMotor.get("rightDrive");
        Rightdrive.setDirection(DcMotorSimple.Direction.REVERSE);
        Power = StateConfig.get(StateConfigID).variable("power");
        Degrees = StateConfig.get(StateConfigID).variable("degrees");
        Firstrun = true;



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

        Currentposition = IMU. getAngularOrientation().firstAngle;

        if (Firstrun){
            Leftdrive.setPower(-Power * Direction);
            Rightdrive.setPower(Power * Direction);
            Firstrun = false;
        }

        if (Currentposition > Degrees - 5 && Currentposition < Degrees + 5 ) {
            Rightdrive.setPower(0);
            Leftdrive.setPower(0);
            setFinished(true);
        }
    }

    @Override
    public void telemetry() {
        engine.telemetry.addData("Current Rotation", Currentposition);
    }
}
