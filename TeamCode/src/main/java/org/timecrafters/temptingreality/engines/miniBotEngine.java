package org.timecrafters.temptingreality.engines;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.timecrafters.engine.Engine;
import org.timecrafters.temptingreality.states.miniBotDrive;

@TeleOp(name = "Too Fast MiniBot", group = "MINIBOT")
public class miniBotEngine extends Engine {
    @Override
    public void setProcesses() {
        addState(new miniBotDrive(this));
    }
}
