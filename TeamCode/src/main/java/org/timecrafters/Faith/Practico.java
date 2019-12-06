package org.timecrafters.Faith;

import com.qualcomm.robotcore.hardware.CRServo;

import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class Practico extends State {
private CRServo bob;
private CRServo jerry;
private CRServo CRServo3;
private double Power;

public Practico (Engine engine) {
    this.engine = engine;
}

    @Override
    public void init() {
        bob = engine.hardwareMap.crservo.get("lateral");
        jerry = engine.hardwareMap.crservo.get("lift");
        CRServo3 = engine.hardwareMap.crservo.get("rotation");
    }

    @Override
    public void exec() throws InterruptedException {
        bob.setPower(engine.gamepad1.left_stick_x);
        jerry.setPower(engine.gamepad1.left_stick_y);
        CRServo3.setPower(engine.gamepad1.right_stick_x * .1);
    }
}
