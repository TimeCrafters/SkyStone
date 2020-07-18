package org.timecrafters.Summer_2020.Nathaniel.Retrace;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.cyberarm.engine.V2.CyberarmEngineV2;

@TeleOp(name = "NRP : Retrace", group = "NRP")
public class RetraceEngine extends CyberarmEngineV2 {
    private Record RecordState = new Record();

    @Override
    public void setup() {
        addState(RecordState);
        addState(new RecordRetraceControl(RecordState));
    }
}
