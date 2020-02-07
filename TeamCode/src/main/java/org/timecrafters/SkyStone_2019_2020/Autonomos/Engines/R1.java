package org.timecrafters.SkyStone_2019_2020.Autonomos.Engines;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.Crane;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.FaceActveCheck;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.FoundationClamp;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.DirectionDrive;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.Face;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.LiftZero;
import org.timecrafters.SkyStone_2019_2020.IMUInit;
import org.timecrafters.engine.Engine;

@Autonomous (name = "R1", group = "Red")
public class R1 extends Engine {
    @Override
    public void setProcesses() {
        StateConfiguration stateConfiguration = new StateConfiguration();

        addState(new IMUInit(this));
        addState(new DirectionDrive(this, stateConfiguration, "R1a"));
        addState(new DirectionDrive(this, stateConfiguration, "R1b"));
        addState(new DirectionDrive(this, stateConfiguration, "R1c_move"));
        addState(new FoundationClamp(this, stateConfiguration, "R1c"));
        addState(new FaceActveCheck(this, stateConfiguration, "R1d"));
        addState(new DirectionDrive(this, stateConfiguration, "R1e"));
        addState(new FoundationClamp(this, stateConfiguration, "R1f"));
        addState(new DirectionDrive(this, stateConfiguration, "R1e_back"));
        addState(new FaceActveCheck(this, stateConfiguration, "R1e_face "));
        addThreadedState(new Crane(this, stateConfiguration,"R1e_carriage_out"));
        addThreadedState(new LiftZero(this, stateConfiguration, "R1e_lower"));
        addState(new DirectionDrive(this, stateConfiguration, "R1g"));
        addState(new DirectionDrive(this, stateConfiguration, "R1h"));
    }
}
