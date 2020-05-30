package org.timecrafters.Summer_2020.Thomas_summer2020;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.SkyStone_2019_2020.IMUInit;
import org.timecrafters.engine.Engine;
@Autonomous(name = "Thomas: Autonomous States",group = "Thomas")
public class drift_thing_engine extends Engine {



    @Override
    public void setProcesses() {
        StateConfiguration stateConfiguration= new StateConfiguration();
        addState(new IMUInit(this));

        addState(new drift_thing_state(this, stateConfiguration, "ThomasStraitForward"));
        addState(new stateTurnInPlace(this, "ThomasTurn1", stateConfiguration));

        addState(new drift_thing_state(this, stateConfiguration, "ThomasStraitForward"));
        addState(new stateTurnInPlace(this, "ThomasTurn2", stateConfiguration));

        addState(new drift_thing_state(this, stateConfiguration, "ThomasStraitForward"));
        addState(new stateTurnInPlace(this, "ThomasTurn3", stateConfiguration));

        addState(new drift_thing_state(this, stateConfiguration, "ThomasStraitForward"));
        addState(new stateTurnInPlace(this, "ThomasTurn4", stateConfiguration));

    }
}
