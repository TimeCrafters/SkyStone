package org.timecrafters.Summer_2020.Faith;

import org.timecrafters.Nartaniel.Training.DriveForward;
import org.timecrafters.engine.Engine;

public class DriveForwardEngine extends Engine {



    @Override
    public void setProcesses() {
addState(new DriveForwardState(this));
    }


}

