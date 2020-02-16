package org.timecrafters.thomas_thingy;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.timecrafters.engine.Engine;
@TeleOp(name = "teleop thingy")
public class engine_teleop extends Engine {
    @Override
    public void setProcesses() {
        addState(new teleop_state(this));

    }
}
