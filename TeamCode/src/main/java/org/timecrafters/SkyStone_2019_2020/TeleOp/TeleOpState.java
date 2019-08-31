package org.timecrafters.SkyStone_2019_2020.TeleOp;

import org.timecrafters.SkyStone_2019_2020.Drive;
import org.timecrafters.engine.Engine;


public class TeleOpState extends Drive {

    @Override
    public void init() {

        super.init();

    }

    public TeleOpState(Engine engine) {
        this.engine = engine;
    }

    @Override
    public void exec() throws InterruptedException {

        drivetowards(0);
        engine.telemetry.update();
    }

}

