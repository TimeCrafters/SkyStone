package org.timecrafters.SkyStone_2019_2020.Autonomos.States;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class Grabber extends State {

    private StateConfiguration StateConfig;
    private String StateConfigID;
    private Servo GrabRotateServo;
    private Servo ArmRight;
    private Servo ArmLeft;
    private CRServo ArmGripRight;
    private CRServo ArmGripLeft;
    private boolean Close;
    private double RotationPosition;

    public Grabber(Engine engine, StateConfiguration stateConfig, String stateConfigID) {
        this.engine = engine;
        StateConfig = stateConfig;
        StateConfigID = stateConfigID;
    }

    @Override
    public void init() {
        Close = StateConfig.get(StateConfigID).variable("close");
        RotationPosition = StateConfig.get(StateConfigID).variable("position");

        GrabRotateServo = engine.hardwareMap.servo.get("grabRot");
        ArmRight = engine.hardwareMap.servo.get("armRight");
        ArmLeft = engine.hardwareMap.servo.get("armLeft");
        ArmGripRight = engine.hardwareMap.crservo.get("armGripRight");
        ArmGripLeft = engine.hardwareMap.crservo.get("armGripLeft");
        ArmGripRight.setDirection(CRServo.Direction.REVERSE);

        GrabRotateServo.setPosition(0.15);
        ArmRight.setPosition(0.4);
        ArmLeft.setPosition(0.5);
    }

    @Override
    public void exec() throws InterruptedException {

        if (StateConfig.allow(StateConfigID)) {

            GrabRotateServo.setPosition(RotationPosition);

            if (Close) {
                ArmRight.setPosition(0.85);
                ArmLeft.setPosition(0.05);
            } else {
                ArmRight.setPosition(0.4);
                ArmLeft.setPosition(0.5);
            }

        } else {
            engine.telemetry.addData("Skipping Step", StateConfigID);
            engine.telemetry.update();
            sleep(1000);
            setFinished(true);
        }

    }
}
