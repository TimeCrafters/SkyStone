package org.timecrafters.SkyStone_2019_2020.Autonomos.States;

import android.util.Log;

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

                Log.i("STATE_DEBUG", "Degree difference is " + degreeDifference + " robotRotation is " + getRobotRotation());



                if (degreeDifference <= 260 && degreeDifference >= -260) {
                    Log.i("STATE_DEBUG", "In range");

                    TargetTicks = (int) (degreeDifference * TickDegreeRatio);

                    DriveForwardLeft.setTargetPosition(TargetTicks);
                    DriveBackLeft.setTargetPosition(TargetTicks);
                    DriveForwardRight.setTargetPosition(-TargetTicks);
                    DriveBackRight.setTargetPosition(-TargetTicks);
                } else if (degreeDifference < -180) {
                    Log.i("STATE_DEBUG", "In < -180");

                    TargetTicks = (int) ( -(degreeDifference + 180) * TickDegreeRatio);

                    DriveForwardLeft.setTargetPosition(TargetTicks);
                    DriveBackLeft.setTargetPosition(TargetTicks);
                    DriveForwardRight.setTargetPosition(-TargetTicks);
                    DriveBackRight.setTargetPosition(-TargetTicks);
                } else if (degreeDifference > 180) {
                    Log.i("STATE_DEBUG", "In > 180");

                    TargetTicks = (int) ( -(degreeDifference - 180) * TickDegreeRatio);

                    DriveForwardLeft.setTargetPosition(TargetTicks);
                    DriveBackLeft.setTargetPosition(TargetTicks);
                    DriveForwardRight.setTargetPosition(-TargetTicks);
                    DriveBackRight.setTargetPosition(-TargetTicks);
                } else {
                    Log.i("STATE_DEBUG", "Failed to set TargetTicks!!!");
                }

                Log.i("STATE_DEBUG", "TargetTicks set to " + TargetTicks + " from " + TargetDegrees + " degrees");

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

            int currentPosition = Math.abs(DriveForwardLeft.getCurrentPosition());

            if ((currentPosition > Math.abs(TargetTicks) - FinishTolerance &&
                   currentPosition < Math.abs(TargetTicks) + FinishTolerance)) {

                Log.i("STATE_DEBUG", "Reached Goal, stopping...");

                DriveForwardLeft.setPower(0);
                DriveForwardRight.setPower(0);
                DriveBackLeft.setPower(0);
                DriveBackRight.setPower(0);

                engine.telemetry.addLine("Done!");
//                sleep(1000);

                setFinished(true);
            }



        } else {
            engine.telemetry.addData("Skipping Step", StateConfigID);
            engine.telemetry.update();
            sleep(1000);
            setFinished(true);
        }


    }

    @Override
    public void telemetry() {
        engine.telemetry.addData("Running Step", StateConfigID);
        engine.telemetry.addData("Robot Rotation", getRobotRotation());
        engine.telemetry.addData("Target Tick", TargetTicks);
        engine.telemetry.addData("Current Tick", DriveForwardLeft.getCurrentPosition());
    }
}
