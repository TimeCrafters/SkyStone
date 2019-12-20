package org.timecrafters.Faith;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class Cranestateauto extends State {

    private StateConfiguration StateConfig;
    private String ID;
    private DcMotor Side;
    private double Power;
    private int Ticks;
    private boolean HasRun;

    public Cranestateauto(Engine engine, StateConfiguration stateConfig, String ID) {
        this.engine = engine;
        StateConfig = stateConfig;
        this.ID = ID;
    }

    @Override
    public void init() {
     Side = engine.hardwareMap.dcMotor.get("craneX");
     Ticks = StateConfig.get(ID).variable("ticks");
     Power = StateConfig.get(ID).variable("power");
    }

    @Override
    public void exec() throws InterruptedException {
        if (! HasRun) {
            Side.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            Side.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            HasRun = true;
        }
        if (Math.abs(Side.getCurrentPosition())<Ticks){
            Side.setPower(Power);
        }else {
            Side.setPower(0);
            setFinished(true);
        }
    }
}
