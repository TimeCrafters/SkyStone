package org.timecrafters.thomas_thingy;

import com.qualcomm.robotcore.hardware.CRServo;

import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class state_prt2 extends State {
    private CRServo inoutservo;
    private double power;
    private long time;
    private CRServo liftservo;

    public state_prt2(Engine engine, double power, long time) {
        this.engine=engine;
        this.time=time;
        this.power=power;
    }

    @Override
    public void init() {
        inoutservo=engine.hardwareMap.crservo.get("lateral");
        liftservo=engine.hardwareMap.crservo.get("lift");
    }

    @Override
    public void exec() throws InterruptedException {
       inoutservo.setPower(power);
       liftservo.setPower(power);
       sleep(time);
       inoutservo.setPower(0);
       liftservo.setPower(0);
       setFinished(true);
    }
}

