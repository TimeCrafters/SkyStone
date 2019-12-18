package org.timecrafters.Faith;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class Lift extends State {

private CRServo LiftServo;
private double Power;
private long Time;

    public Lift(Engine engine,double power, long time) {
        this.engine = engine;
        Power = power;
        Time = time;
    }

    @Override
    public void init() {
        LiftServo = engine.hardwareMap.crservo.get("lift");
        LiftServo.setDirection(CRServo.Direction.REVERSE);
    }

    @Override
    public void exec() throws InterruptedException {
       LiftServo.setPower(Power);
       sleep(Time);
       LiftServo.setPower(0);
       setFinished(true);
    }
}
