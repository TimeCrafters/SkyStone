package org.timecrafters.Summer_2020.Thomas_summer2020;

import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.engine.Engine;

public class drift_thing_engine extends Engine {
    @Override
    public void setProcesses() {
        StateConfiguration stateConfiguration= new StateConfiguration();
        addState(new drift_thing_state(this,stateConfiguration,"ThomasStraitforward"));
    }
}
