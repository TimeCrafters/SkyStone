package org.timecrafters.thomas_thingy;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.timecrafters.SkyStone_2019_2020.IMUInit;
import org.timecrafters.SkyStone_2019_2020.TeleOp.TeleOpState;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

@TeleOp(name = "ee237745")
public class minibotengine extends Engine {
    @Override
    public void setProcesses() {
addState(new ee213345(this));
       // addState(new minibotstate(this));

    }
}
