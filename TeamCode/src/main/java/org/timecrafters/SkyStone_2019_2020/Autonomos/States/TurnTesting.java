package org.timecrafters.SkyStone_2019_2020.Autonomos.States;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.SkyStone_2019_2020.Drive;
import org.timecrafters.engine.Engine;

public class TurnTesting extends Drive {

    private StateConfiguration StateConfig;
    private String StateConfigID;
    private float TargetDegrees;
    private float DegreeDifference;
    private double Power;
    private boolean FirstRun = true;
    private int TargetTicks = 1000;

    public TurnTesting(Engine engine) {
        this.engine = engine;
    }

    @Override
    public void init() {
        super.init();


    }

    @Override
    public void exec() throws InterruptedException {

            if (FirstRun) {
                DriveForwardLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                DriveForwardRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                DriveBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                DriveBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                DriveForwardLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                DriveForwardRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                DriveBackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                DriveBackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                FirstRun = false;
            }

            DriveForwardLeft.setTargetPosition(TargetTicks);
            DriveBackLeft.setTargetPosition(TargetTicks);
            DriveForwardRight.setTargetPosition(-TargetTicks);
            DriveBackRight.setTargetPosition(-TargetTicks);

            engine.telemetry.addData("Robot Rotation", getRobotRotation());
            engine.telemetry.addData("Left Tick", DriveForwardLeft);
            engine.telemetry.addData("Right Tick", DriveForwardRight);


    }
}
