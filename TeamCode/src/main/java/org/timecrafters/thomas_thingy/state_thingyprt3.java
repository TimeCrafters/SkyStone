package org.timecrafters.thomas_thingy;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class state_thingyprt3 extends State {
    private Servo swingservo;
    private double power;
    private long time ;
    private double position;

    state_thingyprt3(Engine engine, long time,double position){
        this.engine=engine;
        this.time=time;
//        this.power=power;
        this.position=position;
    }

    @Override
    public void init() {
        swingservo=engine.hardwareMap.servo.get("rotation");

    }

    @Override
    public void exec() throws InterruptedException {
        swingservo.setPosition(position);
        sleep(time);
        setFinished(true);
    }
}
