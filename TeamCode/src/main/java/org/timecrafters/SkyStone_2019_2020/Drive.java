package org.timecrafters.SkyStone_2019_2020;

import org.timecrafters.engine.State;


public class Drive extends State {

    public void init() {

        //Drive Motor Initialization can go here

    }

    @Override
    public void exec() throws InterruptedException {

    }

    public void drivetowards(int degrees) {

        //positive degrees are towards the right (clockwise), negative degrees are towards the left
        //(counterclockwise)

        double forwardLeftPower = 1.415 * (Math.sin((degrees + 45) * Math.PI / 180));
        double forwardRightPower = 1.415 * (Math.sin((degrees + 135) * Math.PI / 180));

        if(forwardLeftPower > 1) {forwardLeftPower = 1.0;}
        if(forwardRightPower > 1) {forwardRightPower = 1.0;}
        if(forwardLeftPower < -1) {forwardLeftPower = -1.0;}
        if(forwardRightPower < -1) {forwardRightPower = -1.0;}

        engine.telemetry.addData("Left Power", forwardLeftPower);
        engine.telemetry.addData("Right Power", forwardRightPower);

    }
}
