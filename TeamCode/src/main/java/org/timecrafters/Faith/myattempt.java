package org.timecrafters.Faith;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.timecrafters.engine.Engine;
@Autonomous(name = "practice round 1")
public class myattempt extends Engine {
    @Override
    public void setProcesses() {
        addState(new Practico(this));
    }
}
