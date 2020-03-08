package org.timecrafters.Summer_2020.Thomas_summer2020;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.timecrafters.engine.Engine;

@TeleOp (name = "Driver Relative Test")
public class DriverRelativeEngine extends Engine {
    @Override
    public void setProcesses() {
        addState(new DriverRelativeTest(this ));
    }
}
