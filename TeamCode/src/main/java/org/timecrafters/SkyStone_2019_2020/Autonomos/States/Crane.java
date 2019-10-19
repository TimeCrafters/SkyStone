package org.timecrafters.SkyStone_2019_2020.Autonomos.States;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class Crane extends State {

    private StateConfiguration StateConfig;
    private String StateConfigID;
    private DcMotor CraneX;
    private DcMotor CraneY;
    private int Tolerance;
    private int PosY;
    private int PosX;
    private boolean FirstRun;

    public Crane(Engine engine, StateConfiguration stateConfig, String stateConfigID) {
        this.engine = engine;
        StateConfig = stateConfig;
        StateConfigID = stateConfigID;
    }

    @Override
    public void init() {

        PosX = StateConfig.get(StateConfigID).variable("xPos");
        PosX = StateConfig.get(StateConfigID).variable("yPos");
        Tolerance = StateConfig.get(StateConfigID).variable("tolerance");

        CraneX = engine.hardwareMap.dcMotor.get("craneX");
        CraneY = engine.hardwareMap.dcMotor.get("craneY");

        CraneX.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        CraneY.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        CraneX.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        CraneY.setMode(DcMotor.RunMode.RUN_TO_POSITION);

    }

    @Override
    public void exec() throws InterruptedException {


        if (StateConfig.allow(StateConfigID)) {

            if (CraneX.getCurrentPosition() < PosX + Tolerance && CraneX.getCurrentPosition() > PosX - Tolerance) {
                CraneX.setPower(0);
                CraneY.setPower(0);
            } else {
                CraneX.setTargetPosition(PosX);
                CraneY.setTargetPosition(PosY);
            }

        } else {
            engine.telemetry.addData("Skipping Step", StateConfigID);
            engine.telemetry.update();
            sleep(1000);
            setFinished(true);
        }
    }
}
