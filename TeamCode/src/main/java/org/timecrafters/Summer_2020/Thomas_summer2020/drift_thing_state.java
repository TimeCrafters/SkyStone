package org.timecrafters.Summer_2020.Thomas_summer2020;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class drift_thing_state  extends State {

    private DcMotor leftmotor;
    private DcMotor rightmotor;
    private boolean Bfirstrun;
    private StateConfiguration stateconfig;
private String stateconfigID;
private double muchpower;
private int ticks;
private BNO055IMU IMU;
private float fristA;
private float CA;
private long startT;
private long Ctime;
private long acctime;
private double accamount;
private double targetpower;

    public drift_thing_state(Engine engine, StateConfiguration stateconfig,String stateconfigID) {
this.stateconfigID=stateconfigID;
this.stateconfig=stateconfig;
        this.engine=engine;
    }



    public void init() {
leftmotor=engine.hardwareMap.dcMotor.get("leftDrive");
rightmotor=engine.hardwareMap.dcMotor.get("rightDrive");
leftmotor.setDirection(DcMotorSimple.Direction.REVERSE);

muchpower=stateconfig.get(stateconfigID).variable("power");
       ticks=stateconfig.get(stateconfigID).variable("ticks");
       Bfirstrun = true;
        IMU = engine.hardwareMap.get(BNO055IMU.class, "imu");

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();

        parameters.mode = BNO055IMU.SensorMode.IMU;
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.loggingEnabled = false;

        IMU.initialize(parameters);

        acctime=   stateconfig.get(stateconfigID).variable("acceltime");
accamount= targetpower/acctime;
    }

    @Override
    public void exec() {
        float sensorR = IMU.getAngularOrientation().firstAngle;
        double powercor;
        powercor=CA*0.0056;
if (Bfirstrun){
    rightmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    leftmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    rightmotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    leftmotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    Bfirstrun=false;
    startT  = System.currentTimeMillis();

}

if (muchpower < targetpower){
    muchpower = accamount*Ctime;

}

Ctime = System.currentTimeMillis()-startT;
CA=fristA-sensorR;
leftmotor.setPower(muchpower-powercor);
        rightmotor.setPower(muchpower+powercor);
if (Math.abs( rightmotor.getCurrentPosition()) > ticks ) {
    rightmotor.setPower(0);
    leftmotor.setPower(0);
    setFinished(true);
}
engine.telemetry.addData("curentA",CA);
engine.telemetry.update();
    }



}
