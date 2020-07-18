package org.timecrafters.Summer_2020.Nathaniel.Demo;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.cyberarm.engine.V2.CyberarmEngineV2;
import org.timecrafters.Summer_2020.Minibot;

@Autonomous (name = "NRP: Style Guide")
public class ExampleEngine extends CyberarmEngineV2 {

    private Minibot minibot = new Minibot(hardwareMap);

    @Override
    public void setup() {

        addState(new ExampleState(minibot, "a010_test"));
        addState(new ExampleState(minibot, "a020_test"));
    }
}
