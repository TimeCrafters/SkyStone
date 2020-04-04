package org.timecrafters.Summer_2020.scott_arm;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.timecrafters.engine.Engine;

@TeleOp(name = "scott_arm")
public class scott_arm_engine_manual extends Engine{
    @Override
    public void setProcesses() {
        addState(new scott_arm_state(this));
    }
}

