package org.timecrafters.Summer_2020.Nathaniel;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.cyberarm.engine.V2.CyberarmEngineV2;
import org.timecrafters.engine.Engine;

@TeleOp (name = "NRP: Strait Line Test", group = "NRP")
public class DisplacementTestEngine extends CyberarmEngineV2 {

    @Override
    public void setup() {
        addState(new MiniBotTesting2());
    }
}
