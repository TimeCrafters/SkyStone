package org.timecrafters.Summer_2020.Thomas_summer2020;


import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.engine.Engine;


@TeleOp (name = "KidDrive")
public class engine extends Engine {

    @Override
    public void setProcesses() {
        addState(new state(this));
    }
}
