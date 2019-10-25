package org.timecrafters.SkyStone_2019_2020.Autonomos.Engines;

import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.Fingers;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.RampDrive;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.Face;
import org.timecrafters.engine.Engine;

public class R1 extends Engine {
    @Override
    public void setProcesses() {
        StateConfiguration stateConfiguration = new StateConfiguration();
        addState(new RampDrive(this, stateConfiguration, "R1a"));
        addState(new RampDrive(this, stateConfiguration, "R1b"));
        addState(new Fingers(this, stateConfiguration, "R1c"));
        addState(new Face(this, stateConfiguration, "R1d"));
        addState(new Fingers(this, stateConfiguration, "R1d"));
        addState(new RampDrive(this, stateConfiguration, "R1f"));
    }
}
