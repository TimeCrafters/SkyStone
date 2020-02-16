package org.timecrafters.thomas_thingy;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.timecrafters.engine.Engine;

@TeleOp(name = "thomasthing")
public class engine_class extends Engine {
    @Override
    public void setProcesses() {

        addState(new state_thingyprt3(this,5000,.225));

        addState(new state_thingy(this,-1,7000));
        addState(new state_prt2(this, 1, 5000));

        addState(new state_thingy(this,0,1000));
        addState(new state_thingyprt3(this,0,0));
    }
}
