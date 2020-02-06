package org.timecrafters.SkyStone_2019_2020.Autonomos.Engines;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.Arms;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.Crane;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.DirectionDrive;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.Face;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.FaceActveCheck;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.GripRollers;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.Lift;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.SkystoneSight;
import org.timecrafters.SkyStone_2019_2020.Autonomos.Subengines.R2Center;
import org.timecrafters.SkyStone_2019_2020.Autonomos.Subengines.R2Left;
import org.timecrafters.SkyStone_2019_2020.Autonomos.Subengines.R2Right;
import org.timecrafters.SkyStone_2019_2020.Autonomos.Subengines.R2iCenter;
import org.timecrafters.SkyStone_2019_2020.Autonomos.Subengines.R2iLeft;
import org.timecrafters.SkyStone_2019_2020.Autonomos.Subengines.R2iRight;
import org.timecrafters.SkyStone_2019_2020.IMUInit;
import org.timecrafters.engine.Engine;

@Autonomous (name = "R2", group = "Red")
public class R2 extends Engine {
    @Override
    public void setProcesses() {

        //the stateConfiguration is what reads the file kept on the phone
        StateConfiguration stateConfiguration = new StateConfiguration();
        SkystoneSight skystoneSight = new SkystoneSight(this, stateConfiguration, "R2b_stone");


        addState(new IMUInit(this));
        addState(new DirectionDrive(this, stateConfiguration, "R2a"));
        addThreadedState(new Lift(this, stateConfiguration, "R2b_lift"));
        addState(new DirectionDrive(this, stateConfiguration, "R2b"));
        addState(new FaceActveCheck(this,stateConfiguration,"R2b_face"));
        addThreadedState(new Lift(this, stateConfiguration,"R2b_lift"));
        addThreadedState(new Arms(this, stateConfiguration,"R2d"));

        addState(skystoneSight);

        addSubEngine(new R2Left(this, skystoneSight, stateConfiguration));
        addSubEngine(new R2Center(this, skystoneSight, stateConfiguration));
        addSubEngine(new R2Right(this, skystoneSight, stateConfiguration));




        addState(new Arms(this, stateConfiguration, "R2g"));

        addSubEngine(new R2iLeft(this, skystoneSight, stateConfiguration));
        addSubEngine(new R2iCenter(this, skystoneSight, stateConfiguration));
        addSubEngine(new R2iRight(this, skystoneSight, stateConfiguration));

        addState(new FaceActveCheck(this, stateConfiguration, "R2j"));

        //Drive to build area
        addState(new DirectionDrive(this, stateConfiguration, "R2k"));
        addThreadedState(new Crane(this, stateConfiguration, "R2k_crane"));
        addState(new Face(this, stateConfiguration, "R2l"));
        addThreadedState(new Lift(this, stateConfiguration, "R2m"));

        addState(new DirectionDrive(this, stateConfiguration, "R2n"));
        // rotating block not necessary, removed for speed.
        // addState(new Arms(this, stateConfiguration, "R2o"));
        // addState(new Lift(this, stateConfiguration, "R2p"));
        addState(new Arms(this, stateConfiguration, "R2q"));
        addState(new DirectionDrive(this, stateConfiguration, "R2r"));
        addState( new Face(this, stateConfiguration, "R2s"));
        addThreadedState(new Lift(this, stateConfiguration, "R2s_lower"));
        addState(new DirectionDrive(this, stateConfiguration, "R2t"));
        addState(new DirectionDrive(this, stateConfiguration, "R2u"));
    }
}
