package org.timecrafters.SkyStone_2019_2020.Autonomos.States;

import android.util.Log;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.SkyStone_2019_2020.Drive;
import org.timecrafters.engine.Engine;

public class FaceActveCheck extends Drive {

    private StateConfiguration StateConfig;
    private String StateConfigID;
    private float TargetDegrees;
    private float TickDegreeRatio;
    private double Power;
    private boolean FirstRun = true;
    private int FinishTolerance;
    private long StartTime;
    private long InterruptTime;
    private int Direction;
    private int SuggestedDirection;
    float DegreeDifference;



    public FaceActveCheck(Engine engine, StateConfiguration stateConfig, String stateConfigID) {
        this.engine = engine;
        StateConfig = stateConfig;
        StateConfigID = stateConfigID;
    }

    @Override
    public void init() {
        super.init();

        TargetDegrees = StateConfig.get(StateConfigID).variable("degrees");
        Power = StateConfig.get(StateConfigID).variable("power");
        FinishTolerance = StateConfig.get(StateConfigID).variable("tolerance");

        try {
            SuggestedDirection = StateConfig.get(StateConfigID).variable("direction");
        } catch (NullPointerException e) {
            SuggestedDirection = 0;
        }

        try  {
            InterruptTime = StateConfig.get(StateConfigID).variable("stopTime");
        } catch (NullPointerException e) {
            InterruptTime = 30000;
        }

        engine.telemetry.addData("Initialized", StateConfigID);
        engine.telemetry.update();

    }

    @Override
    public void exec() throws InterruptedException {
        if (StateConfig.allow(StateConfigID)) {
            long currentTime = System.currentTimeMillis();

            float robotRotation = getRobotRotation();

            if (FirstRun) {
                StartTime = currentTime;

                DriveForwardLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                DriveForwardRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                DriveBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                DriveBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


                DriveForwardLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                DriveForwardRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                DriveBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                DriveBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

                FirstRun = false;
            }

            //If the direction is unspecified, select whichever direction will complete the turn
            //in the shortest distance.
            if (SuggestedDirection == 0) {
                float adjustedRotation = robotRotation;
                if (adjustedRotation < 0) {
                    adjustedRotation += 360;
                }
                if (((360 + (adjustedRotation - TargetDegrees)) % 360) < 180) {
                    Direction = -1;
                } else {
                    Direction = 1;
                }
            } else {
                Direction = SuggestedDirection;
            }

            if (Direction == 1) {
                DriveForwardLeft.setPower(Power);
                DriveBackLeft.setPower(Power);
                DriveForwardRight.setPower(-Power);
                DriveBackRight.setPower(-Power);
            } else if (Direction == -1) {
                DriveForwardLeft.setPower(-Power);
                DriveBackLeft.setPower(-Power);
                DriveForwardRight.setPower(Power);
                DriveBackRight.setPower(Power);
            }


            if ((robotRotation > TargetDegrees - FinishTolerance &&
                   robotRotation < TargetDegrees + FinishTolerance) ||
                    (currentTime - StartTime >= InterruptTime) ) {

                Log.i("STATE_DEBUG", "Reached Goal, stopping...");

                DriveForwardLeft.setPower(0);
                DriveForwardRight.setPower(0);
                DriveBackLeft.setPower(0);
                DriveBackRight.setPower(0);

                engine.telemetry.addLine("Done!");

                setFinished(true);
            }

            engine.telemetry.addData("Robot Rotation", robotRotation);
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

    @Override
    public void telemetry() {
        //engine.telemetry.addData("Running Step", StateConfigID);

//        engine.telemetry.addData("Target Tick", TargetTicks);
//        engine.telemetry.addData("Current Tick", DriveForwardLeft.getCurrentPosition());
    }
}
