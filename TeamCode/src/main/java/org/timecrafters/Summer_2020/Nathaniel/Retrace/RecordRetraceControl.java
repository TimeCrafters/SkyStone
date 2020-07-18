package org.timecrafters.Summer_2020.Nathaniel.Retrace;

import android.util.Log;

import org.cyberarm.engine.V2.CyberarmStateV2;

public class RecordRetraceControl extends CyberarmStateV2 {

    private boolean Retracing = false;
    private Record RecordState; //= new Record();
    private Retrace RetraceState;

    public RecordRetraceControl(Record recordState) {
        RecordState = recordState;
    }

    @Override
    public void start() {
        //
        Log.i("RecordRetrace", "ran Control");

    }

    @Override
    public void exec() {
        //Log.i("RecordRetrace", "ran Control Exec");
        if (cyberarmEngine.gamepad1.left_bumper && !Retracing) {
            Log.i("RecordRetrace", "bumper");
            //children.get(children.indexOf(RecordState)).setHasFinished(true);

            Retracing = true;
            RetraceState = new Retrace(RecordState.Actions);
         addParallelState(RetraceState);
        }

        boolean childrenFinished = childrenHaveFinished();
        if (childrenFinished && Retracing) {
            children.remove(RecordState);
            addParallelState(RecordState);
            Retracing = false;
        } else {
            Log.i("RecordRetrace", "Retracing :"+Retracing+"Children have finished :"+childrenFinished);
        }


    }

    @Override
    public void telemetry() {

        cyberarmEngine.telemetry.addData("containsRecordState", children.contains(RecordState));

    }
}
