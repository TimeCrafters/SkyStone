package org.timecrafters.Summer_2020.Nathaniel;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.timecrafters.engine.Engine;

@TeleOp (name = "NRP: Strait Line Test", group = "NRP")
public class DisplacementTestEngine extends Engine {
    @Override
    public void setProcesses() {
        addState(new MiniBotTesting(this, 0.5, 0.25, 100,40 , 1000));
    }
}
