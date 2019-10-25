package org.timecrafters.SkyStone_2019_2020.Autonomos.States;

import com.qualcomm.robotcore.hardware.Servo;

import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class Fingers extends State {

    private StateConfiguration StateConfig;
    private String StateConfigID;
    private Servo FingerServoLeft;
    private Servo FingerServoRight;
    private boolean Clamp;

    public Fingers(Engine engine, StateConfiguration stateConfig, String stateConfigID) {
        this.engine = engine;
        StateConfig = stateConfig;
        StateConfigID = stateConfigID;
    }

    @Override
    public void init() {

        Clamp = StateConfig.get(StateConfigID).variable("clamp");

        FingerServoLeft = engine.hardwareMap.servo.get("fingerLeft");
        FingerServoRight = engine.hardwareMap.servo.get("fingerRight");

        FingerServoLeft.setDirection(Servo.Direction.REVERSE);
    }

    @Override
    public void exec() throws InterruptedException {
        if (StateConfig.allow(StateConfigID)) {

            if (Clamp) {
                FingerServoLeft.setPosition(0.65);
                FingerServoRight.setPosition(0.65);
            } else  {
                FingerServoLeft.setPosition(0);
                FingerServoRight.setPosition(0);
            }

        } else {
            engine.telemetry.addData("Skipping Step", StateConfigID);
            engine.telemetry.update();
            sleep(1000);
            setFinished(true);
        }
    }
}
