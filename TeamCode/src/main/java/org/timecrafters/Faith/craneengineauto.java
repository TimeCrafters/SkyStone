package org.timecrafters.Faith;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.engine.Engine;
@Autonomous (name = "gogo")
public class craneengineauto extends Engine {
    @Override
    public void setProcesses() {
        StateConfiguration stateConfiguration = new StateConfiguration();
        addState(new Cranestateauto(this, stateConfiguration, "TF"));
    }
}
