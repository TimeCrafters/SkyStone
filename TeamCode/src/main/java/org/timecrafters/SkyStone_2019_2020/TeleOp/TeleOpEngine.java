package org.timecrafters.SkyStone_2019_2020.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.timecrafters.SkyStone_2019_2020.IMUInit;
import org.timecrafters.engine.Engine;

@TeleOp (name = "TeleOp", group = "SkyStone")
public class TeleOpEngine extends Engine {

    @Override
    public void setProcesses() {
        addState(new IMUInit(this));
        addState(new TeleOpState(this));
    }
}
