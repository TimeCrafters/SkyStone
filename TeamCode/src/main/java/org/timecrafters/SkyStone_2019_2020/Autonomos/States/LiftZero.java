package org.timecrafters.SkyStone_2019_2020.Autonomos.States;

import com.qualcomm.hardware.rev.RevTouchSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class LiftZero extends State {

    private StateConfiguration StateConfig;
    private String StateConfigID;
    private DcMotor LiftRight;
    private DcMotor LiftLeft;
    private TouchSensor LimitSwitch;
    private double LoweringPower;

    public LiftZero(Engine engine, StateConfiguration stateConfig, String stateConfigID) {
        this.engine = engine;
        StateConfig = stateConfig;
        StateConfigID = stateConfigID;
    }

    @Override
    public void init() {

        LimitSwitch = engine.hardwareMap.touchSensor.get("liftLimit");

        LiftRight = engine.hardwareMap.dcMotor.get("liftRight");
        LiftLeft = engine.hardwareMap.dcMotor.get("liftLeft");
        LiftLeft.setDirection(DcMotor.Direction.REVERSE);

        LiftRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        LiftLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

    }

    @Override
    public void exec() throws InterruptedException {
        if (StateConfig.allow(StateConfigID)) {

            if (LimitSwitch.isPressed()) {
                LiftLeft.setPower(0);
                LiftRight.setPower(0);
                LiftLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                LiftRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                setFinished(true);
            } else {
                LiftLeft.setPower(LoweringPower);
                LiftRight.setPower(LoweringPower);
            }

        } else {
            engine.telemetry.addData("Skipping Step", StateConfigID);
            engine.telemetry.update();
            sleep(1000);
            setFinished(true);
        }
    }
}
