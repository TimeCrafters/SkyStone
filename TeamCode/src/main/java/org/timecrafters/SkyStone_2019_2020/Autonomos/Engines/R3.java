package org.timecrafters.SkyStone_2019_2020.Autonomos.Engines;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.Arms;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.Crane;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.DirectionDrive;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.Face;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.FaceActveCheck;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.FoundationClamp;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.GripRollers;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.Lift;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.LiftZero;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.RobotDodge;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.SkystoneSight;
import org.timecrafters.SkyStone_2019_2020.Autonomos.Subengines.R2Center;
import org.timecrafters.SkyStone_2019_2020.Autonomos.Subengines.R2Left;
import org.timecrafters.SkyStone_2019_2020.Autonomos.Subengines.R2Right;
import org.timecrafters.SkyStone_2019_2020.Autonomos.Subengines.R2iCenter;
import org.timecrafters.SkyStone_2019_2020.Autonomos.Subengines.R2iLeft;
import org.timecrafters.SkyStone_2019_2020.Autonomos.Subengines.R2iRight;
import org.timecrafters.SkyStone_2019_2020.Autonomos.Subengines.R3Center;
import org.timecrafters.SkyStone_2019_2020.Autonomos.Subengines.R3Left;
import org.timecrafters.SkyStone_2019_2020.Autonomos.Subengines.R3Right;
import org.timecrafters.SkyStone_2019_2020.IMUInit;
import org.timecrafters.engine.Engine;

@Autonomous (name = "R3", group = "Red")
public class R3 extends Engine {
    @Override
    public void setProcesses() {
        StateConfiguration stateConfiguration = new StateConfiguration();
        SkystoneSight skystoneSight = new SkystoneSight(this, stateConfiguration, "R2b_stone");
        addState(new IMUInit(this));

        //Positions robot in front of the first set of stones.
        addState(new DirectionDrive(this, stateConfiguration, "R2a"));
        addState(new DirectionDrive(this, stateConfiguration, "R2b"));
        addState(new FaceActveCheck(this, stateConfiguration, "R2b_face"));
        addThreadedState(new Lift(this, stateConfiguration, "R2b_lift"));
        addThreadedState(new Arms(this, stateConfiguration, "R2d"));


        //Identifies the Position of Skystone and moves into position to grab it
        addState(skystoneSight);
        addSubEngine(new R2Left(this, skystoneSight, stateConfiguration));
        addSubEngine(new R2Center(this, skystoneSight, stateConfiguration));
        addSubEngine(new R2Right(this, skystoneSight, stateConfiguration));


        //addState(new Crane(this, stateConfiguration, "R3d"));


        addState(new Arms(this, stateConfiguration, "R2g"));

        //back up and turn towards build site

        addSubEngine(new R2iLeft(this, skystoneSight, stateConfiguration));
        addSubEngine(new R2iCenter(this, skystoneSight, stateConfiguration));
        addSubEngine(new R2iRight(this, skystoneSight, stateConfiguration));

        addState(new FaceActveCheck( this, stateConfiguration, "R2j"));

        //drive at varying speeds to end of the build site, then face the foundation
        addState(new DirectionDrive(this, stateConfiguration, "R3k"));
        addThreadedState(new Crane(this, stateConfiguration, "R2k_crane"));
//        addState(new DirectionDrive(this, stateConfiguration, "R3l"));
        addState(new DirectionDrive(this, stateConfiguration, "R3m"));
        addState(new FaceActveCheck(this, stateConfiguration, "R3n"));

        addThreadedState(new Lift(this, stateConfiguration, "R3o"));
        addState(new DirectionDrive(this, stateConfiguration, "R3p"));
//        addState(new Arms(this, stateConfiguration, "R3q"));
//        addState(new Lift(this, stateConfiguration, "R3r"));
        addState(new Arms(this, stateConfiguration, "R3s"));

        //Turn Around and back into Foundation
        addState(new Face(this, stateConfiguration, "R3t"));
        addThreadedState(new Crane(this, stateConfiguration, "R3t_crane"));
        addState(new DirectionDrive(this, stateConfiguration, "R3u"));

        //Grabs Foundation and moves it into position
        addState(new FoundationClamp(this, stateConfiguration, "R3v"));
        addState(new FaceActveCheck(this, stateConfiguration, "R3x"));
         addState(new DirectionDrive(this, stateConfiguration, "R3y"));
        addThreadedState(new Lift(this, stateConfiguration, "R3z"));
        addThreadedState(new Crane(this, stateConfiguration, "R3w_crane"));

        //Releases Foundation and drives toward Bridge.
        addState(new FoundationClamp(this, stateConfiguration, "R3aa"));
        addState(new DirectionDrive(this, stateConfiguration, "R3aa_back"));
        addState(new FaceActveCheck(this,stateConfiguration, "R3aa_face"));
        addState(new DirectionDrive(this, stateConfiguration, "R3ab"));
        addState(new Face(this, stateConfiguration, "R3ab_face"));

        //Use Distance Sensor to check for the presence of another robot in parking position.
        //If another robot is present, move to the other side of the bridge before parking.
        addState(new RobotDodge(this, stateConfiguration, "R3ac"));
        addState(new DirectionDrive(this, stateConfiguration, "R3ac_manual"));
        addState(new DirectionDrive(this, stateConfiguration, "R3ad"));
    }
}
