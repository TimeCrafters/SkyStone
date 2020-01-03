package org.timecrafters.Faith;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.timecrafters.engine.Engine;
@TeleOp (name = "mini", group = "SkyStone")
public class MiniBotControlEngine extends Engine {

    @Override
    public void setProcesses() {
        addState(new MiniBotControlState(this));

    }
}
