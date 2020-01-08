package org.timecrafters.Nartaniel.Training;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class TrainingCraneState extends State {

    private StateConfiguration StateConfig;
    private String StateConfigID;
    private DcMotor CraneX;
    private double Power;
    private int TargetTicks;
    private boolean HasRun;

    public TrainingCraneState(Engine engine, StateConfiguration stateConfig, String stateConfigID) {
        this.engine = engine;
        StateConfig = stateConfig;
        StateConfigID = stateConfigID;
    }

    @Override
    public void init() {
        Power = StateConfig.get(StateConfigID).variable("power");
        TargetTicks = StateConfig.get(StateConfigID).variable("ticks");

        CraneX = engine.hardwareMap.dcMotor.get("craneX");
    }

    @Override
    public void exec() throws InterruptedException {

        if (StateConfig.allow(StateConfigID)) {

            if (!HasRun) {
                CraneX.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                CraneX.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                HasRun = true;
            }

            int currentTick = Math.abs(CraneX.getCurrentPosition());

            if (currentTick < TargetTicks) {
                CraneX.setPower(Power);
            } else {
                CraneX.setPower(0);
                setFinished(true);
            }

        } else {

            setFinished(true);
        }
    }
}
