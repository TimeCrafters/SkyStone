package org.timecrafters.Summer_2020.Faith;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.engine.Engine;
@Autonomous(name = "Faith Drive", group = "Faith")
public class DriveForwardEngine extends Engine {

    @Override
    public void setProcesses() {
        StateConfiguration stateConfiguration = new StateConfiguration();
        addState(new DriveForwardState(this, stateConfiguration, "DriveStateVroom"));
        addState(new DriveForwardState(this,stateConfiguration,"DriveBackwardsVroom") );
    }


}

