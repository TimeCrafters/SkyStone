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

        addState(new drift_thing_state(this, stateConfiguration, "ThomasStraitForward1"));
        addState(new stateTurnInPlace(this, "ThomasTurn1", stateConfiguration));

        addState(new drift_thing_state(this, stateConfiguration, "ThomasStraitForward2"));
        addState(new stateTurnInPlace(this, "ThomasTurn2", stateConfiguration));

        addState(new drift_thing_state(this, stateConfiguration, "ThomasStraitForward3"));
        addState(new stateTurnInPlace(this, "ThomasTurn3", stateConfiguration));

        addState(new drift_thing_state(this, stateConfiguration, "ThomasStraitForward4"));
        addState(new stateTurnInPlace(this, "ThomasTurn4", stateConfiguration));

        addState(new drift_thing_state(this, stateConfiguration, "ThomasStraitForward5"));
        addState(new stateTurnInPlace(this, "ThomasTurn5", stateConfiguration));

        addState(new drift_thing_state(this, stateConfiguration, "ThomasStraitForward6"));
        addState(new stateTurnInPlace(this, "ThomasTurn6", stateConfiguration));

        addState(new drift_thing_state(this, stateConfiguration, "ThomasStraitForward7"));

    }
}
