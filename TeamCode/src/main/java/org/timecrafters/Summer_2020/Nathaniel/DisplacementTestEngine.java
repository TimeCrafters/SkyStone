package org.timecrafters.Summer_2020.Nathaniel;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.timecrafters.engine.Engine;

@TeleOp (name = "NRP: Strait Line Test", group = "NRP")
public class DisplacementTestEngine extends Engine {
    @Override
    public void setProcesses() {
        //addState(new MiniBotTesting(this, 1, 0.3 , 100,40 , 1000));
        addState(new DisplacementTest(this));
    }
}
