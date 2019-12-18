package org.timecrafters.Faith;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.engine.Engine;
@Autonomous(name = "Drive")
public class AutonomousEngine extends Engine {

    @Override
    public void setProcesses() {
        StateConfiguration stateConfiguration = new StateConfiguration();
        addState(new AutomomousState(this,stateConfiguration,"TF"));

    }
}
