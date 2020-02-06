package org.timecrafters.SkyStone_2019_2020.Autonomos.Engines;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.Crane;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.DirectionDrive;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.Face;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.FoundationClamp;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.LiftZero;
import org.timecrafters.SkyStone_2019_2020.IMUInit;
import org.timecrafters.engine.Engine;

@Autonomous (name = "B0", group = "Blue")
public class B0 extends Engine {
    @Override
    public void setProcesses() {
        StateConfiguration stateConfiguration = new StateConfiguration();

        addState(new IMUInit(this));
        addState(new DirectionDrive(this, stateConfiguration, "B0a"));
        addThreadedState(new Crane(this, stateConfiguration,"B0b"));
        addState(new LiftZero(this, stateConfiguration, "B0c"));
        addState(new DirectionDrive(this, stateConfiguration, "B0d"));
        addState(new DirectionDrive(this, stateConfiguration, "B0e"));
    }
}
