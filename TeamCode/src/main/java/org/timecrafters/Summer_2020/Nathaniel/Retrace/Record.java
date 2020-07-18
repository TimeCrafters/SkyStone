package org.timecrafters.Summer_2020.Nathaniel.Retrace;

import android.util.Log;

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
    private double powerRight;
    private double powerLeft;
    public ArrayList<NRPAction> Actions = new ArrayList<NRPAction>();


    @Override
    public void init() {
        DriveLeft = cyberarmEngine.hardwareMap.dcMotor.get("leftDrive");
        DriveRight = cyberarmEngine.hardwareMap.dcMotor.get("rightDrive");
        DriveRight.setDirection(DcMotorSimple.Direction.REVERSE);
        DriveLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        DriveRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Log.i("RecordRetrace", "ran init");

    }

    @Override
    public void start() {
        DriveLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        DriveRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        StartTime = System.currentTimeMillis();
        Actions.clear();
        Actions.add(new NRPAction(0, 0, 0, 0, 0));
        EncoderPrevLeft = 0;
        EncoderPrevRight = 0;
        Log.i("RecordRetrace", "Ran start");
    }

    @Override
    public void exec() {
        //Log.i("RecordRetrace", "Ran exec");
        int encoderCurrentRight = DriveRight.getCurrentPosition();
        int encoderCurrentLeft = DriveLeft.getCurrentPosition();
        powerRight = 0.3 * cyberarmEngine.gamepad1.right_stick_y;
        powerLeft = 0.3 * cyberarmEngine.gamepad1.left_stick_y;
        cyberarmEngine.telemetry.addData("Left Power", powerLeft);
        cyberarmEngine.telemetry.addData("Right Power", powerRight);
        cyberarmEngine.telemetry.update();

        DriveLeft.setPower(powerLeft);
        DriveRight.setPower(powerRight);

        CurrentTime = System.currentTimeMillis();

        if (encoderCurrentLeft != EncoderPrevLeft || encoderCurrentRight != EncoderPrevRight) {
            EncoderPrevLeft = encoderCurrentLeft;
            EncoderPrevRight = encoderCurrentRight;
            NRPAction nrpAction = new NRPAction(CurrentTime - StartTime, powerLeft, powerRight, encoderCurrentLeft, encoderCurrentRight);
            Actions.add(nrpAction);
            StartTime = CurrentTime;
            cyberarmEngine.telemetry.addData("Time Stamp", nrpAction.TimeStamp);
            cyberarmEngine.telemetry.addData("Left Tick", nrpAction.CurrentTickLeft);
            cyberarmEngine.telemetry.addData("Right Tick", nrpAction.CurrentTickRight);
            cyberarmEngine.telemetry.addData("Left Power", nrpAction.PowerLeft);
            cyberarmEngine.telemetry.addData("Right Power", nrpAction.PowerRight);
            cyberarmEngine.telemetry.update();

        }

        setHasFinished(cyberarmEngine.gamepad1.left_bumper);
    }

    @Override
    public void telemetry() {

        cyberarmEngine.telemetry.addData("Controler Input Y", cyberarmEngine.gamepad1.right_stick_y);
        cyberarmEngine.telemetry.addData("Controler Input A", cyberarmEngine.gamepad1.a);
        cyberarmEngine.telemetry.addData("run time", runTime());
    }
}
