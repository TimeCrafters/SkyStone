package org.timecrafters.Nartaniel.Testing;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class StoneCollectorTest extends State {

    private Servo ClawServoRight;
    private Servo ClawServoLeft;
    private CRServo GripServoRight;
    private CRServo GripServoLeft;

    public StoneCollectorTest(Engine engine) {
        this.engine = engine;
    }

    @Override
    public void init() {
        ClawServoLeft = engine.hardwareMap.servo.get("clawLeft");
        ClawServoRight = engine.hardwareMap.servo.get("clawRight");
        GripServoLeft = engine.hardwareMap.crservo.get("gripLeft");
        GripServoRight = engine.hardwareMap.crservo.get("gripRight");
        GripServoRight.setDirection(CRServo.Direction.REVERSE);
        ClawServoRight.setDirection(Servo.Direction.REVERSE);
    }

    @Override
    public void exec() throws InterruptedException {

        //Prototype Code

        if (engine.gamepad1.y) {
            GripServoLeft.setPower(1.0);
            GripServoRight.setPower(1.0);
        } else if (engine.gamepad1.a) {
            GripServoLeft.setPower(-1.0);
            GripServoRight.setPower(-1.0);
        } else{
            GripServoLeft.setPower(0.0);
            GripServoRight.setPower(0.0);
        }

        if (engine.gamepad1.b) {
            ClawServoLeft.setPosition(0.15);
            ClawServoRight.setPosition(0.15);
        }

        if (engine.gamepad1.x) {
            ClawServoLeft.setPosition(0);
            ClawServoRight.setPosition(0);
        }
    }
}
