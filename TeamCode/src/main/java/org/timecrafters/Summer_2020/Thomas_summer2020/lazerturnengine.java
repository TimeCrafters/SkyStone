package org.timecrafters.Summer_2020.Thomas_summer2020;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.cyberarm.engine.V2.CyberarmEngineV2;
import org.timecrafters.Summer_2020.Minibot;
import org.timecrafters.Summer_2020.Nathaniel.Demo.ExampleState;

@Autonomous (name = "thomas: lazer turn",group = "Thomas")
public class lazerturnengine extends CyberarmEngineV2 {

    private Minibot minibot;

    @Override
    public void init() {
        minibot = new Minibot(hardwareMap);
        minibot.initHardware();
        super.init();
    }

    @Override
    public void setup() {
addState(new lzerturnstate(minibot,"t010_turnPast"));


    }
}
