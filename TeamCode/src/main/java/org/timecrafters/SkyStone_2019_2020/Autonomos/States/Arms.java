package org.timecrafters.SkyStone_2019_2020.Autonomos.States;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class Arms extends State {

    private StateConfiguration StateConfig;
    private String StateConfigID;
    private Servo GrabRotateServo;
    private Servo ArmRight;
    private Servo ArmLeft;
    private boolean Close;
    private double RotationPosition;
    private long Delay;

    public Arms(Engine engine, StateConfiguration stateConfig, String stateConfigID) {
        this.engine = engine;
        StateConfig = stateConfig;
        StateConfigID = stateConfigID;
    }

    @Override
    public void init() {
        //The StateConfig reads a file on the phone and looks for a set of varia
        Close = StateConfig.get(StateConfigID).variable("close");
        RotationPosition = StateConfig.get(StateConfigID).variable("rotation");
        Delay = StateConfig.get(StateConfigID).variable("delay");

        GrabRotateServo = engine.hardwareMap.servo.get("grabRot");
        ArmRight = engine.hardwareMap.servo.get("armRight");
        ArmLeft = engine.hardwareMap.servo.get("armLeft");


        GrabRotateServo.setPosition(1);
        ArmRight.setPosition(0.3);
        ArmLeft.setPosition(0.1);

        engine.telemetry.addData("Initialized", StateConfigID);
        engine.telemetry.update();

    }

    @Override
    public void exec() throws InterruptedException {

        if (StateConfig.allow(StateConfigID)) {

            GrabRotateServo.setPosition(RotationPosition);

            if (Close) {

                ArmRight.setPosition(0.3);
                ArmLeft.setPosition(0.7);
            } else {
                ArmRight.setPosition(0.8);
                ArmLeft.setPosition(0.2);
            }

            sleep(Delay);
            setFinished(true);

        } else {
            engine.telemetry.addData("Skipping Step", StateConfigID);
            engine.telemetry.update();
            if (StateConfig.allow("AddSkipDelays")) {
                sleep(1000);
            }
            setFinished(true);
        }

    }
}
