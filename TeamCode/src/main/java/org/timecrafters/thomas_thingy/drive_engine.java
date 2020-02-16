package org.timecrafters.thomas_thingy;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;
import org.timecrafters.thomas_thingy.state.driveforward;
@Autonomous(name = "drivething")
public class drive_engine extends Engine {


    @Override
    public void setProcesses() {
        StateConfiguration stateConfiguration = new StateConfiguration();
        addState(new driveforward(this,stateConfiguration,"TF"));
        addState(new armstate(this,stateConfiguration,"TF"));
    }
}

