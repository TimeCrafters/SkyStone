package org.timecrafters.Nartaniel.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.timecrafters.engine.Engine;
@Disabled
@TeleOp (name = "TeleOp")

public class TeleOpEngine extends Engine {

    @Override
    public void setProcesses() {
        addState(new TeleOpState(this));

    }
}
