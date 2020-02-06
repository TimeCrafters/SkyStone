package org.timecrafters.SkyStone_2019_2020.Autonomos.Engines;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.Crane;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.DirectionDrive;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.LiftZero;
import org.timecrafters.SkyStone_2019_2020.IMUInit;
import org.timecrafters.engine.Engine;

@Autonomous (name = "R0", group = "Blue")
public class R0 extends Engine {
    @Override
    public void setProcesses() {
        StateConfiguration stateConfiguration = new StateConfiguration();

        addState(new IMUInit(this));
        addState(new DirectionDrive(this, stateConfiguration, "R0a"));
        addThreadedState(new Crane(this, stateConfiguration,"R0b"));
        addState(new LiftZero(this, stateConfiguration, "R0c"));
        addState(new DirectionDrive(this, stateConfiguration, "R0d"));
        addState(new DirectionDrive(this, stateConfiguration, "R0e"));
    }
}
