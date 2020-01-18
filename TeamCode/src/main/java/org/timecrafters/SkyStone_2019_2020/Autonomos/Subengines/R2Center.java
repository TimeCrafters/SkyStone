package org.timecrafters.SkyStone_2019_2020.Autonomos.Subengines;

import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.Crane;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.Face;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.SkystoneSight;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.Turn;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.SubEngine;

public class R2Center extends SubEngine {

    private SkystoneSight skystoneSight;
    Engine engine;
    private StateConfiguration stateConfiguration;

    public R2Center(Engine engine, SkystoneSight skystoneSight, StateConfiguration stateConfiguration) {
        this.engine = engine;
        this.skystoneSight = skystoneSight;
        this.stateConfiguration = stateConfiguration;
    }

    @Override
    public void setProcesses() {
        addState(new Turn(engine, stateConfiguration, "R2b_Ca"));
        addState(new Crane(engine, stateConfiguration, "R2b_Cb"));
    }

    @Override
    public void evaluate() {
        setRunable(skystoneSight.SkystonePosition == 0);
    }
}
