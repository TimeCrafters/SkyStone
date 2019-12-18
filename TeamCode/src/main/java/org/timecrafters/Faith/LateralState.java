package org.timecrafters.Faith;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class LateralState extends State {

private CRServo Lateral;
private  CRServo Lift;
private double Power;
private long Time;

    public LateralState(Engine engine, double power, long time) {
        this.engine = engine;
        Power = power;
        Time = time;
    }

    @Override
    public void init() {
Lateral = engine.hardwareMap.crservo.get("lateral");
Lateral.setDirection(CRServo.Direction.REVERSE);
Lift = engine.hardwareMap.crservo.get("lift");
Lift.setDirection(CRServo.Direction.REVERSE);
    }

    @Override
    public void exec() throws InterruptedException {
Lateral.setPower(Power);
Lift.setPower(-Power);
sleep(Time);
Lateral.setPower(0);
Lift.setPower(0);
setFinished(true);
    }
}
