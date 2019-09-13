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

    //positive degrees are towards the right (clockwise), negative degrees are towards the left
    //(counterclockwise).

    public double getForwardLeftPower(double directionDegrees, double zeroPowerThreshold) {

        return degreesToPower(45, directionDegrees, zeroPowerThreshold);
    }

    public double getForwardRightPower(double directionDegrees, double zeroPowerThreshold) {

        return degreesToPower(135, directionDegrees, zeroPowerThreshold);

    }

    private double degreesToPower(int translation, double directionDegrees, double zeroPowerThreshold) {

        double power = 1.415 * (Math.sin(Math.toRadians(directionDegrees + translation)));

        if (power > 1) {power = 1;}
        if (power < -1) {power = -1;}

        if (power < zeroPowerThreshold && power > -zeroPowerThreshold) {power = 0;}

        return power;
    }
}
