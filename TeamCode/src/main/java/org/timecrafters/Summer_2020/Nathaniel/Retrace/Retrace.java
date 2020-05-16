package org.timecrafters.Summer_2020.Nathaniel.Retrace;

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
        DriveLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        DriveRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        StartTime = System.currentTimeMillis() - StartTime;
        CurrentAction = Actions.get(Actions.size() - 1);
        NextActionTime = Actions.get(Actions.size() - 2).TimeStamp;
        PreviousActionTime = 0;
        performAction();
    }

    @Override
    public void exec() {
        CurrentTime = System.currentTimeMillis() - StartTime;

        long timeSinceLastAction = CurrentTime - PreviousActionTime;

        if (CurrentAction.TimeStamp - NextActionTime >= timeSinceLastAction) {

            if (Actions.indexOf(CurrentAction) >= 1) {
                CurrentAction = Actions.get(Actions.indexOf(CurrentAction) - 1);
            } else {
                setHasFinished(true);
            }

            if (Actions.indexOf(CurrentAction) >= 2) {
                NextActionTime = Actions.get(Actions.indexOf(CurrentAction) - 2).TimeStamp;
            } else {
                NextActionTime = 50;
            }


            PreviousActionTime = CurrentTime;

            performAction();

            cyberarmEngine.telemetry.addData("Time Stamp", CurrentAction.TimeStamp);
            cyberarmEngine.telemetry.addData("Left Tick", CurrentAction.CurrentTickLeft);
            cyberarmEngine.telemetry.addData("Right Tick", CurrentAction.CurrentTickRight);
            cyberarmEngine.telemetry.addData("Left Power", CurrentAction.PowerLeft);
            cyberarmEngine.telemetry.addData("Right Power", CurrentAction.PowerRight);
            cyberarmEngine.telemetry.update();
        }
    }

    private void performAction() {
        DriveLeft.setPower(CurrentAction.PowerLeft);
        DriveRight.setPower(CurrentAction.PowerRight);
        DriveLeft.setTargetPosition(CurrentAction.CurrentTickLeft);
        DriveRight.setTargetPosition(CurrentAction.CurrentTickRight);
    }
}
