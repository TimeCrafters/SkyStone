package org.timecrafters.SkyStone_2019_2020.Autonomos.States;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.SkyStone_2019_2020.Drive;
import org.timecrafters.engine.Engine;

import java.lang.annotation.Target;

public class Face extends Drive {

    private StateConfiguration StateConfig;
    private String StateConfigID;
    private float TargetDegrees;
    private int TargetTicks;
    private float TickDegreeRatio;
    private double Power;
    private boolean FirstRun = true;
    private int FinishTolerance;
    private boolean Clockwise;

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
        FinishTolerance = StateConfig.get(StateConfigID).variable("tolerance");



    }

    @Override
    public void exec() throws InterruptedException {
        if (StateConfig.allow(StateConfigID)) {


            if (FirstRun) {

                TargetTicks = (int) ((TargetDegrees - getRobotRotation()) * TickDegreeRatio);

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

            if (DriveForwardLeft.getCurrentPosition() > TargetTicks - FinishTolerance &&
                    DriveForwardLeft.getCurrentPosition() < TargetTicks + FinishTolerance) {

                DriveForwardLeft.setPower(0);
                DriveForwardRight.setPower(0);
                DriveBackLeft.setPower(0);
                DriveBackRight.setPower(0);

                setFinished(true);
            }


            engine.telemetry.addData("Robot Rotation", getRobotRotation());
            engine.telemetry.addData("Left Tick", DriveForwardLeft);
            engine.telemetry.addData("Right Tick", DriveForwardRight);
            engine.telemetry.update();

        } else {
            engine.telemetry.addData("Skipping Step", StateConfigID);
            engine.telemetry.update();
            sleep(1000);
            setFinished(true);
        }


    }
}
