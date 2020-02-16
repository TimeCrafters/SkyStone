package org.timecrafters.thomas_thingy;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class teleop_state extends State {

    private DcMotor frontleft;
private DcMotor frontright;
private DcMotor backright;
private DcMotor backleft;
private DcMotor cranesidemovement;
private DcMotor craneinoutmovement;
    public teleop_state(Engine engine ) {
        this.engine=engine;

    }

    @Override
    public void init() {
        frontright=engine.hardwareMap.dcMotor.get("forwardRightDrive");
        frontleft=engine.hardwareMap.dcMotor.get("forwardLeftDrive");
        backleft=engine.hardwareMap.dcMotor.get("backLeftDrive");
        backright=engine.hardwareMap.dcMotor.get("backLeftDrive");
        craneinoutmovement=engine.hardwareMap.dcMotor.get("craneY");
        cranesidemovement=engine.hardwareMap.dcMotor.get("craneX");
    }

    @Override
    public void exec() throws InterruptedException {
        frontleft.setPower(-engine.gamepad1.left_stick_y);
        backleft.setPower(-engine.gamepad1.left_stick_y);
        frontright.setPower(engine.gamepad1.right_stick_y);
        backright.setPower(engine.gamepad1.right_stick_y);
        if (engine.gamepad1.dpad_up){
            craneinoutmovement.setPower(-1);
        }else if(engine.gamepad1.dpad_down){
            craneinoutmovement.setPower(1);
        } else {craneinoutmovement.setPower(0);}
if (engine.gamepad1.dpad_left){cranesidemovement.setPower(1);}else if(engine.gamepad1.dpad_right){cranesidemovement.setPower(-1);}else{cranesidemovement.setPower(0);}

    }
}
