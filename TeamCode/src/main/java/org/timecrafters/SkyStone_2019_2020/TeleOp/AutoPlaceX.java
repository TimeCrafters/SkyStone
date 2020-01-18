package org.timecrafters.SkyStone_2019_2020.TeleOp;

import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class AutoPlaceX extends State {

    private StateConfiguration StateConfig;
    private DcMotor CraneX;
    private Rev2mDistanceSensor LeftDistanceSensor;
    private Rev2mDistanceSensor RightDistanceSensor;
    private double AutoCranePowerX;


    public AutoPlaceX(Engine engine, StateConfiguration stateConfiguration) {
        this.engine = engine;
        StateConfig = stateConfiguration;
    }

    @Override
    public void init() {
        AutoCranePowerX = StateConfig.get("TeleOp").variable("placePowerX");

        CraneX = engine.hardwareMap.dcMotor.get("craneX");
        RightDistanceSensor = engine.hardwareMap.get(Rev2mDistanceSensor.class, "distanceRight");
        LeftDistanceSensor = engine.hardwareMap.get(Rev2mDistanceSensor.class, "distanceLeft");
    }

    @Override
    public void exec() throws InterruptedException {

    }
}
