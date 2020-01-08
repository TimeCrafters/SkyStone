package org.timecrafters.SkyStone_2019_2020.Autonomos.Engines;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.Arms;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.Crane;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.DirectionDrive;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.FoundationClamp;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.RobotDodge;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.Face;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.GripRollers;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.Lift;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.LiftZero;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.SkystoneSight;
import org.timecrafters.SkyStone_2019_2020.Autonomos.Subengines.B3Center;
import org.timecrafters.SkyStone_2019_2020.Autonomos.Subengines.B3Left;
import org.timecrafters.SkyStone_2019_2020.Autonomos.Subengines.B3Right;
import org.timecrafters.SkyStone_2019_2020.IMUInit;
import org.timecrafters.engine.Engine;

@Autonomous (name = "B3", group = "Blue")
public class B3 extends Engine {
    @Override
    public void setProcesses() {
        StateConfiguration stateConfiguration = new StateConfiguration();
        SkystoneSight skystoneSight = new SkystoneSight(this, stateConfiguration, "B3stone");
        addState(new IMUInit(this));

        //Positions robot in front of the first set of stones.
        addState(new DirectionDrive(this, stateConfiguration, "B3a"));
        addState(new DirectionDrive(this, stateConfiguration, "B3b"));

        //Identifies the Position of Skystone and moves into position to grab it
        addState(skystoneSight);
        addSubEngine(new B3Left(this, skystoneSight, stateConfiguration));
        addSubEngine(new B3Center(this, skystoneSight, stateConfiguration));
        addSubEngine(new B3Right(this, skystoneSight, stateConfiguration));

        addState(new Arms(this, stateConfiguration, "B3c"));
        addState(new Crane(this, stateConfiguration, "B3d"));
        addState(new DirectionDrive(this, stateConfiguration, "B3e"));
        addState(new LiftZero(this, stateConfiguration, "B3f"));
        addState(new Arms(this, stateConfiguration, "B3g"));
        addState(new GripRollers(this, stateConfiguration, "B3h"));

        //back up and turn towards build site
        addState(new DirectionDrive(this, stateConfiguration, "B3i"));
        addState(new Face(this, stateConfiguration, "B3j"));

        //drive at varying speeds to end of the build site, then face the foundation
        addState(new DirectionDrive(this, stateConfiguration, "B3k"));
        addState(new DirectionDrive(this, stateConfiguration, "B3l"));
        addState(new DirectionDrive(this, stateConfiguration, "B3m"));
        addState(new Face(this, stateConfiguration, "B3n"));

        addState(new Lift(this, stateConfiguration, "B3o"));
        addState(new DirectionDrive(this, stateConfiguration, "B3p"));
        addState(new Arms(this, stateConfiguration, "B3q"));
        addState(new Lift(this, stateConfiguration, "B3r"));
        addState(new Arms(this, stateConfiguration, "B3s"));

        //Turn Around and back into Foundation
        addState(new Face(this, stateConfiguration, "B3t"));
        addState(new DirectionDrive(this, stateConfiguration, "B3u"));

        //Grabs Foundation and moves it into position
        addState(new FoundationClamp(this, stateConfiguration, "B3v"));
        addState(new DirectionDrive(this, stateConfiguration, "B3w"));
        addState(new Face(this, stateConfiguration, "B3x"));
        addState(new DirectionDrive(this, stateConfiguration, "B3y"));
        addState(new Lift(this, stateConfiguration, "B3z"));

        //Releases Foundation and drives toward Bridge.
        addState(new FoundationClamp(this, stateConfiguration, "B3aa"));
        addState(new DirectionDrive(this, stateConfiguration, "B3ab"));

        //Use Distance Sensor to check for the presence of another robot in parking position.
        //If another robot is present, move to the other side of the bridge before parking.
        addState(new RobotDodge(this, stateConfiguration, "B3ac"));
        addState(new DirectionDrive(this, stateConfiguration, "B3ad"));
    }
}
