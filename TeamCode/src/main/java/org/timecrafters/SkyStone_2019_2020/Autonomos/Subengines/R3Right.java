package org.timecrafters.SkyStone_2019_2020.Autonomos.Subengines;

/**********************************************************************************************
 * Name: R2Right
 * Description: contains drive path for right positioned skystone. Enabled and disabled by
 * SkystonePosition
 **********************************************************************************************/


import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.Crane;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.SkystoneSight;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.Turn;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.SubEngine;

public class R3Right extends SubEngine {

    private SkystoneSight skystoneSight;
    private Engine engine;
    private StateConfiguration stateConfiguration;

    public R3Right(Engine engine, SkystoneSight skystoneSight, StateConfiguration stateConfiguration) {
        this.engine = engine;
        this.skystoneSight = skystoneSight;
        this.stateConfiguration = stateConfiguration;
    }

    @Override
    public void setProcesses() {
        addState(new Turn(engine, stateConfiguration, "R3Ra"));
        addThreadedState(new Crane(engine, stateConfiguration, "R3Rb"));
    }

    @Override
    public void evaluate() {
        setRunable(skystoneSight.SkystonePosition == 1);
    }
}
