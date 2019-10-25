package org.timecrafters.SkyStone_2019_2020.Autonomos.States;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.SkyStone_2019_2020.Drive;
import org.timecrafters.engine.Engine;

public class Face extends Drive {

    private StateConfiguration StateConfig;
    private String StateConfigID;
    private float TargetDegrees;
    private int TargetTicks;
    private float TickDegreeRatio;
    private double Power;
    private boolean FirstRun = true;

    public Face(Engine engine, StateConfiguration stateConfig, String stateConfigID) {
        this.engine = engine;
        StateConfig = stateConfig;
        StateConfigID = stateConfigID;
    }

    @Override
    public void init() {
        super.init();

        TargetDegrees = StateConfig.get(StateConfigID).variable("degrees");
        Power = StateConfig.get(StateConfigID).variable("power");
        TickDegreeRatio = StateConfig.get(StateConfigID).variable("ratio");

    }

    @Override
    public void exec() throws InterruptedException {
        if (StateConfig.allow(StateConfigID)) {

            if (FirstRun) {
                DriveForwardLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                DriveForwardRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                DriveBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                DriveBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                DriveForwardLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                DriveForwardRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                DriveBackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                DriveBackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                TargetTicks = (int) ((TargetDegrees - getRobotRotation()) * TickDegreeRatio);

                FirstRun = false;
            }


        } else {
            engine.telemetry.addData("Skipping Step", StateConfigID);
            engine.telemetry.update();
            sleep(1000);
            setFinished(true);
        }


    }
}
