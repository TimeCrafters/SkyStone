package org.timecrafters.SkyStone_2019_2020;

import org.timecrafters.engine.State;


public class Drive extends State {

    @Override
    public void exec() throws InterruptedException {

    }

    public void drivetowards(int degrees) {
        double forwardLeftPower = 1.415 * (Math.sin(degrees + 45));
        double forwardRightPower = 1.415 * (Math.sin(degrees + 135));

        if(forwardLeftPower > 1) {forwardLeftPower = 1.0;}
        if(forwardRightPower > 1) {forwardRightPower = 1.0;}

        engine.telemetry.addData("Left Power", forwardLeftPower);
        engine.telemetry.addData("Right Power", forwardRightPower);

    }
}
