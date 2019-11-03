package org.timecrafters.SkyStone_2019_2020.Autonomos.Engines;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.timecrafters.SkyStone_2019_2020.Autonomos.States.TurnTesting;
import org.timecrafters.SkyStone_2019_2020.IMUInit;
import org.timecrafters.engine.Engine;

@TeleOp(name = "Turn Testing",group = "Testing")
public class TurnTest extends Engine {
    @Override
    public void setProcesses() {
        addState(new IMUInit(this));
        addState(new TurnTesting(this));
    }
}
