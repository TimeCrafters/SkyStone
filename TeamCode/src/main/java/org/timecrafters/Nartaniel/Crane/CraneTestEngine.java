package org.timecrafters.Nartaniel.Crane;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.timecrafters.engine.Engine;

@TeleOp (name = "Crane Test")
public class CraneTestEngine extends Engine {

    @Override
    public void setProcesses() {
        addState(new CraneTestState(this));
    }
}
