package org.timecrafters.SkyStone_2019_2020.Autonomos.Engines;

import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.Face;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.Fingers;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.RampDrive;
import org.timecrafters.engine.Engine;

public class B1 extends Engine {
    @Override
    public void setProcesses() {
        StateConfiguration stateConfiguration = new StateConfiguration();
        addState(new RampDrive(this, stateConfiguration, "B1a"));
        addState(new RampDrive(this, stateConfiguration, "B1b"));
        addState(new Fingers(this, stateConfiguration, "B1c"));
        addState(new Face(this, stateConfiguration, "B1d"));
        addState(new Fingers(this, stateConfiguration, "B1e"));
        addState(new RampDrive(this, stateConfiguration, "B1f"));
    }
}