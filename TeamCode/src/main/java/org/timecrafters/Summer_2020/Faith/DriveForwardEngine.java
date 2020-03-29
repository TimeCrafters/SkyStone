package org.timecrafters.Summer_2020.Faith;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.timecrafters.Nartaniel.Training.DriveForward;
import org.timecrafters.engine.Engine;
@Autonomous(name = "Faith Drive", group = "Faith")
public class DriveForwardEngine extends Engine {

    @Override
    public void setProcesses() {
addState(new DriveForwardState(this));
    }


}

