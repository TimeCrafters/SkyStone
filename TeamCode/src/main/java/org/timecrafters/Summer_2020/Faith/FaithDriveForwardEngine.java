package org.timecrafters.Summer_2020.Faith;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.engine.Engine;
@Autonomous(name = "Faith Drive", group = "Faith")
public class FaithDriveForwardEngine extends Engine {

    @Override
    public void setProcesses() {
        StateConfiguration stateConfiguration = new StateConfiguration();
        addState(new FaithDriveForwardState(this, stateConfiguration, "DriveStateVroom"));
      //  addState(new FaithDriveForwardState(this,stateConfiguration,"DriveBackwardsVroom") );
    }


}

