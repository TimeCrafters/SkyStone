package org.timecrafters.Summer_2020.Nathaniel.Retrace;

import org.cyberarm.engine.V2.CyberarmStateV2;

public class RecordRetraceControl extends CyberarmStateV2 {

    private boolean Retracing;
    private Record RecordState = new Record();
    private Retrace RetraceState;

    @Override
    public void start() {
        //
        addParallelState(RecordState);
    }

    @Override
    public void exec() {
        if (cyberarmEngine.gamepad1.right_bumper && !Retracing) {
            children.get(children.indexOf(RecordState)).setHasFinished(true);
            Retracing = true;
            RetraceState = new Retrace(RecordState.Actions);
         addParallelState(RetraceState);
        }

        if (childrenHaveFinished() && Retracing) {
            children.remove(RecordState);
            addParallelState(RecordState);
            Retracing = false;
        }
    }
}
