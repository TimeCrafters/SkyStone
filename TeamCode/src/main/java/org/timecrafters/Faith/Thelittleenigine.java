package org.timecrafters.Faith;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.timecrafters.engine.Engine;

@Autonomous (name = "Crane Thingy Test")
public class Thelittleenigine extends Engine {
    @Override
    public void setProcesses() {
        addState(new CraneState(this));
        addState(new CraneState(this));
    }
}
