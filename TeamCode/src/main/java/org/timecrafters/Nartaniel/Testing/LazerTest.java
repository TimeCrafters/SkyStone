package org.timecrafters.Nartaniel.Testing;

import com.qualcomm.hardware.rev.Rev2mDistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class LazerTest extends State {

    private Rev2mDistanceSensor RightDistanceSensor;
    private Rev2mDistanceSensor LeftDistanceSensor;

    public LazerTest(Engine engine) {
        this.engine = engine;
    }

    @Override
    public void init() {
        RightDistanceSensor = engine.hardwareMap.get(Rev2mDistanceSensor.class, "distanceRight");
        LeftDistanceSensor = engine.hardwareMap.get(Rev2mDistanceSensor.class, "distanceLeft");

    }

    @Override
    public void exec() throws InterruptedException {
        engine.telemetry.addData("RightSensor", RightDistanceSensor.getDistance(DistanceUnit.MM));
        engine.telemetry.addData("LeftSensor", LeftDistanceSensor.getDistance(DistanceUnit.MM));
        engine.telemetry.update();
    }
}
