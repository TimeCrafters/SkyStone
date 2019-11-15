package org.timecrafters.SkyStone_2019_2020.Autonomos.Engines;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.Arms;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.Crane;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.Fingers;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.DirectionDrive;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.Face;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.Lift;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.LiftZero;
import org.timecrafters.engine.Engine;

@Autonomous (name = "R1", group = "R")
public class R1 extends Engine {
    @Override
    public void setProcesses() {
        StateConfiguration stateConfiguration = new StateConfiguration();
        addState(new DirectionDrive(this, stateConfiguration, "R1a"));
        addState(new DirectionDrive(this, stateConfiguration, "R1b"));
        addState(new Fingers(this, stateConfiguration, "R1c"));
        addState(new DirectionDrive(this, stateConfiguration, "R1c_move"));
        addState(new Face(this, stateConfiguration, "R1d"));
        addState(new DirectionDrive(this, stateConfiguration, "R1e"));
        addState(new Crane(this, stateConfiguration,"R1e_carriage_out"));
        addState(new LiftZero(this, stateConfiguration, "R1e_lower"));
        addState(new Fingers(this, stateConfiguration, "R1f"));
        addState(new DirectionDrive(this, stateConfiguration, "R1g"));
        addState(new DirectionDrive(this, stateConfiguration, "R1h"));
    }
}
