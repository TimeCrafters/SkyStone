package org.timecrafters.Summer_2020.Thomas_summer2020;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class state_no2 extends State {


    private DcMotor CraneX;
    private DcMotor CraneY;
    private Servo ArmRight;
    private boolean GrabberClosed;
    private boolean ArmTogglePrevious;
    private DcMotor LiftRight;
    private DcMotor LiftLeft;


    public state_no2(Engine engine ) {
        this.engine = engine;

    }

    @Override
    public void init() {
        CraneY =engine.hardwareMap.dcMotor.get("craneY");
        CraneX=engine.hardwareMap.dcMotor.get("craneX");
        ArmRight=engine.hardwareMap.servo.get("armRight");
        LiftRight=engine.hardwareMap.dcMotor.get("liftRight");
        LiftLeft=engine.hardwareMap.dcMotor.get("liftLeft");
    }


    @Override
    public void exec() throws InterruptedException {
        if (engine.gamepad2.dpad_right) {
            CraneX.setPower(0.6);
        } else if (engine.gamepad2.dpad_left) {
            CraneX.setPower(-0.6);
        } else {
            CraneX.setPower(0);
        }
        if (engine.gamepad2.dpad_up) {
            CraneY.setPower(1);
        } else if (engine.gamepad2.dpad_down)  {
            CraneY.setPower(-1);
        } else {
            CraneY.setPower(0);
        }

        boolean ArmButton = engine.gamepad2.right_bumper;

        if (ArmButton && ArmButton != ArmTogglePrevious && GrabberClosed) {
            ArmRight.setPosition(0.3);

            GrabberClosed = false;
        } else if (ArmButton && ArmButton != ArmTogglePrevious && !GrabberClosed) {
            ArmRight.setPosition(0.8);
            GrabberClosed = true;
        }
        ArmTogglePrevious = ArmButton;
        LiftLeft.setDirection(DcMotor.Direction.REVERSE);
        if (engine.gamepad2.left_stick_y > 0 ) {
            LiftRight.setPower(0.5);
            LiftLeft.setPower(0.5);
        } else if ( engine.gamepad2.left_stick_y < 0 ){
            LiftRight.setPower(-1);
            LiftLeft.setPower(-1);
        } else {
            LiftRight.setPower(-.1);
            LiftLeft.setPower(-.1);
        }

    }
}
