package org.timecrafters.SkyStone_2019_2020.Autonomos.States;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.SkyStone_2019_2020.Drive;
import org.timecrafters.engine.Engine;

public class Turn extends Drive {

    private StateConfiguration StateConfig;
    private String StateConfigID;
    private int TargetTicks;
    private double Power;
    private int FinishTolerance;
    private boolean FirstRun = true;

    public Turn(Engine engine, StateConfiguration stateConfig, String stateConfigID) {
        this.engine = engine;
        StateConfig = stateConfig;
        StateConfigID = stateConfigID;
    }

    @Override
    public void init() {
        super.init();

        TargetTicks = StateConfig.get(StateConfigID).variable("ticks");
        Power = StateConfig.get(StateConfigID).variable("power");
        FinishTolerance = StateConfig.get(StateConfigID).variable("tolerance");

        engine.telemetry.addData("Initialized", StateConfigID);
        engine.telemetry.update();

    }

    @Override
    public void exec() throws InterruptedException {
        if (StateConfig.allow(StateConfigID)) {

            if (FirstRun) {
                DriveForwardLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                DriveForwardRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                DriveBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                DriveBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

                DriveForwardLeft.setTargetPosition(TargetTicks);
                DriveBackLeft.setTargetPosition(TargetTicks);
                DriveForwardRight.setTargetPosition(-TargetTicks);
                DriveBackRight.setTargetPosition(-TargetTicks);

                DriveForwardLeft.setPower(Power);
                DriveForwardRight.setPower(Power);
                DriveBackLeft.setPower(Power);
                DriveBackRight.setPower(Power);

                DriveForwardLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                DriveForwardRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                DriveBackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                DriveBackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                FirstRun = false;
            }

            if ((DriveForwardLeft.getCurrentPosition() > TargetTicks - FinishTolerance &&
                    DriveForwardLeft.getCurrentPosition() < TargetTicks + FinishTolerance)) {

                DriveForwardLeft.setPower(0);
                DriveForwardRight.setPower(0);
                DriveBackLeft.setPower(0);
                DriveBackRight.setPower(0);

                engine.telemetry.addLine("Done!");

                setFinished(true);
            }

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
