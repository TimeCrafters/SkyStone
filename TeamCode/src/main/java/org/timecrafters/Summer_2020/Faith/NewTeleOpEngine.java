package org.timecrafters.Summer_2020.Faith;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.timecrafters.engine.Engine;
@TeleOp (name = "FaithNewDrive")

public class NewTeleOpEngine extends Engine {
    @Override
    public void setProcesses() {
       addState(new NewTeleOpState(this));
    }
}
