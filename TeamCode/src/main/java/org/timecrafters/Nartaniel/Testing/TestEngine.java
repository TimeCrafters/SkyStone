package org.timecrafters.Nartaniel.Testing;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.SkystoneSight;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.TurnToSkyStone;
import org.timecrafters.SkyStone_2019_2020.IMUInit;
import org.timecrafters.engine.Engine;

@TeleOp (name = "Testing Ground", group = "Testing")
public class TestEngine extends Engine {
    @Override
    public void setProcesses() {
        StateConfiguration stateConfiguration = new StateConfiguration();
        //addState(new IMUInit(this));
        SkystoneSight skystoneSight = new SkystoneSight(this, stateConfiguration, "Exist");
        addState(skystoneSight);

    }
}
