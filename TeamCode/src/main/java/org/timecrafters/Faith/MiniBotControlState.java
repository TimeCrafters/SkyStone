package org.timecrafters.Faith;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class MiniBotControlState extends State {

public MiniBotControlState (Engine engine) {this.engine = engine;}

private DcMotor Right;
private DcMotor Left;

    @Override
    public void init() {
        Right = engine.hardwareMap.dcMotor.get("Right");
        Left = engine.hardwareMap.dcMotor.get("Left");
        Right.setPower(0);
        Left.setPower(0);
        Left.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    @Override
    public void exec() throws InterruptedException {
        Left.setPower(engine.gamepad1.left_stick_y);
        Right.setPower(engine.gamepad1.right_stick_y);
    }
}
