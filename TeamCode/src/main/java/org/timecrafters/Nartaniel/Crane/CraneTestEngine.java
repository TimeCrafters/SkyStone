package org.timecrafters.Nartaniel.Crane;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.timecrafters.engine.Engine;

@TeleOp (name = "Crane Test")
public class CraneTestEngine extends Engine {

    @Override
    public void setProcesses() {
        addState(new Lift(this, 1, 5000));
        addState(new Lift(this, -1, 1000));
        addState(new Lift(this, 0.5, 2000));
    }
}
