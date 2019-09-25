package org.timecrafters.Nartaniel.Testing;

import org.timecrafters.SkyStone_2019_2020.Drive;
import org.timecrafters.engine.Engine;

public class RotationSensorTest extends Drive {

    public RotationSensorTest(Engine engine) {
        this.engine = engine;
    }

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void exec() throws InterruptedException {
        engine.telemetry.addData("Rotation", getRobotRotation());
    }
}
