package org.timecrafters.Summer_2020.Thomas_summer2020;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.engine.Engine;
@TeleOp (name = "12345")
public class drift_thing_engine extends Engine {



    @Override
    public void setProcesses() {
        StateConfiguration stateConfiguration= new StateConfiguration();
        addState(new drift_thing_state(this, stateConfiguration, "ThomasStraitForward"));
        addState(new stateTurnInPlace(this, "ThomasTurn", stateConfiguration));
    }
}
