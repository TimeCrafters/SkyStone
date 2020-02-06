package org.timecrafters.SkyStone_2019_2020.Autonomos.Subengines;

import org.cyberarm.NeXT.StateConfiguration;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.DirectionDrive;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.GripRollers;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.SkystoneSight;
import org.timecrafters.SkyStone_2019_2020.Autonomos.States.SkystoneSightBlue;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.SubEngine;

public class R2iCenter extends SubEngine {

    private SkystoneSight skystoneSight;
    private Engine engine;
    private StateConfiguration stateConfiguration;

    public R2iCenter(Engine engine, SkystoneSight skystoneSight, StateConfiguration stateConfiguration) {
        this.engine = engine;
        this.skystoneSight = skystoneSight;
        this.stateConfiguration = stateConfiguration;
    }

    @Override
    public void setProcesses() {
        addState(new GripRollers(engine, stateConfiguration, "R2h"));
        addThreadedState(new DirectionDrive(engine, stateConfiguration, "R2i_C"));
    }

    @Override
    public void evaluate() {
        setRunable(skystoneSight.SkystonePosition == 0);
    }
}
