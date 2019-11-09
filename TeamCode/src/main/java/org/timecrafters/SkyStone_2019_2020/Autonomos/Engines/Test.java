package org.timecrafters.SkyStone_2019_2020.Autonomos.Engines;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.Face;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.Fingers;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.LiftZero;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.RampDrive;
import org.timecrafters.SkyStone_2019_2020.IMUInit;
import org.timecrafters.engine.Engine;

@TeleOp(name = "Testing",group = "Testing")
public class Test extends Engine {
    @Override
    public void setProcesses() {
        StateConfiguration stateConfiguration = new StateConfiguration();
        addState(new IMUInit(this));
        addState(new Face(this, stateConfiguration, "Tface"));
        addState(new Face(this, stateConfiguration, "Tface2"));
        addState(new Face(this, stateConfiguration, "Tface"));
        addState(new Fingers(this, stateConfiguration, "Tfinger"));
        addState(new Fingers(this, stateConfiguration, "Tfinger2"));
        addState(new RampDrive(this, stateConfiguration, "B1a"));
        // addState(new LiftZero(this, stateConfiguration, "TliftZero"));
    }
}
