package org.timecrafters.temptingreality.engines;

import org.timecrafters.engine.Engine;
import org.timecrafters.temptingreality.states.miniBotDrive;

public class miniBotEngine extends Engine {
    @Override
    public void setProcesses() {
        addState(new miniBotDrive());
    }
}
