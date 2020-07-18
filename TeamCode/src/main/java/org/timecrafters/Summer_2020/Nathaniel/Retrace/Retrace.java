package org.timecrafters.Summer_2020.Nathaniel.Retrace;

import android.util.Log;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.cyberarm.engine.V2.CyberarmStateV2;

import java.util.ArrayList;

public class Retrace extends CyberarmStateV2 {

    private long StartTime;
    private long CurrentTime;
    private DcMotor DriveLeft;
    private DcMotor DriveRight;
    private NRPAction CurrentAction;
    private long NextActionTime;
    private long PreviousActionTime;
    private long TotalRecordedTime;
    private ArrayList<NRPAction> Actions;

    public Retrace(ArrayList<NRPAction> actions) {
        Actions = actions;
    }

    @Override
    public void init() {
        DriveLeft = cyberarmEngine.hardwareMap.dcMotor.get("leftDrive");
        DriveRight = cyberarmEngine.hardwareMap.dcMotor.get("rightDrive");
        DriveRight.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    @Override
    public void start() {
        StartTime = System.currentTimeMillis();
        CurrentAction = Actions.get(Actions.size() - 1);
        PreviousActionTime = 0;
        performAction();
        DriveLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        DriveRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    @Override
    public void exec() {
        CurrentTime = System.currentTimeMillis() - StartTime;

        long timeSinceLastAction = CurrentTime - PreviousActionTime;



        if (timeSinceLastAction >= CurrentAction.TimeStamp) {
            Log.i("RecordRetrace", "Performed Action");


            if (Actions.indexOf(CurrentAction) >= 1) {
                CurrentAction = Actions.get(Actions.indexOf(CurrentAction) - 1);
                Log.i("RecordRetrace", "Not Finished");
            } else {
                Log.i("RecordRetrace", "Finished");
                setHasFinished(true);
            }

            Log.i("RecordRetrace", "Action Duration "+CurrentAction.TimeStamp);

            PreviousActionTime = CurrentTime;

            performAction();

            cyberarmEngine.telemetry.addData("Time Stamp", CurrentAction.TimeStamp);
            cyberarmEngine.telemetry.addData("Left Tick", CurrentAction.CurrentTickLeft);
            cyberarmEngine.telemetry.addData("Right Tick", CurrentAction.CurrentTickRight);
            cyberarmEngine.telemetry.addData("Left Power", CurrentAction.PowerLeft);
            cyberarmEngine.telemetry.addData("Right Power", CurrentAction.PowerRight);
            cyberarmEngine.telemetry.update();
        } else {
            Log.i("RecordRetrace", "Current Action:( duration :"+ CurrentAction.TimeStamp+", timeSinceLast Action :"+timeSinceLastAction+" index :"+Actions.indexOf(CurrentAction)+")");
        }
    }

    private void performAction() {
        DriveLeft.setPower(CurrentAction.PowerLeft);
        DriveRight.setPower(CurrentAction.PowerRight);
        DriveLeft.setTargetPosition(CurrentAction.CurrentTickLeft);
        DriveRight.setTargetPosition(CurrentAction.CurrentTickRight);
    }

    @Override
    public void telemetry() {
        cyberarmEngine.telemetry.addData("Action Size", Actions.size());
        //cyberarmEngine.telemetry.addData("Current Action Timestamp", CurrentAction.TimeStamp);
    }
}
