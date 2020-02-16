package org.timecrafters.thomas_thingy;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class armstate extends State {
    private DcMotor arminout;
    private StateConfiguration statecon;
    private String conid;
    private double power;
    private int ticks;
    private boolean hasrun;

    public armstate(Engine engine, StateConfiguration stateConfiguration, String conid) {

        this.engine=engine;
        statecon=stateConfiguration;
        this.conid=conid;


    }

    @Override
    public void init() {
        arminout=engine.hardwareMap.dcMotor.get("craneY");
        power=statecon.get(conid).variable("power");
        ticks=statecon.get(conid).variable("ticks");
    }

    @Override
    public void exec() throws InterruptedException {
        if (!hasrun) {
        arminout.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arminout.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        hasrun = true;
    }

        if (Math.abs(arminout.getCurrentPosition()) <ticks) {

            arminout.setPower(power);


        }else {
            arminout.setPower(0);
            setFinished(true);
        }

    }

}
