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
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.DirectionDriveAbsoute;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.Face;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.FaceActveCheck;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.GripRollers;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.Lift;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.LiftZero;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.RampDrive;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.SkystoneSight;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.SkystoneSightBlue;
import org.timecrafters.SkyStone_2019_2020.Autonomos.Subengines.B2Center;
import org.timecrafters.SkyStone_2019_2020.Autonomos.Subengines.B2Left;
import org.timecrafters.SkyStone_2019_2020.Autonomos.Subengines.B2Right;
import org.timecrafters.SkyStone_2019_2020.Autonomos.Subengines.B2iCenter;
import org.timecrafters.SkyStone_2019_2020.Autonomos.Subengines.B2iLeft;
import org.timecrafters.SkyStone_2019_2020.Autonomos.Subengines.B2iRight;
import org.timecrafters.SkyStone_2019_2020.IMUInit;
import org.timecrafters.engine.Engine;

@Autonomous (name = "B2", group = "Blue")
public class B2 extends Engine {
    @Override
    public void setProcesses() {
        StateConfiguration stateConfiguration = new StateConfiguration();
        SkystoneSightBlue skystoneSight = new SkystoneSightBlue(this, stateConfiguration, "B2b_stone");
        addState(new IMUInit(this));

        //Positions robot in front of the first set of stones.
        addState(new DirectionDrive(this, stateConfiguration, "B2a"));
        addThreadedState(new Lift(this, stateConfiguration, "R2b_lift"));
        addState(new DirectionDrive(this, stateConfiguration, "B2b"));
        addThreadedState(new Arms(this, stateConfiguration,"B2d"));
        addState(new FaceActveCheck(this, stateConfiguration, "B2b_face"));


        //Identifies the Position of Skystone and moves into position to grab it
        addState(skystoneSight);

        addSubEngine(new B2Left(this, skystoneSight, stateConfiguration));
        addSubEngine(new B2Center(this, skystoneSight, stateConfiguration));
        addSubEngine(new B2Right(this, skystoneSight, stateConfiguration));


        addState(new Arms(this, stateConfiguration, "B2g"));

        //back up and turn towards build site
        addSubEngine(new B2iLeft(this, skystoneSight, stateConfiguration));
        addSubEngine(new B2iCenter(this, skystoneSight, stateConfiguration));
        addSubEngine(new B2iRight(this, skystoneSight, stateConfiguration));

        //Grabs Stone
        addState(new FaceActveCheck(  this, stateConfiguration, "B2j"));

        addState(new DirectionDrive(this, stateConfiguration, "B2k"));
        addThreadedState(new Crane(this, stateConfiguration, "B2k_crane"));

        addState(new FaceActveCheck( this, stateConfiguration, "B2l"));
        addThreadedState(new Lift(this, stateConfiguration, "B2m"));
        addState(new DirectionDrive(this, stateConfiguration, "B2n"));
        //Possible spot for threaded crane
        //addState(new Arms(this, stateConfiguration, "B2o"));
        //addState(new Lift(this, stateConfiguration, "B2p"));
        addState(new Arms(this, stateConfiguration, "B2q"));
        addState(new DirectionDrive(this, stateConfiguration, "B2r"));
        addState(new Face(this, stateConfiguration, "B2s"));
        addThreadedState(new Lift(this, stateConfiguration, "B2s_lower"));
        addState(new DirectionDrive(this, stateConfiguration, "B2t"));
        addState(new DirectionDrive(this, stateConfiguration, "B2u"));
    }
}
