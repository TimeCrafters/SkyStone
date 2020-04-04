package org.timecrafters.Faith;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.timecrafters.engine.Engine;
@Autonomous (name = "Minidrive3")
public class MinibotautoDriveEngine extends Engine {
    @Override
    public void setProcesses() {
        addState(new MinibotautoDriveState(this,1524,1524));
        addState(new MinibotautoDriveState(this,0,291));
        addState(new MinibotautoDriveState(this,1524,1524));
        addState(new MinibotautoDriveState(this,0,291));
        addState(new MinibotautoDriveState(this,1524,1524));
        addState(new MinibotautoDriveState(this,0,291));
        addState(new MinibotautoDriveState(this,1524,1524));
    }
}
