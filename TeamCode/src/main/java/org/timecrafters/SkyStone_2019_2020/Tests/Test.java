package org.timecrafters.SkyStone_2019_2020.Tests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.Arms;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.Crane;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.FoundationClamp;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.GripRollers;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.Lift;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.LiftZero;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.RampDrive;
import org.timecrafters.SkyStone_2019_2020.IMUInit;
import org.timecrafters.engine.Engine;

@TeleOp(name = "Testing",group = "Testing")
public class Test extends Engine {
    @Override
    public void setProcesses() {
        StateConfiguration stateConfiguration = new StateConfiguration();
        addState(new IMUInit(this));
        addState(new RampDrive(this, stateConfiguration, "Tdrive"));
        addState(new FoundationClamp(this, stateConfiguration, "Tfinger"));
        addState(new FoundationClamp(this, stateConfiguration, "Tfinger2"));
        addState(new Crane(this, stateConfiguration, "Tcrane"));
        addState(new LiftZero(this, stateConfiguration, "Tzero"));
        addState(new Lift(this, stateConfiguration, "Tlift"));
        addState(new GripRollers(this, stateConfiguration, "Tgrip"));
        addState(new Arms(this, stateConfiguration, "Tarms"));
    }
}
