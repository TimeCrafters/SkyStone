package org.timecrafters.Nartaniel.Testing;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class TestTeleOp extends State {

    private DcMotor Motor;

    public TestTeleOp(Engine engine) {
        this.engine = engine;
    }

    public void init() {
        Motor = engine.hardwareMap.dcMotor.get("Motor");
    }

    @Override
    public void exec() throws InterruptedException {
        if (engine.gamepad1.a) {
            engine.telemetry.addLine("Hello!");
        }
        Motor.setPower(engine.gamepad2.left_stick_y);
    }
}
