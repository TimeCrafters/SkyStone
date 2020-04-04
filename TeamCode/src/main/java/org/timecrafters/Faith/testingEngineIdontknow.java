package org.timecrafters.Faith;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.timecrafters.engine.Engine;
@Autonomous (name = "yeeet")
public class testingEngineIdontknow extends Engine {
    @Override
    public void setProcesses() {
        addState(new Anythingyouwantstate(this));
    }
}
