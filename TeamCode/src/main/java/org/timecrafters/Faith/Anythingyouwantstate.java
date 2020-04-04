package org.timecrafters.Faith;

import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class Anythingyouwantstate extends State {
    StateConfiguration Config;

    public Anythingyouwantstate(Engine engine) {
       // Config = config;
        this.engine =engine;
    }

    @Override
    public void init() {
        Config = new StateConfiguration();
    }

    @Override
    public void exec() throws InterruptedException {

    }

    @Override
    public void telemetry() {
        engine.telemetry.addData("yeet", Config.get("jfzbnf").variable("yeet"));
    }
}
