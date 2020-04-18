package org.timecrafters.Summer_2020.Nathaniel;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.cyberarm.NeXT.StateConfiguration;
import org.cyberarm.engine.V2.CyberarmEngineV2;

@TeleOp (name = "NRP: Variable Test", group = "NRP")
public class CyberarmEngineTest extends CyberarmEngineV2 {

    @Override
    public void setup() {
        StateConfiguration stateConfiguration = new StateConfiguration();
        addState(new GenericDrive(stateConfiguration, "S1"));
        addState(new ButtonPathChooser(stateConfiguration));
        addState(new GenericDrive(stateConfiguration, "S3"));

    }
}
