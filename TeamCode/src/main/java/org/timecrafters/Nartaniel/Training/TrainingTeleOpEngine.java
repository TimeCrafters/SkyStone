package org.timecrafters.Nartaniel.Training;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.engine.Engine;

@TeleOp (name = "Training TeleOp ")
public class TrainingTeleOpEngine extends Engine {
    @Override
    public void setProcesses() {
        StateConfiguration stateConfiguration = new StateConfiguration();

        addState(new DriveForward(this, stateConfiguration, "TrainingForward"));
    }
}
