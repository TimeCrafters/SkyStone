package org.timecrafters.SkyStone_2019_2020.Autonomos.States;

import android.util.Log;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.cyberarm.NeXT.StateConfiguration;
import org.cyberarm.plotter.TCPServer.Client;
import org.timecrafters.SkyStone_2019_2020.Drive;
import org.timecrafters.engine.Engine;

public class FaceExplicit extends Drive {

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
    private boolean Clockwise;


    public FaceExplicit(Engine engine, StateConfiguration stateConfig, String stateConfigID) {
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
        Clockwise = StateConfig.get(StateConfigID).variable("clockwise");


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

                if (robotRotation < 0) {
                    robotRotation += 360;
                }

                float degreeDifference = TargetDegrees - robotRotation;

                if (Clockwise && degreeDifference < 0) {
                    degreeDifference += 360;
                }
                if (!Clockwise && degreeDifference > 0) {
                    degreeDifference -=360;
                }

                TargetTicks = (int) (degreeDifference * TickDegreeRatio);

                DriveForwardLeft.setTargetPosition(TargetTicks);
                DriveBackLeft.setTargetPosition(TargetTicks);
                DriveForwardRight.setTargetPosition(-TargetTicks);
                DriveBackRight.setTargetPosition(-TargetTicks);

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
        engine.telemetry.addData("Running Step", StateConfigID);
        engine.telemetry.addData("Robot Rotation", getRobotRotation());
        engine.telemetry.addData("Target Tick", TargetTicks);
        engine.telemetry.addData("Current Tick", DriveForwardLeft.getCurrentPosition());
    }
}
