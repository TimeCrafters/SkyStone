package org.timecrafters.SkyStone_2019_2020.Autonomos.States;

import android.util.Log;

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
    private int FinishTolerance;
    private long StartTime;
    private long InterruptTime;
    private int Direction;
    float DegreeDifference;



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

        try {
            Direction = StateConfig.get(StateConfigID).variable("direction");
        } catch (NullPointerException e) {
            Direction = 0;
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

            if (FirstRun) {
                StartTime = currentTime;

                DriveForwardLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                DriveForwardRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                DriveBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                DriveBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


                float robotRotation = getRobotRotation();

                Log.i("STATE_DEBUG", "Degree difference is " + DegreeDifference + " robotRotation is " + getRobotRotation());

                //if the direction variable is zero, turn in whichever direction will get to the target angle faster.
                //Used to be our only option before we realised that the shortest rout wasn't always the best.
                if (Direction == 0) {

                    DegreeDifference = TargetDegrees - robotRotation;

                    //if the degree difference is within the expected range, run the conversion with
                    //the Degree diference unchanged.
                    if (DegreeDifference <= 180 && DegreeDifference >= -180) {
                        Log.i("STATE_DEBUG", "In range");

                        TargetTicks = (int) (DegreeDifference * TickDegreeRatio);

                        //if the Degree difference is outside the expected range, adjust it before running
                        //the conversion.
                    } else if (DegreeDifference < -180) {
                        Log.i("STATE_DEBUG", "In < -180");

                        TargetTicks = (int) (-(DegreeDifference + 360) * TickDegreeRatio);

                    } else if (DegreeDifference > 180) {
                        Log.i("STATE_DEBUG", "In > 180");

                        TargetTicks = (int) (-(DegreeDifference - 360) * TickDegreeRatio);

                    } else {
                        Log.i("STATE_DEBUG", "Failed to set TargetTicks!!!");
                    }
                } else {

                    if (robotRotation < 0) {
                        robotRotation += 360;
                    }

                    DegreeDifference = TargetDegrees - robotRotation;

                    if (Direction == 1 && DegreeDifference < 0) {
                        DegreeDifference += 360;
                    }
                    if (Direction == -1 && DegreeDifference > 0) {
                        DegreeDifference -=360;
                    }

                    TargetTicks = (int) (DegreeDifference * TickDegreeRatio);
                }

                Log.i("STATE_DEBUG", "TargetTicks set to " + TargetTicks + " from " + TargetDegrees + " degrees");

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

            int currentPosition = Math.abs(DriveForwardLeft.getCurrentPosition());

            if ((currentPosition > Math.abs(TargetTicks) - FinishTolerance &&
                   currentPosition < Math.abs(TargetTicks) + FinishTolerance) ||
                    (currentTime - StartTime >= InterruptTime) ) {

                Log.i("STATE_DEBUG", "Reached Goal, stopping...");

                DriveForwardLeft.setPower(0);
                DriveForwardRight.setPower(0);
                DriveBackLeft.setPower(0);
                DriveBackRight.setPower(0);

                engine.telemetry.addLine("Done!");
//                sleep(1000);

                setFinished(true);
            }

            engine.telemetry.addData("Robot Rotation", getRobotRotation());
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
