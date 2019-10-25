package org.timecrafters.SkyStone_2019_2020.Autonomos.Engines;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.TurnTesting;
import org.timecrafters.SkyStone_2019_2020.IMUInit;
import org.timecrafters.engine.Engine;

@Autonomous (name = "Square",group = "Testing")
public class Test extends Engine {
    @Override
    public void setProcesses() {
        StateConfiguration StateConfig = new StateConfiguration();
        addState(new IMUInit(this));
        addState(new TurnTesting(this));
    }
}
