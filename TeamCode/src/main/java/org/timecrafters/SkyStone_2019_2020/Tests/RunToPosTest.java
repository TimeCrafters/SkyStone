package org.timecrafters.SkyStone_2019_2020.Tests;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class RunToPosTest extends State {

    private DcMotor Motor;

    public RunToPosTest(Engine engine) {
        this.engine = engine;
    }

    @Override
    public void init() {
        Motor = engine.hardwareMap.dcMotor.get("Motor");
        Motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Motor.setTargetPosition(1500);
        Motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

    }

    @Override
    public void exec() throws InterruptedException {

        if (engine.gamepad1.a) {
            Motor.setPower(engine.gamepad1.right_trigger);
        }



        if (engine.gamepad1.b) {
            Motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            Motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }

        engine.telemetry.addData("Trigger", engine.gamepad1.right_trigger);
        engine.telemetry.addData("Power", Motor.getPower());
        engine.telemetry.addData("Position", Motor.getCurrentPosition());
        engine.telemetry.addData("Finished", Motor.isBusy());
        engine.telemetry.update();
    }
}
