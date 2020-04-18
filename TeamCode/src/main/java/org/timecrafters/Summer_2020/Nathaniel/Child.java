package org.timecrafters.Summer_2020.Nathaniel;

import org.cyberarm.engine.V2.CyberarmStateV2;

public class Child extends CyberarmStateV2 {
    private Grandchild grandchildState;

    @Override
    public void init() {
        addParallelState(new Grandchild());
    }

    @Override
    public void exec() {


        Grandchild grandchild = (Grandchild) children.get(0);
        grandchild.CoolVariable = cyberarmEngine.gamepad1.right_trigger;
    }
}
