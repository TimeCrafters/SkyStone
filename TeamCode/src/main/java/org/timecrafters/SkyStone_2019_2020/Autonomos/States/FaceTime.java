package org.timecrafters.SkyStone_2019_2020.Autonomos.States;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.SkyStone_2019_2020.Drive;
import org.timecrafters.engine.Engine;

public class FaceTime extends Drive {

    private StateConfiguration StateConfig;
    private String StateConfigID;
    private float TargetDegrees;
    private int TargetTicks;
    private float TickDegreeRatio;
    private double Power;
    private boolean FirstRun = true;
    private int FinishTolerance;


    public FaceTime(Engine engine, StateConfiguration stateConfig, String stateConfigID) {
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


                float degreeDifference = TargetDegrees - getRobotRotation();


                if (degreeDifference <= 180 && degreeDifference >= -180) {
                    TargetTicks = (int) (degreeDifference * TickDegreeRatio);

                    DriveForwardLeft.setTargetPosition(TargetTicks);
                    DriveBackLeft.setTargetPosition(TargetTicks);
                    DriveForwardRight.setTargetPosition(-TargetTicks);
                    DriveBackRight.setTargetPosition(-TargetTicks);
                } else if (degreeDifference < -180) {
                    TargetTicks = (int) ( -(degreeDifference + 180) * TickDegreeRatio);

                    DriveForwardLeft.setTargetPosition(TargetTicks);
                    DriveBackLeft.setTargetPosition(TargetTicks);
                    DriveForwardRight.setTargetPosition(-TargetTicks);
                    DriveBackRight.setTargetPosition(-TargetTicks);
                } else if (degreeDifference > 180) {
                    TargetTicks = (int) ( -(degreeDifference - 180) * TickDegreeRatio);

                    DriveForwardLeft.setTargetPosition(TargetTicks);
                    DriveBackLeft.setTargetPosition(TargetTicks);
                    DriveForwardRight.setTargetPosition(-TargetTicks);
                    DriveBackRight.setTargetPosition(-TargetTicks);
                }

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


            engine.telemetry.addData("Running Step", StateConfigID);
            engine.telemetry.addData("Target Tick", TargetTicks);
            engine.telemetry.addData("Current Tick", DriveForwardLeft.getCurrentPosition());
            engine.telemetry.update();

        } else {
            engine.telemetry.addData("Skipping Step", StateConfigID);
            engine.telemetry.update();
            sleep(1000);
            setFinished(true);
        }


    }


}
