package org.timecrafters.Faith;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class RotationState extends State {

    private Servo Rotate;
    private double Power;
    private long Time;
    private double position;

    public RotationState(Engine engine, double power, long time, double position) {
        this.engine = engine;
        Power = power;
        Time = time;
        this.position = position;
    }

    @Override
    public void init() {
        Rotate = engine.hardwareMap.servo.get("rotation");
    }

    @Override
    public void exec() throws InterruptedException {
        Rotate.setPosition(position);
        sleep(Time);
        setFinished(true);
    }
}
