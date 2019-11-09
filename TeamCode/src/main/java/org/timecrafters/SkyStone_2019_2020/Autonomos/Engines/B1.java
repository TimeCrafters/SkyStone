package org.timecrafters.SkyStone_2019_2020.Autonomos.Engines;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.Face;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.Fingers;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.RampDrive;
import org.timecrafters.SkyStone_2019_2020.IMUInit;
import org.timecrafters.engine.Engine;

@Autonomous (name = "B1", group = "B")
public class B1 extends Engine {
    @Override
    public void setProcesses() {
        StateConfiguration stateConfiguration = new StateConfiguration();

        addState(new IMUInit(this));
        addState(new RampDrive(this, stateConfiguration, "B1a"));
        addState(new RampDrive(this, stateConfiguration, "B1b"));
        addState(new Fingers(this, stateConfiguration, "B1c"));
        addState(new Face(this, stateConfiguration, "B1d"));
        addState(new RampDrive(this, stateConfiguration, "B1e"));
        addState(new Fingers(this, stateConfiguration, "B1f"));
        addState(new RampDrive(this, stateConfiguration, "B1g"));
        addState(new Face(this, stateConfiguration, "B1h"));
    }
}
