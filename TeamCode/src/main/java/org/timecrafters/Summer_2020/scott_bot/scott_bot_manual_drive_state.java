package org.timecrafters.Summer_2020.scott_bot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

public class scott_bot_manual_drive_state extends State {

    public scott_bot_manual_drive_state (Engine engine) { this.engine = engine;}

    private DcMotor pLeftDrive;
    private DcMotor pRightDrive;
    private double dLeftDirection;
    private double dRightDirection;

    @Override
    public void init() {
        pLeftDrive = engine.hardwareMap.dcMotor.get("LeftWheel");
        pRightDrive = engine.hardwareMap.dcMotor.get("RightWheel");

        pLeftDrive.setDirection(DcMotorSimple.Direction.FORWARD);
        pRightDrive.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    @Override
    public void exec() throws InterruptedException {
        if(engine.gamepad1.y || engine.gamepad1.dpad_up){
            /*forward*/
            dLeftDirection=1;
            dRightDirection=1;
        }
        else if (engine.gamepad1.a || engine.gamepad1.dpad_down){
            /* reverse*/
            dLeftDirection=-1;
            dRightDirection=-1;
        }
        else if (engine.gamepad1.x || engine.gamepad1.dpad_left){
            /* rotate counter clock wise */
            dLeftDirection=-1;
            dRightDirection=1;
        }
        else if (engine.gamepad1.b || engine.gamepad1.dpad_right){
            /* rotate clock wise*/
            dLeftDirection=1;
            dRightDirection=-1;
        }
        else if (engine.gamepad1.left_stick_y > 0.2 || engine.gamepad1.left_stick_y < -0.2 ||
                engine.gamepad1.right_stick_y > 0.2 || engine.gamepad1.right_stick_y < -0.2) {
            dLeftDirection = -engine.gamepad1.left_stick_y;
            dRightDirection = -engine.gamepad1.right_stick_y;
        }
        else{
            /* stop */
            dLeftDirection=0;
            dRightDirection=0;
        }

        pLeftDrive.setPower(dLeftDirection*1);
        pRightDrive.setPower(dRightDirection*1);
    }
}


