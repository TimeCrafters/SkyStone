package org.timecrafters.Faith;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.timecrafters.engine.Engine;
@TeleOp (name = " First go robo")
public class TeleOpEngine extends Engine {
    @Override
    public void setProcesses() {
        addState(new TeleOpState(this));
    }
}
