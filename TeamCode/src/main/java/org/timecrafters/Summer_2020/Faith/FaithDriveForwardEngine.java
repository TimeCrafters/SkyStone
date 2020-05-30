package org.timecrafters.Summer_2020.Faith;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.engine.Engine;
@Autonomous(name = "Faith: Autonomous States",group = "Faith")
public class FaithDriveForwardEngine extends Engine {

    @Override
    public void setProcesses() {
        StateConfiguration stateConfiguration = new StateConfiguration();
       addState(new FaithDriveForwardState(this, stateConfiguration,"FaithDrive1"));
        addState(new Faithturnsstate(this, stateConfiguration, "FaithTurn1"));
        addState(new FaithDriveForwardState(this,stateConfiguration,"FaithDrive2"));
        addState(new Faithturnsstate(this, stateConfiguration, "FaithTurn2"));
        addState(new FaithDriveForwardState(this, stateConfiguration, "FaithDrive3"));
        addState(new Faithturnsstate(this, stateConfiguration, "FaithTurn3"));
        addState(new FaithDriveForwardState(this, stateConfiguration, "FaithDrive4"));
        addState(new Faithturnsstate(this, stateConfiguration, "FaithTurn4"));
    }


}

