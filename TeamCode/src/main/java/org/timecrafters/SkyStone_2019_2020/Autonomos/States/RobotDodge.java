package org.timecrafters.SkyStone_2019_2020.Autonomos.States;

import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.cyberarm.NeXT.StateConfiguration;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.timecrafters.SkyStone_2019_2020.Drive;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class RobotDodge extends Drive {

    private StateConfiguration StateConfig;
    private String StateConfigID;
    private Rev2mDistanceSensor DistanceSensor;
    private boolean FirstRun;
    private long StartTime;
    private long EndTime;
    private double DistanceThreshold;
    public boolean RobotPresent;
    private double Power;
    private float DirectionDegrees;
    private double Inches;
    private int CurrentTick;


    public RobotDodge(Engine engine, StateConfiguration stateConfig, String stateConfigID) {
        this.engine = engine;
        StateConfig = stateConfig;
        StateConfigID = stateConfigID;
    }

    @Override
    public void init() {
        //DistanceSensor = engine.hardwareMap.get(Rev2mDistanceSensor.class, "distanceSensor");

        DistanceThreshold = StateConfig.get(StateConfigID).variable("thresholdCM");
        EndTime = StateConfig.get(StateConfigID).variable("time");

        //Init things from drive class
        super.init();

        //The State Config reads a file on the phone that contains the variables for our different
        //steps. This file can be modified to edit variables on the fly
        Inches = StateConfig.get(StateConfigID).variable("distance");
        Power = StateConfig.get(StateConfigID).variable("maxPower");
        DirectionDegrees = StateConfig.get(StateConfigID).variable("direction");


        engine.telemetry.addData("Initialized", StateConfigID);
        engine.telemetry.update();

    }

    @Override
    public void exec() throws InterruptedException {
        if (StateConfig.allow(StateConfigID)) {

            if (FirstRun) {
                RobotPresent =  (DistanceSensor.getDistance(DistanceUnit.CM) < DistanceThreshold);
                if (RobotPresent) {
                    DriveForwardLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    DriveForwardRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    DriveBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    DriveBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    DriveForwardLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                    DriveForwardRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                    DriveBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                    DriveBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                }
                FirstRun = false;
            }

            if (RobotPresent) {

                int RightTick = DriveForwardRight.getCurrentPosition();
                int LeftTick = DriveForwardLeft.getCurrentPosition();

                //since the power of the motors in certain situations may be zero, select whichever side
                //is larger for determining when to finish.
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

            } else {
                setFinished(true);
            }

            engine.telemetry.addData("Distance Cm", DistanceSensor.getDistance(DistanceUnit.CM));
            engine.telemetry.addData("Robot Present", RobotPresent);

        } else {
            engine.telemetry.addData("Skipping Step", StateConfigID);
            engine.telemetry.update();
            sleep(1000);
            setFinished(true);
        }
    }
}
