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

    private int iSpeedIncreaseState; // press +10% ... 0 = waiting for press, 1 = waiting for release
    private int iSpeedDecreaseState; // press -10% ... 0 = waiting for press, 1 = waiting for release
    private double dDrivePowerRatio; // limit the amount of power to the drive motors

    @Override
    public void init() {
        pLeftDrive = engine.hardwareMap.dcMotor.get("LeftWheel");
        pRightDrive = engine.hardwareMap.dcMotor.get("RightWheel");

        pLeftDrive.setDirection(DcMotorSimple.Direction.FORWARD);
        pRightDrive.setDirection(DcMotorSimple.Direction.REVERSE);

        iSpeedDecreaseState = 0;
        iSpeedIncreaseState = 0;
        dDrivePowerRatio = 1;
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

        switch (iSpeedIncreaseState) {
            case 0:
                if(engine.gamepad1.right_bumper){
                    iSpeedIncreaseState = 1;
                }
                break;
            case 1:
                if(!engine.gamepad1.right_bumper){
                    iSpeedIncreaseState = 0;
                    if(dDrivePowerRatio <= .9) {
                        dDrivePowerRatio += .1;
                    }
                }
                break;
        }

        switch (iSpeedDecreaseState) {
            case 0:
                if(engine.gamepad1.left_bumper){
                    iSpeedDecreaseState = 1;
                }
                break;
            case 1:
                if(!engine.gamepad1.left_bumper){
                    iSpeedDecreaseState = 0;
                    if(dDrivePowerRatio >= .2) {
                        dDrivePowerRatio -= .1;
                    }
                }
                break;
        }

        pLeftDrive.setPower(dLeftDirection*dDrivePowerRatio);
        pRightDrive.setPower(dRightDirection*dDrivePowerRatio);

        engine.telemetry.addData("drive power ..... ", (int)(dDrivePowerRatio*10));
        engine.telemetry.update();
    }
}


