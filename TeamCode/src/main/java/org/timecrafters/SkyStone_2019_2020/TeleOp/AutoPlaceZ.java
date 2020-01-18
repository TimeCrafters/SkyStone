package org.timecrafters.SkyStone_2019_2020.TeleOp;

import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class AutoPlaceZ extends State {

    private StateConfiguration StateConfig;
    private DcMotor LiftRight;
    private DcMotor LiftLeft;
    private Rev2mDistanceSensor LeftDistanceSensor;
    private Rev2mDistanceSensor RightDistanceSensor;
    private double AutoLiftPower;

    public AutoPlaceZ(Engine engine, StateConfiguration stateConfiguration) {
        this.engine = engine;
        StateConfig = stateConfiguration;
    }

    @Override
    public void init() {
        AutoLiftPower = StateConfig.get("TeleOp").variable("placePowerZ");

        LiftRight = engine.hardwareMap.dcMotor.get("liftRight");
        LiftLeft = engine.hardwareMap.dcMotor.get("liftLeft");
        RightDistanceSensor = engine.hardwareMap.get(Rev2mDistanceSensor.class, "distanceRight");
        LeftDistanceSensor = engine.hardwareMap.get(Rev2mDistanceSensor.class, "distanceLeft");
    }

    @Override
    public void exec() throws InterruptedException {

    }
}
