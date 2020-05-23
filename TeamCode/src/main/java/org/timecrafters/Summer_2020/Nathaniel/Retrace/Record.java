package org.timecrafters.Summer_2020.Nathaniel.Retrace;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.cyberarm.engine.V2.CyberarmStateV2;

import java.util.ArrayList;

public class Record extends CyberarmStateV2 {

    private long StartTime;
    private long CurrentTime;
    private DcMotor DriveLeft;
    private DcMotor DriveRight;
    private int EncoderPrevLeft;
    private int EncoderPrevRight;
    public ArrayList<NRPAction> Actions = new ArrayList<NRPAction>();


    @Override
    public void init() {
        DriveLeft = cyberarmEngine.hardwareMap.dcMotor.get("leftDrive");
        DriveRight = cyberarmEngine.hardwareMap.dcMotor.get("rightDrive");
        DriveRight.setDirection(DcMotorSimple.Direction.REVERSE);
        DriveLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        DriveRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


    }

    @Override
    public void start() {
        DriveLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        DriveRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        StartTime = System.currentTimeMillis();
        Actions.clear();
        Actions.add(new NRPAction(StartTime, 0, 0, 0, 0));
        EncoderPrevLeft = 0;
        EncoderPrevRight = 0;
    }

    @Override
    public void exec() {
        int encoderCurrentRight = DriveRight.getCurrentPosition();
        int encoderCurrentLeft = DriveLeft.getCurrentPosition();
        double powerRight = 0.3 * cyberarmEngine.gamepad1.right_stick_y;
        double powerLeft = 0.3 * cyberarmEngine.gamepad1.left_stick_y;


        DriveLeft.setPower(powerLeft);
        DriveRight.setPower(powerRight);

        if (encoderCurrentLeft != EncoderPrevLeft || encoderCurrentRight != EncoderPrevRight) {
            EncoderPrevLeft = encoderCurrentLeft;
            EncoderPrevRight = encoderCurrentRight;
            NRPAction nrpAction = new NRPAction(System.currentTimeMillis() - StartTime, powerLeft, powerRight, encoderCurrentLeft, encoderCurrentRight);
            Actions.add(nrpAction);
            cyberarmEngine.telemetry.addData("Time Stamp", nrpAction.TimeStamp);
            cyberarmEngine.telemetry.addData("Left Tick", nrpAction.CurrentTickLeft);
            cyberarmEngine.telemetry.addData("Right Tick", nrpAction.CurrentTickRight);
            cyberarmEngine.telemetry.addData("Left Power", nrpAction.PowerLeft);
            cyberarmEngine.telemetry.addData("Right Power", nrpAction.PowerRight);
            cyberarmEngine.telemetry.update();
        }

    }
}
