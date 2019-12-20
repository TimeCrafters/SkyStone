package org.timecrafters.Nartaniel.Crane;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;


import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class CraneTestState extends State {

    private CRServo LateralServo;
    private CRServo LiftServo;
    private CRServo RotationServo;

    //Constructorb
    public CraneTestState(Engine engine) {
        this.engine = engine;
    }

    @Override
    public void init() {
        LateralServo = engine.hardwareMap.crservo.get("lateral");
        LiftServo = engine.hardwareMap.crservo.get("lift");
        RotationServo = engine.hardwareMap.crservo.get("rotation");
    }

    @Override
    public void exec() throws InterruptedException {

        if (engine.gamepad1.x){
            RotationServo.setPower(0.1);
        } else if (engine.gamepad1.b) {
            RotationServo.setPower(-0.1);
        } else {
            RotationServo.setPower(0);
        }

        LateralServo.setPower(engine.gamepad1.left_stick_y);

        LiftServo.setPower(engine.gamepad1.right_stick_y);
    }
}
