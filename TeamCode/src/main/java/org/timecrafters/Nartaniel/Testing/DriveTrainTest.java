package org.timecrafters.Nartaniel.Testing;

import org.timecrafters.SkyStone_2019_2020.Drive;
import org.timecrafters.engine.Engine;

public class DriveTrainTest extends Drive {

    public DriveTrainTest(Engine engine) {
        this.engine = engine;
    }

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void exec() throws InterruptedException {
        DriveForwardLeft.setPower(0.25);
        DriveForwardRight.setPower(0.25);
        DriveBackLeft.setPower(0.25);
        DriveBackRight.setPower(0.25);
    }
}
