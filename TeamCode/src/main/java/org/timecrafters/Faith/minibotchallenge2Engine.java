package org.timecrafters.Faith;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.timecrafters.engine.Engine;
@TeleOp (name = "mini2", group = "SkyStone")
public class minibotchallenge2Engine extends Engine {
    @Override
    public void setProcesses() {
    addState(new Minibotchallenge2state(this));
    }
}
