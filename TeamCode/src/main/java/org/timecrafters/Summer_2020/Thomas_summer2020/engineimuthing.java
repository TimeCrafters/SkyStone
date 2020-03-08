package org.timecrafters.Summer_2020.Thomas_summer2020;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@Autonomous (name= "yeeet")
public class engineimuthing extends engine {


    @Override
    public void setProcesses() {
        addState(new stateimuthing(this));
    }
}

