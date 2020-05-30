package org.timecrafters.Summer_2020.Thomas_summer2020;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class drift_thing_state extends State {

    private DcMotor leftmotor;
    private DcMotor rightmotor;
    private boolean Bfirstrun;
    private StateConfiguration stateconfig;
private String stateconfigID;
private double muchpower;
private int ticks;
private BNO055IMU IMU;
private float firstA;
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

targetpower=stateconfig.get(stateconfigID).variable("power");
       double disance = stateconfig.get(stateconfigID).variable("cm");
       Bfirstrun = true;

        ticks=(int) ((560/(Math.PI*11.5))*disance);
        ticks = 1000;

        IMU = engine.hardwareMap.get(BNO055IMU.class, "imu");

       // BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();

     //   parameters.mode = BNO055IMU.SensorMode.IMU;
       // parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
       // parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        //parameters.loggingEnabled = false;

        //IMU.initialize(parameters);

        acctime=   stateconfig.get(stateconfigID).variable("acceltime");
        accamount= targetpower/acctime;
    }

    @Override
    public void exec() {
        if (stateconfig.allow(stateconfigID)) {

            float sensorR = IMU.getAngularOrientation().firstAngle;

            if (Bfirstrun) {
                rightmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                leftmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                rightmotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                leftmotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                Bfirstrun = false;
                startT = System.currentTimeMillis();
                firstA = sensorR;
            }

            if (muchpower < targetpower) {
                muchpower = accamount * Ctime;

            }

            Ctime = System.currentTimeMillis() - startT;
            CA = sensorR-firstA ;

                if (CA<-180){
                CA+=360;
            }
            if (CA>180){
                CA-=360;
            }


            double powercor = CA * 0.012;
            leftmotor.setPower(muchpower + powercor);
            rightmotor.setPower(muchpower - powercor);

            if (Math.abs(rightmotor.getCurrentPosition()) > ticks) {
                rightmotor.setPower(0);
                leftmotor.setPower(0);
                setFinished(true);
            }
            engine.telemetry.addData("curentA", CA);
            engine.telemetry.update();
        } else {
            setFinished(true);
        }

    }

}
