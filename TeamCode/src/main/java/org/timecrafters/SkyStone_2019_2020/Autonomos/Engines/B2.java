package org.timecrafters.SkyStone_2019_2020.Autonomos.Engines;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.Arms;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.Crane;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.DirectionDrive;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.Face;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.GripRollers;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.Lift;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.LiftZero;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.RampDrive;
import org.timecrafters.SkyStone_2019_2020.IMUInit;
import org.timecrafters.engine.Engine;

@Autonomous (name = "B2", group = "B")
public class B2 extends Engine {
    @Override
    public void setProcesses() {
        StateConfiguration stateConfiguration = new StateConfiguration();
        addState(new IMUInit(this));
        addState(new DirectionDrive(this, stateConfiguration, "B2a"));
        addState(new DirectionDrive(this, stateConfiguration, "B2b"));
        addState(new Crane(this, stateConfiguration, "B2c"));
        addState(new Arms(this, stateConfiguration,"B2d"));
        addState(new DirectionDrive(this, stateConfiguration, "B2e"));
        addState(new LiftZero(this, stateConfiguration, "B2f"));
        addState(new Arms(this, stateConfiguration, "B2g"));
        addState(new GripRollers(this, stateConfiguration, "B2h"));
        addState(new DirectionDrive(this, stateConfiguration, "B2i"));
        addState(new Face(this, stateConfiguration, "B2j"));
        addState(new DirectionDrive(this, stateConfiguration, "B2k"));
        addState(new Face(this, stateConfiguration, "B2l"));
        addState(new Lift(this, stateConfiguration, "B2m"));
        addState(new DirectionDrive(this, stateConfiguration, "B2n"));
        addState(new Arms(this, stateConfiguration, "B2o"));
        addState(new Lift(this, stateConfiguration, "B2p"));
        addState(new Arms(this, stateConfiguration, "B2q"));
        addState(new DirectionDrive(this, stateConfiguration, "B2r"));
        addState(new Face(this, stateConfiguration, "B2s"));
        addState(new DirectionDrive(this, stateConfiguration, "B2t"));
        addState(new DirectionDrive(this, stateConfiguration, "B2u"));
    }
}
