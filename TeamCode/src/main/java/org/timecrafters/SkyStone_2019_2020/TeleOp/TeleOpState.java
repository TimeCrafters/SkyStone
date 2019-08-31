package org.timecrafters.SkyStone_2019_2020.TeleOp;

import org.timecrafters.SkyStone_2019_2020.Drive;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class TeleOpState extends Drive {

    public TeleOpState(Engine engine) {
        this.engine = engine;
    }

    @Override
    public void exec() throws InterruptedException {

        drivetowards(0);
        engine.telemetry.update();
    }

}

