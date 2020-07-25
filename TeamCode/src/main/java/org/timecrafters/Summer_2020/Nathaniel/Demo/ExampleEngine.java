package org.timecrafters.Summer_2020.Nathaniel.Demo;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.cyberarm.engine.V2.CyberarmEngineV2;
import org.timecrafters.Summer_2020.Minibot;

@Autonomous (name = "NRP: Style Guide",group = "NRP")
public class ExampleEngine extends CyberarmEngineV2 {

    private Minibot minibot;

    @Override
    public void init() {
        minibot = new Minibot(hardwareMap);
        minibot.initHardware();
        super.init();
    }

    @Override
    public void setup() {

        addState(new ExampleState(minibot, "a010_test"));
        addState(new ExampleState(minibot,"a011_test"));
        addState(new ExampleState(minibot, "a020_test"));
        addState(new ExampleState(minibot, "a030_test"));
        addState(new ExampleState(minibot, "a040_test"));

    }
}
