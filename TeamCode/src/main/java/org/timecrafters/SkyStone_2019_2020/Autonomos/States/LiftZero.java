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
    private int LoweringDistance;
    private double LoweringPower;
    private int FinishTolerance;
    private boolean FirstRun = true;

    public LiftZero(Engine engine, StateConfiguration stateConfig, String stateConfigID) {
        this.engine = engine;
        StateConfig = stateConfig;
        StateConfigID = stateConfigID;
    }

    @Override
    public void init() {
        LoweringDistance = StateConfig.get(StateConfigID).variable("ticks");
        LoweringPower = StateConfig.get(StateConfigID).variable("power");
        FinishTolerance = StateConfig.get(StateConfigID).variable("tolerance");

        LiftRight = engine.hardwareMap.dcMotor.get("liftRight");
        LiftLeft = engine.hardwareMap.dcMotor.get("liftLeft");
        LiftLeft.setDirection(DcMotor.Direction.REVERSE);

        LiftRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        LiftLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        engine.telemetry.addData("Initialized", StateConfigID);
        engine.telemetry.update();

    }

    @Override
    public void exec() throws InterruptedException {
        if (StateConfig.allow(StateConfigID)) {

            int correctedDistance = Math.abs(LoweringDistance);

            if (FirstRun) {
                FirstRun = false;
                LiftLeft.setPower(-LoweringPower);
                LiftRight.setPower(-LoweringPower);
                LiftLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                LiftRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                LiftLeft.setTargetPosition(correctedDistance);
                LiftRight.setTargetPosition(correctedDistance);
                LiftLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                LiftRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }

            if (LiftLeft.getCurrentPosition() > correctedDistance - FinishTolerance ) {
                LiftLeft.setPower(0);
                LiftRight.setPower(0);
                LiftLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                LiftRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

                setFinished(true);
            }

            engine.telemetry.addData("Power", LiftLeft.getPower());
            engine.telemetry.addData("target tick", correctedDistance);
            engine.telemetry.addData("current tick", LiftLeft.getCurrentPosition());
            engine.telemetry.update();

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
