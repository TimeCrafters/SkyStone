package org.timecrafters.Summer_2020.scott_bot;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.timecrafters.engine.Engine;

@Autonomous(name = "scott_bot_auto")
public class scott_bot_auto_engine extends Engine{
    @Override
    public void setProcesses() {
        addState(new scott_bot_auto_drive_state(this));
    }
}

