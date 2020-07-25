package org.timecrafters.Summer_2020.Nathaniel.Retrace;

import android.util.Log;

import org.cyberarm.engine.V2.CyberarmStateV2;

public class RecordRetraceControl extends CyberarmStateV2 {

    private boolean Retracing = false;
    protected Record RecordState;
    private Retrace RetraceState;

    public RecordRetraceControl(Record recordState) {
        RecordState = recordState;
    }

    @Override
    public void start() {

        Log.i("RecordRetrace", "ran Control");

    }

    @Override
    public void exec() {

        if (cyberarmEngine.gamepad1.left_bumper && !Retracing) {
            Log.i("RecordRetrace", "bumper");

            Retracing = true;
            RetraceState = new Retrace(RecordState.Actions);
         addParallelState(RetraceState);
        }

        if (childrenHaveFinished() && Retracing) {
            children.remove(RecordState);
            children.remove(RecordState);
            RecordState = new Record();
            addParallelState(RecordState);
            Retracing = false;
        }


    }

    @Override
    public void telemetry() {

    }
}
