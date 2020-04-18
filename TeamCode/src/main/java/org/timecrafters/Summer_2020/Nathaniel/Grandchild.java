package org.timecrafters.Summer_2020.Nathaniel;

import org.cyberarm.engine.V2.CyberarmStateV2;

public class Grandchild extends CyberarmStateV2 {
    public double CoolVariable;

    public Grandchild() {
    }

    @Override
    public void exec() {
        cyberarmEngine.telemetry.addData("Grandchild Variable", CoolVariable);
        cyberarmEngine.telemetry.update();
    }
}
