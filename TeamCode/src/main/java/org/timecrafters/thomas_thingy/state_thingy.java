package org.timecrafters.thomas_thingy;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.CRServoImpl;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class state_thingy extends State {

    private CRServo liftservo;
    private double power;
    private long time;

    public state_thingy(Engine engine,double power,long time) {
        this.engine=engine;
        this.power=power;
        this.time=time;
    }

    @Override
    public void init() {
        liftservo=engine.hardwareMap.crservo.get("lift");
        liftservo.setDirection(CRServo.Direction.REVERSE);
    }

    @Override
    public void exec() throws InterruptedException {
        liftservo.setPower(power);
        sleep(time);
        liftservo.setPower(0);
        setFinished(true);
    }
}
