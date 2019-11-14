package org.timecrafters.SkyStone_2019_2020.Autonomos.Subengines;

import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.Crane;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.Face;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.SkystoneSight;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.SubEngine;

public class B2Left extends SubEngine {

    private SkystoneSight skystoneSight;
    private Engine engine;
    private StateConfiguration stateConfiguration;

    public B2Left(Engine engine, SkystoneSight skystoneSight, StateConfiguration stateConfiguration) {
        this.engine = engine;
        this.skystoneSight = skystoneSight;
        this.stateConfiguration = stateConfiguration;
    }

    @Override
    public void setProcesses() {
        addState(new Face(engine, stateConfiguration, "R2La"));
        addThreadedState(new Crane(engine, stateConfiguration, "R2Lb"));
    }

    @Override
    public void evaluate() {
        setRunable(skystoneSight.SkystonePosition == -1);
    }
}