package org.timecrafters.Summer_2020.Thomas_summer2020;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.timecrafters.SkyStone_2019_2020.TeleOp.TeleOpState;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class state extends State {

    private DcMotor CraneX;
    private DcMotor CraneY;
    private Servo ArmRight;
    private boolean GrabberClosed;
    private boolean ArmTogglePrevious;

    public state(Engine engine ) {
        this.engine = engine;

    }

    @Override
    public void init() {
       CraneY =engine.hardwareMap.dcMotor.get("craneY");
       CraneX=engine.hardwareMap.dcMotor.get("craneX");
       ArmRight=engine.hardwareMap.servo.get("armRight");
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

    }
}
