package org.timecrafters.SkyStone_2019_2020.Autonomos.States;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class GripRollers extends State {

    private StateConfiguration StateConfig;
    private String StateConfigID;
    private CRServo ArmGripRight;
    private CRServo ArmGripLeft;
    private boolean Up;
    private long Delay;


    public GripRollers(Engine engine, StateConfiguration stateConfig, String stateConfigID) {
        this.engine = engine;
        StateConfig = stateConfig;
        StateConfigID = stateConfigID;
    }

    @Override
    public void init() {
        Delay = StateConfig.get(StateConfigID).variable("delay");
        Up = StateConfig.get(StateConfigID).variable("up");

        ArmGripRight = engine.hardwareMap.crservo.get("armGripRight");
        ArmGripLeft = engine.hardwareMap.crservo.get("armGripLeft");
        ArmGripRight.setDirection(CRServo.Direction.REVERSE);

        engine.telemetry.addData("Initialized", StateConfigID);
        engine.telemetry.update();

    }

    @Override
    public void exec() throws InterruptedException {

        if (StateConfig.allow(StateConfigID)) {

            if (Up) {
                ArmGripRight.setPower(1);
                ArmGripLeft.setPower(1);
            } else {
                ArmGripRight.setPower(-1);
                ArmGripLeft.setPower(-1);
            }

            sleep(Delay);

            ArmGripRight.setPower(0);
            ArmGripLeft.setPower(0);
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
