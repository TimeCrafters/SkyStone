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
        DriveForwardLeft.setPower(1.0);
        DriveForwardRight.setPower(1.0);
        DriveBackLeft.setPower(1.0);
        DriveBackRight.setPower(1.0);
    }
}
