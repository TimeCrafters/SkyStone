package org.timecrafters.Summer_2020.Thomas_summer2020;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.SkyStone_2019_2020.IMUInit;
import org.timecrafters.engine.Engine;
@Autonomous(name = "thomas: drive path 2",group = "Thomas")
public class auto_drivePractice extends Engine {
    @Override
    public void setProcesses() {
        StateConfiguration stateConfiguration= new StateConfiguration();
        addState(new IMUInit(this));


        addState(new stateTurnInPlace(this, "T2AAstartTurn", stateConfiguration));
        addState(new drift_thing_state(this, stateConfiguration, "T2ABstartForward"));
        addState(new stateTurnInPlace(this, "T2AClineDoor", stateConfiguration));
        addState(new drift_thing_state(this, stateConfiguration, "T2ADdoorForward"));
        addState(new stateTurnInPlace(this, "T2AEturnPostdoor", stateConfiguration));
        addState(new drift_thing_state(this, stateConfiguration, "T2AFdrivePostdoor"));
        addState(new stateTurnInPlace(this, "T2AGturnToOpening", stateConfiguration));
        addState(new drift_thing_state(this, stateConfiguration, "T2AHdriveTOOpening"));
        addState(new stateTurnInPlace(this, "T2AIturnTrench", stateConfiguration));
        addState(new drift_thing_state(this, stateConfiguration, "T2AJdriveTrench"));
        addState(new stateTurnInPlace(this, "T2AKturnExit", stateConfiguration));
        addState(new drift_thing_state(this, stateConfiguration, "T2ALdriveExit"));
        addState(new stateTurnInPlace(this, "T2AMturnCouch", stateConfiguration));
        addState(new drift_thing_state(this, stateConfiguration, "T2ANdriveCouch"));
        addState(new stateTurnInPlace(this, "T2AOturnAlongCouch", stateConfiguration));
        addState(new drift_thing_state(this, stateConfiguration, "T2APdriveAlongCouch"));
        addState(new stateTurnInPlace(this, "T2AQturnToDoor", stateConfiguration));
        addState(new drift_thing_state(this, stateConfiguration, "T2ARdriveTodoor"));
        addState(new stateTurnInPlace(this, "T2ASturnToLeave", stateConfiguration));
        addState(new drift_thing_state(this, stateConfiguration, "T2ATdriveTroughDoor"));
        addState(new stateTurnInPlace(this, "T2AUturnToFinsih", stateConfiguration));
        addState(new drift_thing_state(this, stateConfiguration, "T2AVdriveToFinish"));


    }
}


