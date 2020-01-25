package org.timecrafters.SkyStone_2019_2020.Autonomos.States;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class Lift extends State {

    private StateConfiguration StateConfig;
    private String StateConfigID;
    private DcMotor LiftRight;
    private DcMotor LiftLeft;
    private int LiftHight;
    private int FinishTolerance;
    private double Power;
    private boolean FirstRun = true;

    public Lift(Engine engine, StateConfiguration stateConfig, String stateConfigID) {
        this.engine = engine;
        StateConfig = stateConfig;
        StateConfigID = stateConfigID;
    }

    @Override
    public void init() {

        LiftHight = StateConfig.get(StateConfigID).variable("height");
        FinishTolerance = StateConfig.get(StateConfigID).variable("tolerance");
        Power = StateConfig.get(StateConfigID).variable("power");

        LiftRight = engine.hardwareMap.dcMotor.get("liftRight");
        LiftLeft = engine.hardwareMap.dcMotor.get("liftLeft");
        LiftLeft.setDirection(DcMotor.Direction.REVERSE);

        LiftRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LiftLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        LiftRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        LiftLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        engine.telemetry.addData("Initialized", StateConfigID);
        engine.telemetry.update();

    }

    @Override
    public void exec() throws InterruptedException {
        if (StateConfig.allow(StateConfigID)) {

        if (FirstRun) {
            LiftRight.setTargetPosition(-LiftHight);
            LiftLeft.setTargetPosition(-LiftHight);

            LiftRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            LiftLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            LiftRight.setPower(-Power);
            LiftLeft.setPower(-Power);

            FirstRun = false;
        }

            if (LiftLeft.getCurrentPosition() > -LiftHight - FinishTolerance &&
                    LiftLeft.getCurrentPosition() < -LiftHight + FinishTolerance) {

                LiftLeft.setPower(0);
                LiftRight.setPower(0);

                setFinished(true);
            }

            engine.telemetry.addData("Power", LiftLeft.getPower());
            engine.telemetry.addData("target tick", LiftHight);
            engine.telemetry.addData("current tick", LiftLeft.getCurrentPosition());
            engine.telemetry.addData("Finished", LiftLeft.getCurrentPosition() > LiftHight - FinishTolerance &&
                    LiftLeft.getCurrentPosition() < LiftHight + FinishTolerance) ;
            engine.telemetry.update();

        } else {
            engine.telemetry.addData("Skipping Step", StateConfigID);
            engine.telemetry.update();
            sleep(1000);
            setFinished(true);
        }
    }
}
