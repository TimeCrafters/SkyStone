package org.timecrafters.Faith;

import com.qualcomm.robotcore.hardware.CRServo;

import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class myownattempttogetherthingy extends State {
    private CRServo lift;
    private CRServo lateral;
    private CRServo rotation;
    public myownattempttogetherthingy (Engine engine) {
        this.engine = engine;
    }

    public void init() {
        lift = engine.hardwareMap.crservo.get("lift");
        lateral = engine.hardwareMap.crservo.get("lateral");
        rotation = engine.hardwareMap.crservo.get("rotation");
    }

    @Override
    public void exec() throws InterruptedException {
    lift.setPower(-1);
    sleep(5000);
    lift.setPower(0);
    lateral.setPower(1);
    sleep(4000);
    lateral.setPower(0);
    setFinished(true);
    }
}
