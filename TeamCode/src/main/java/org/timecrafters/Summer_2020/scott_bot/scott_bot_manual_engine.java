package org.timecrafters.Summer_2020.scott_bot;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.timecrafters.engine.Engine;

@TeleOp (name = "scott_bot")
public class scott_bot_manual_engine extends Engine {
    @Override
    public void setProcesses() {

        addState(new scott_bot_manual_drive_state(this));
    }
}

