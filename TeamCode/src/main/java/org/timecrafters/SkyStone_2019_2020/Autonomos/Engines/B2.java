package org.timecrafters.SkyStone_2019_2020.Autonomos.Engines;

/**********************************************************************************************
 * Name: B2
 * Description: Grabs and Places Skystone for Blue Aliance
 **********************************************************************************************/


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
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.SkystoneSight;
import org.timecrafters.SkyStone_2019_2020.Autonomos.Subengines.B2Center;
import org.timecrafters.SkyStone_2019_2020.Autonomos.Subengines.B2Left;
import org.timecrafters.SkyStone_2019_2020.Autonomos.Subengines.B2Right;
import org.timecrafters.SkyStone_2019_2020.IMUInit;
import org.timecrafters.engine.Engine;

@Autonomous (name = "B2", group = "B")
public class B2 extends Engine {
    @Override
    public void setProcesses() {
        StateConfiguration stateConfiguration = new StateConfiguration();
        SkystoneSight skystoneSight = new SkystoneSight(this, stateConfiguration, "Bstone");
        addState(new IMUInit(this));

        //Positions robot in front of the first set of stones.
        addState(new DirectionDrive(this, stateConfiguration, "B2a"));
        addState(new DirectionDrive(this, stateConfiguration, "B2b"));

        //Identifies the Position of Skystone and moves into position to grab it
        addState(skystoneSight);
        addSubEngine(new B2Left(this, skystoneSight, stateConfiguration));
        addSubEngine(new B2Center(this, skystoneSight, stateConfiguration));
        addSubEngine(new B2Right(this, skystoneSight, stateConfiguration));


        addState(new Arms(this, stateConfiguration,"B2d"));
        addState(new Crane(this, stateConfiguration, "B2d_crane"));
        addState(new DirectionDrive(this, stateConfiguration, "B2e"));
        addState(new LiftZero(this, stateConfiguration, "B2f"));
        addState(new Arms(this, stateConfiguration, "B2g"));
        addState(new GripRollers(this, stateConfiguration, "B2h"));
        addState(new DirectionDrive(this, stateConfiguration, "B2i"));
        addState(new Face(this, stateConfiguration, "B2j"));
        addState(new DirectionDrive(this, stateConfiguration, "B2k"));
        addThreadedState(new Crane(this, stateConfiguration, "B2k_crane"));
        addState(new Face(this, stateConfiguration, "B2l"));
        addState(new Lift(this, stateConfiguration, "B2m"));
        addState(new DirectionDrive(this, stateConfiguration, "B2n"));
        addState(new Arms(this, stateConfiguration, "B2o"));
        addState(new Lift(this, stateConfiguration, "B2p"));
        addState(new Arms(this, stateConfiguration, "B2q"));
        addState(new DirectionDrive(this, stateConfiguration, "B2r"));
        addState(new Face(this, stateConfiguration, "B2s"));
        addState(new Lift(this, stateConfiguration, "B2s_lower"));
        addState(new DirectionDrive(this, stateConfiguration, "B2t"));
        addState(new DirectionDrive(this, stateConfiguration, "B2u"));
    }
}
