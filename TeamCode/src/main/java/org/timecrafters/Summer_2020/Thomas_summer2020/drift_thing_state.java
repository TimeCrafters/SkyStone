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
private BNO055IMU imu;

    public drift_thing_state(Engine engine, StateConfiguration stateconfig,String stateconfigID) {
this.stateconfigID=stateconfigID;
this.stateconfig=stateconfig;
        this.engine=engine;
    }



    public void init() {
leftmotor=engine.hardwareMap.dcMotor.get("leftDrive");
rightmotor=engine.hardwareMap.dcMotor.get("rightDrive");
rightmotor.setDirection(DcMotorSimple.Direction.REVERSE);

muchpower=stateconfig.get(stateconfigID).variable("power");
       ticks=stateconfig.get(stateconfigID).variable("ticks");
       Bfirstrun = true;
    }

    @Override
    public void exec() {
if (Bfirstrun){
    rightmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    leftmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    rightmotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    leftmotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    Bfirstrun=false;

}
if (Math.abs( rightmotor.getCurrentPosition()) > ticks ) {
    rightmotor.setPower(0);
    leftmotor.setPower(0);
    setFinished(true);
}

    }



}
