package org.timecrafters.SkyStone_2019_2020.Autonomos.Engines;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.Crane;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.DirectionDrive;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.Face;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.FaceActveCheck;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.FoundationClamp;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.LiftZero;
import org.timecrafters.SkyStone_2019_2020.IMUInit;
import org.timecrafters.engine.Engine;

@Autonomous (name = "B1", group = "Blue")
public class B1 extends Engine {
    @Override
    public void setProcesses() {
        StateConfiguration stateConfiguration = new StateConfiguration();

        addState(new IMUInit(this));
        addState(new DirectionDrive(this, stateConfiguration, "B1a"));
        addState(new DirectionDrive(this, stateConfiguration, "B1b"));
        addState(new FoundationClamp(this, stateConfiguration, "B1c"));
//        addState(new DirectionDrive(this, stateConfiguration, "B1c_move"));
        addState(new FaceActveCheck(this, stateConfiguration, "B1d"));
        addState(new DirectionDrive(this, stateConfiguration, "B1e"));
        addState(new FoundationClamp(this, stateConfiguration, "B1f"));
        addState(new DirectionDrive(this, stateConfiguration, "B1e_back"));
        addState(new FaceActveCheck(this, stateConfiguration, "B1e_face"));
        addState(new Crane(this, stateConfiguration,"B1e_carriage_out"));
        addState(new LiftZero(this, stateConfiguration, "B1e_lower"));

        addState(new DirectionDrive(this, stateConfiguration, "B1g"));
        addState(new DirectionDrive(this, stateConfiguration, "B1h"));
    }
}
