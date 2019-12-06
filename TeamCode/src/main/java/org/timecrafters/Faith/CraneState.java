package org.timecrafters.Faith;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class CraneState extends State {

    private CRServo Thingy;

    public CraneState(Engine engine) {
        this.engine = engine;
    }

    @Override
    public void init() {
        Thingy = engine.hardwareMap.crservo.get("lift");
    }

    @Override
    public void exec() throws InterruptedException {
        Thingy.setPower(0.5);
        sleep(1000);
        Thingy.setPower(0);
        setFinished(true);
    }
}
