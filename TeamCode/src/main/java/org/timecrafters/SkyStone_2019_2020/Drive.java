package org.timecrafters.SkyStone_2019_2020;

import org.timecrafters.engine.State;


public class Drive extends State {

    public void init() {

        //Drive Motor Initialization could go here

    }

    @Override
    public void exec() throws InterruptedException {

    }

    //The forward-left and backward-right motors need the same powers and the forward-right and
    //backward-left need the same power.

    public double getForwardLeftPower(double directionDegrees) {

        //positive degrees are towards the right (clockwise), negative degrees are towards the left
        //(counterclockwise)

        double forwardLeftPower = 1.415 * (Math.sin((directionDegrees + 45) * Math.PI / 180));

        if(forwardLeftPower > 1) {forwardLeftPower = 1.0;}
        if(forwardLeftPower < -1) {forwardLeftPower = -1.0;}

        return forwardLeftPower;
    }

    public double getForwardRightPower(double directionDegrees) {

        double forwardRightPower = 1.415 * (Math.sin((directionDegrees + 135) * Math.PI / 180));

        if(forwardRightPower > 1) {forwardRightPower = 1.0;}
        if(forwardRightPower < -1) {forwardRightPower = -1.0;}

        return forwardRightPower;
    }
}
