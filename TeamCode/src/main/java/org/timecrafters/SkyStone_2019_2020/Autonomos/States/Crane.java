package org.timecrafters.SkyStone_2019_2020.Autonomos.States;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

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
    private double Power;
    private boolean FirstRun = true;

    public Crane(Engine engine, StateConfiguration stateConfig, String stateConfigID) {
        this.engine = engine;
        StateConfig = stateConfig;
        StateConfigID = stateConfigID;
    }

    @Override
    public void init() {

        PosX = StateConfig.get(StateConfigID).variable("xPos");
        PosY = StateConfig.get(StateConfigID).variable("yPos");
        Tolerance = StateConfig.get(StateConfigID).variable("tolerance");
        Power = StateConfig.get(StateConfigID).variable("power");

        CraneX = engine.hardwareMap.dcMotor.get("craneX");
        CraneY = engine.hardwareMap.dcMotor.get("craneY");

        CraneX.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        CraneY.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        CraneY.setDirection(DcMotorSimple.Direction.REVERSE);


        engine.telemetry.addData("Initialized", StateConfigID);
        engine.telemetry.update();

    }

    @Override
    public void exec() throws InterruptedException {
        if (StateConfig.allow(StateConfigID)) {

            if (FirstRun) {
                FirstRun = false;
                CraneX.setTargetPosition(PosX);
                CraneY.setTargetPosition(PosY);
                CraneX.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                CraneY.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                CraneX.setPower(Power);
                CraneY.setPower(Power);
            }

            boolean Xfinish = (CraneX.getCurrentPosition() < PosX + Tolerance && CraneX.getCurrentPosition() > PosX - Tolerance);
            boolean Yfinish = (CraneY.getCurrentPosition() < PosY + Tolerance && CraneY.getCurrentPosition() > PosY - Tolerance);

            if (Xfinish) {
                CraneX.setPower(0);
            }

            if (Yfinish) {
                CraneY.setPower(0);
            }

            setFinished(Xfinish && Yfinish);


            engine.telemetry.addData("X target", CraneY.getTargetPosition());
            engine.telemetry.addData("X current", CraneY.getCurrentPosition());
            engine.telemetry.addData("x Finish", Yfinish);
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
