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
import org.timecrafters.SkyStone_2019_2020.IMUInit;
import org.timecrafters.engine.Engine;

@Autonomous (name = "R2", group = "R ")
public class R2 extends Engine {
    @Override
    public void setProcesses() {
        StateConfiguration stateConfiguration = new StateConfiguration();
        addState(new IMUInit(this));
        addState(new DirectionDrive(this, stateConfiguration, "R2a"));
        addState(new DirectionDrive(this, stateConfiguration, "R2b"));
        addState(new Crane(this, stateConfiguration, "R2c"));
        addState(new Arms(this, stateConfiguration,"R2d"));
        addState(new DirectionDrive(this, stateConfiguration, "R2e"));
        addState(new LiftZero(this, stateConfiguration, "R2f"));
        addState(new Arms(this, stateConfiguration, "R2g"));
        addState(new GripRollers(this, stateConfiguration, "R2h"));
        addState(new DirectionDrive(this, stateConfiguration, "R2i"));
        addState(new Face(this, stateConfiguration, "R2j"));
        addState(new DirectionDrive(this, stateConfiguration, "R2k"));
        addState(new Face(this, stateConfiguration, "R2l"));
        addState(new Lift(this, stateConfiguration, "R2m"));
        addState(new DirectionDrive(this, stateConfiguration, "R2n"));
        addState(new Arms(this, stateConfiguration, "R2o"));
        addState(new Lift(this, stateConfiguration, "R2p"));
        addState(new Arms(this, stateConfiguration, "R2q"));
        addState(new DirectionDrive(this, stateConfiguration, "R2r"));
        addState(new Face(this, stateConfiguration, "R2s"));
        addState(new DirectionDrive(this, stateConfiguration, "R2t"));
        addState(new DirectionDrive(this, stateConfiguration, "R2u"));
    }
}
