package org.timecrafters.Faith;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.CRServo;

import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;
@Autonomous (name = "Another Try")
public class Myonwattempt extends Engine {


    @Override
    public void setProcesses() {
        addState(new myownattempttogetherthingy(this));
    }
}
