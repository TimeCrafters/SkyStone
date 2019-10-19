package org.timecrafters.SkyStone_2019_2020.Autonomos.States;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.SkyStone_2019_2020.Drive;
import org.timecrafters.engine.Engine;

public class RampDrive extends Drive {

    public StateConfiguration StateConfig;
    private String StateConfigID;
    private double MaxPower;
    private boolean FirstRun = true;
    private double Inches;
    private double MinPower;
    private double RampFunctionDegree;
    private float DirectionDegrees;
    private int CurrentTick;
    private long StartDelay;

    public RampDrive(Engine engine, StateConfiguration stateConfig, String stateConfigID) {
        this.engine = engine;
        StateConfig = stateConfig;
        StateConfigID = stateConfigID;
    }

    @Override
    public void init() {
        super.init();

        Inches = StateConfig.get(StateConfigID).variable("distance");
        MinPower = StateConfig.get(StateConfigID).variable("minPower");
        MaxPower = StateConfig.get(StateConfigID).variable("maxPower");
        DirectionDegrees = StateConfig.get(StateConfigID).variable("direction");
        RampFunctionDegree = StateConfig.get(StateConfigID).variable("degree");
        StartDelay = StateConfig.get(StateConfigID).variable("startDelay");


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
            double power = MaxPower * DistanceToRampedPower(CurrentTick, tickDistance, MinPower, RampFunctionDegree);

            if (CurrentTick < tickDistance) {

                DriveForwardLeft.setPower(power * getForwardLeftPower(DirectionDegrees, MinPower));
                DriveBackRight.setPower(power * getForwardLeftPower(DirectionDegrees, MinPower));

                DriveForwardRight.setPower(power * getForwardRightPower(DirectionDegrees, MinPower));
                DriveBackLeft.setPower(power * getForwardRightPower(DirectionDegrees, MinPower));
            } else {
                DriveForwardLeft.setPower(0);
                DriveForwardRight.setPower(0);
                DriveBackLeft.setPower(0);
                DriveBackRight.setPower(0);
                setFinished(true);
            }

            engine.telemetry.addData("Power", power);
            engine.telemetry.addData("TargetTicks", tickDistance);
            engine.telemetry.addData("CurrentTick", CurrentTick);
            engine.telemetry.addData("Left power", getForwardLeftPower(DirectionDegrees, MinPower));
            engine.telemetry.addData("Right  power", getForwardRightPower(DirectionDegrees, MinPower));

        } else {
            engine.telemetry.addData("Skipping Step", StateConfigID);
            engine.telemetry.update();
            sleep(1000);
            setFinished(true);
        }
    }

    private double DistanceToRampedPower (int currentTick, int TargetTicks, double minPower, double functionDegree) {

        //The vertex is the halfway point, where the robot stops accelerating and  starts decelerating.

        double vertex = (double) TargetTicks / 2;

        float scalar = ((float) Math.pow((2.0 / TargetTicks), functionDegree))*((float) (minPower - 1));

        //these two outputs are reflections of each other, across the vertex.
        if (currentTick >= vertex) {
            return (scalar * (Math.pow((currentTick - vertex), functionDegree))) + 1;
        } else {
            return (scalar * (Math.pow((-currentTick + vertex), functionDegree))) + 1;
        }

    }
}
