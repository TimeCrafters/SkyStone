package org.timecrafters.PINKS_2018.TeleOp.Engine;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.timecrafters.PINKS_2018.TeleOp.States.TeleOpState;
import org.timecrafters.engine.Engine;

@Disabled
@TeleOp (name = "TeleOp")

public class TeleOpEngine extends Engine {

    @Override
    public void setProcesses() {
        addState(new TeleOpState(this));

    }
}
