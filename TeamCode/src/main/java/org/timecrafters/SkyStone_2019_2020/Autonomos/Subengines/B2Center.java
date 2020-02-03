package org.timecrafters.SkyStone_2019_2020.Autonomos.Subengines;

import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.Arms;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.Crane;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.DirectionDrive;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.DirectionDriveAbsoute;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.Face;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.GripRollers;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.LiftZero;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.SkystoneSight;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.SkystoneSightBlue;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.Turn;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.SubEngine;

public class B2Center extends SubEngine {

    private SkystoneSightBlue skystoneSight;
    private Engine engine;
    private StateConfiguration stateConfiguration;

    public B2Center(Engine engine, SkystoneSightBlue skystoneSight, StateConfiguration stateConfiguration) {
        this.engine = engine;
        this.skystoneSight = skystoneSight;
        this.stateConfiguration = stateConfiguration;
    }

    @Override
    public void setProcesses() {
        addState(new DirectionDrive(engine, stateConfiguration, "B2e"));
        addThreadedState(new Crane(engine, stateConfiguration, "B2b_Cb"));
        addState(new LiftZero(engine, stateConfiguration, "B2f"));
        addThreadedState(new Turn(engine, stateConfiguration, "B2b_Ca"));
        addState(new Arms(engine, stateConfiguration, "B2g"));
        addState(new GripRollers(engine, stateConfiguration, "B2h"));
        addThreadedState(new DirectionDrive(engine, stateConfiguration, "B2i_C"));
    }

    @Override
    public void evaluate() {
        setRunable(skystoneSight.SkystonePosition == 0);
    }
}
