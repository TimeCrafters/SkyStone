package org.timecrafters.Nartaniel.Training;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class DriveForward extends State {

    private StateConfiguration StateConfig;
    private String ConfigID;
    private DcMotor DriveFrontRight;
    private DcMotor DriveFrontLeft;
    private DcMotor DriveBackRight;
    private DcMotor DriveBackLeft;
    private double Power;
    private int Ticks;
    private boolean HasRun;

    public DriveForward(Engine engine, StateConfiguration stateConfiguration, String configID) {
        this.engine = engine;
        StateConfig = stateConfiguration;
        ConfigID = configID;

    }

    @Override
    public void init() {
        Power = StateConfig.get(ConfigID).variable("power");
        Ticks = StateConfig.get(ConfigID).variable("ticks");

        DriveFrontRight = engine.hardwareMap.dcMotor.get("forwardRightDrive");
        DriveFrontLeft = engine.hardwareMap.dcMotor.get("forwardLeftDrive");
        DriveBackRight = engine.hardwareMap.dcMotor.get("backRightDrive");
        DriveBackLeft = engine.hardwareMap.dcMotor.get("backLeftDrive");

    }

    @Override
    public void exec() throws InterruptedException {
        if (!HasRun) {
            DriveFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            DriveFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            DriveBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            DriveBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            DriveFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            DriveFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            DriveBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            DriveBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            //Has run is set to true so that the encoders are only reset once
            HasRun = true;
        }

        int currentTick = Math.abs(DriveFrontLeft.getCurrentPosition());

        if (currentTick < Ticks) {
            DriveFrontRight.setPower(Power);
            DriveFrontLeft.setPower(Power);
            DriveBackRight.setPower(Power);
            DriveBackLeft.setPower(Power);
        } else {
            DriveFrontRight.setPower(0);
            DriveFrontLeft.setPower(0);
            DriveBackRight.setPower(0);
            DriveBackLeft.setPower(0);

            setFinished(true);
        }
    }
}
