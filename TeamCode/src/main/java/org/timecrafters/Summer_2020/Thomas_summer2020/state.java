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

//0123456789101112131415161718192021222324252627282930313233343536373839404142434445464748495051525354555657585960616263646566676869707172737475767778798081828384858687888990919293949596979899100101102103104105106107108109110111112113114115116117118119120121122123124125126127128129130131132133134135136137138139141142143144145146147148149150151152153154155156157158159160161162163164165166167168169170171172173174
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
