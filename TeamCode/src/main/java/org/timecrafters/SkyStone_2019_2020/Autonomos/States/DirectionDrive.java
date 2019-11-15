package org.timecrafters.SkyStone_2019_2020.Autonomos.States;

import android.util.Log;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.SkyStone_2019_2020.Drive;
import org.timecrafters.engine.Engine;

public class DirectionDrive extends Drive {

    public StateConfiguration StateConfig;
    private String StateConfigID;
    private double Power;
    private boolean FirstRun = true;
    private double Inches;
    private float DirectionDegrees;
    private int CurrentTick;
    private long StartDelay;

    public DirectionDrive(Engine engine, StateConfiguration stateConfig, String stateConfigID) {
        this.engine = engine;
        StateConfig = stateConfig;
        StateConfigID = stateConfigID;
    }

    @Override
    public void init() {
        super.init();

        Log.i("B2Tag", StateConfigID);
        Log.i("B2Tag", StateConfig.get(StateConfigID).variables().toString());

        Inches = StateConfig.get(StateConfigID).variable("distance");
        Power = StateConfig.get(StateConfigID).variable("maxPower");
        DirectionDegrees = StateConfig.get(StateConfigID).variable("direction");
        StartDelay = StateConfig.get(StateConfigID).variable("delay");

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
                DriveForwardLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                DriveForwardRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                DriveBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                DriveBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

                sleep(StartDelay);

                FirstRun = false;
            }

            int RightTick = DriveForwardRight.getCurrentPosition();
            int LeftTick = DriveForwardLeft.getCurrentPosition();

            if (LeftTick > RightTick) {
                CurrentTick = Math.abs(LeftTick);
            } else {
                CurrentTick = Math.abs(RightTick);
            }

            int tickDistance = InchesToTicks(Inches, 4, 2);

            if (CurrentTick < tickDistance) {

                DriveForwardLeft.setPower(Power * getForwardLeftPower(DirectionDegrees, 0.1));
                DriveBackRight.setPower(Power * getForwardLeftPower(DirectionDegrees, 0.1));

                DriveForwardRight.setPower(Power * getForwardRightPower(DirectionDegrees, 0.1));
                DriveBackLeft.setPower(Power * getForwardRightPower(DirectionDegrees, 0.1));
            } else {
                DriveForwardLeft.setPower(0);
                DriveForwardRight.setPower(0);
                DriveBackLeft.setPower(0);
                DriveBackRight.setPower(0);
                setFinished(true);
            }

            engine.telemetry.addData("Power", Power);
            engine.telemetry.addData("TargetTicks", tickDistance);
            engine.telemetry.addData("CurrentTick", CurrentTick);
            engine.telemetry.addData("Left power", getForwardLeftPower(DirectionDegrees, 0.1));
            engine.telemetry.addData("Right  power", getForwardRightPower(DirectionDegrees, 0.1));

        } else {
            engine.telemetry.addData("Skipping Step", StateConfigID);
            engine.telemetry.update();
            sleep(1000);
            setFinished(true);
        }
    }
}
