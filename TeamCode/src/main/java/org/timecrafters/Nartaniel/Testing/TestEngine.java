package org.timecrafters.Nartaniel.Testing;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.timecrafters.SkyStone_2019_2020.IMUInit;
import org.timecrafters.engine.Engine;

@TeleOp (name = "Testing Ground", group = "Testing")
public class TestEngine extends Engine {
    @Override
    public void setProcesses() {
        addState(new IMUInit(this));
        addState(new DriveTrainTest(this, .5, 96, 0));
        addState(new DriveTrainTest(this, .5, 96, -90));
        addState(new DriveTrainTest(this, .5, 96, 180));
        addState(new DriveTrainTest(this, .5, 96, 90));

    }
}
